package userService;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class UserSimulation extends Simulation {

    //parameters
    private static final int PAUSE_TIME_IN_SECONDS = 1;
    private static final int USERS_COUNT = 500;
    private static final int RAMP_DURATION = 30;
    private static final String API_GATEWAY_IP = "34.116.141.175";
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
                            .check(jmesPath("id").saveAs("userId")));
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
                scn.injectOpen(
                        nothingFor(1),
                        rampUsers(USERS_COUNT).during(RAMP_DURATION)
                )
        ).protocols(httpProtocol);
    }

    @Override
    public void before() {
        System.out.println("Running test with " + USERS_COUNT + " users over " + RAMP_DURATION + " seconds");
    }
}
