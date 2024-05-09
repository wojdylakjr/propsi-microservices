package services;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class PropertyServiceSimulation extends Simulation {

    public static final String IP_NOT_DEFINED_ERROR = "IP_NOT_DEFINED_ERROR";
    //parameters
    private static final int PAUSE_TIME_IN_SECONDS = 1;
    //    private static final int START_USERS_COUNT = Integer.parseInt(System.getProperty("START_USERS", "50"));
//    private static final int END_USERS_COUNT = Integer.parseInt(System.getProperty("END_USERS", "500"));
    private static final int RAMP_DURATION = Integer.parseInt(System.getProperty("RAMP_DURATION", "500"));
    private static final int INCREMENT_USERS = Integer.parseInt(System.getProperty("INCREMENT_USERS", "20"));
    ;
    private static final int STEPS_COUNT = Integer.parseInt(System.getProperty("STEPS_COUNT", "3"));
    private static final int STEP_TIME = RAMP_DURATION / (2 * STEPS_COUNT);
    private static final String API_GATEWAY_IP = System.getProperty("IP", IP_NOT_DEFINED_ERROR);
    private static final String URL = "http://" + API_GATEWAY_IP + ":8080";
//    private static final String URL = "http://localhost:8082";

    //HTTP calls
    private static final ChainBuilder getAllProperties =
            exec(http("Get all properties")
                    .get("/properties"));
    private static final ChainBuilder createProperty =
            exec(http("Create property")
                    .post("/properties")
                    .body(ElFileBody("bodies/newProperty.json")).asJson()
                    .check(jmesPath("id").optional().saveAs("propertyId")));
    private static final ChainBuilder getPropertyById =
            exec(http("Get property by id")
                    .get("/properties/#{propertyId}")
                    .check(jmesPath("premises[0].meters[0].id").optional().saveAs("meterId")));

    private static final ChainBuilder createMeterMeasurement =
            exec(http("Create meter measurement")
                    .post("/meter-measurements")
                    .body(ElFileBody("bodies/meterMeasurementBody.json")).asJson());
    private static final ChainBuilder deletePropertyById =
            exec(http("Delete user by id")
                    .delete("/properties/#{propertyId}"));

    //Http configuration
    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl(URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    //Scenario definition
    private final ScenarioBuilder scn = scenario("Property service perf test")
            .exec(getAllProperties)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(createProperty)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(getPropertyById)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(createMeterMeasurement)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(deletePropertyById);


    //Load simulation
    {
        setUp(
                scn.injectClosed(
//                        LINEAR:
//                        rampConcurrentUsers(1).to(1).during(15)
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
