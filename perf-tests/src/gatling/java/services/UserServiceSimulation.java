package services;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class UserServiceSimulation extends Simulation {

    //parameters
    private static final int PAUSE_TIME_IN_SECONDS = 1;
    //    private static final int START_USERS_COUNT = Integer.parseInt(System.getProperty("START_USERS", "50"));
//    private static final int END_USERS_COUNT = Integer.parseInt(System.getProperty("END_USERS", "500"));
    private static final int RAMP_DURATION = Integer.parseInt(System.getProperty("RAMP_DURATION", "500"));
    private static final int INCREMENT_USERS = Integer.parseInt(System.getProperty("INCREMENT_USERS", "20"));
    private static final int STEPS_COUNT = Integer.parseInt(System.getProperty("STEPS_COUNT", "3"));
    ;
    private static final int STEP_TIME = RAMP_DURATION / 2 * STEPS_COUNT;
    public static final String IP_NOT_DEFINED_ERROR = "IP_NOT_DEFINED_ERROR";
    private static final String API_GATEWAY_IP = System.getProperty("IP", IP_NOT_DEFINED_ERROR);
    private static final String URL = "http://" + API_GATEWAY_IP + ":8080";

    //Feeder for test data
    private static final FeederBuilder.FileBased<Object> jsonFeeder = jsonFile("data/usersData.json").random();

    //HTTP calls
    private static final ChainBuilder getAllUsers =
            exec(http("Get all users")
                    .get("/users"));
    private static final ChainBuilder createUser =
            feed(jsonFeeder)
                    .exec(http("Create user")
                            .post("/users")
                            .body(ElFileBody("bodies/newUserTemplate.json")).asJson()
                            .check(jmesPath("id").optional().saveAs("userId")));
    private static final ChainBuilder getUserById =
            exec(http("Get user by id")
                    .get("/users/#{userId}"));
    private static final ChainBuilder deleteUserById =
            exec(http("Delete user by id")
                    .delete("/users/#{userId}"));

    //Http configuration
    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl(URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    //Scenario definition
    private final ScenarioBuilder scn = scenario("User service perf test")
            .exec(getAllUsers)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(createUser)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(getUserById)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(deleteUserById);

    //Load simulation
    {
        setUp(
                scn.injectClosed(
//                        LINEAR:
//                        rampConcurrentUsers(START_USERS_COUNT).to(END_USERS_COUNT).during(RAMP_DURATION)
//                        STEPS:
                        incrementConcurrentUsers(INCREMENT_USERS)
                                .times(STEPS_COUNT)
                                .eachLevelLasting(STEP_TIME)
                                .separatedByRampsLasting(STEP_TIME)
                                .startingFrom(0)
                )
        ).protocols(httpProtocol);
    }

    @Override
    public void before() {
//        System.out.println("Running test with users from " + START_USERS_COUNT + " to " + END_USERS_COUNT + " over " + RAMP_DURATION + " seconds");
        System.out.println("Running scalability test for " + INCREMENT_USERS + " users from  over " + RAMP_DURATION + " seconds");
        System.out.println("API Gateway IP: " + API_GATEWAY_IP);

        if (IP_NOT_DEFINED_ERROR.equals(API_GATEWAY_IP)) {
            System.out.println("API Gateway IP is not defined. Exit tests");
            System.exit(0);
        }
    }
}
