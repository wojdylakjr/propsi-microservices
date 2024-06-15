package services;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class RentalServiceSimulation extends Simulation {
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
//    private static final String URL = "http://localhost:8083";

    //HTTP calls
    private static final ChainBuilder getAllRentals =
            exec(http("Get all rentals")
                    .get("/rentals"));
    private static final ChainBuilder createRental =
            exec(http("Create rental")
                    .post("/rentals")
                    .body(ElFileBody("bodies/newRental.json")).asJson()
                    .check(jmesPath("id").optional().saveAs("rentalId")));
    private static final ChainBuilder getRentalById =
            exec(http("Get rental by id")
                    .get("/rentals/#{rentalId}"));

    private static final ChainBuilder createBill =
            exec(http("Create bill for rental")
                    .post("/bills")
                    .body(ElFileBody("bodies/newBill.json")).asJson());

    private static final ChainBuilder getAllBills =
            exec(http("Get all bills")
                    .get("/bills"));

    private static final ChainBuilder deleteRentalById =
            exec(http("Delete rental by id")
                    .delete("/rentals/#{rentalId}"));


    //Http configuration
    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl(URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    //Scenario definition
    private final ScenarioBuilder scn = scenario("Rental service perf test")
//            .exec(getAllRentals)
//            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(createRental)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(getRentalById)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(createBill)
//            .pause(PAUSE_TIME_IN_SECONDS)
//            .exec(getAllBills)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(deleteRentalById);


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
