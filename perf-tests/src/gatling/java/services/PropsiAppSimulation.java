package services;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class PropsiAppSimulation extends Simulation {

    public static final String IP_NOT_DEFINED_ERROR = "IP_NOT_DEFINED_ERROR";
    //parameters
    private static final int PAUSE_TIME_IN_SECONDS = 1;
    private static final int START_USERS_COUNT = Integer.parseInt(System.getProperty("START_USERS", "50"));
    private static final int END_USERS_COUNT = Integer.parseInt(System.getProperty("END_USERS", "500"));
    private static final int RAMP_DURATION = Integer.parseInt(System.getProperty("RAMP_DURATION", "500"));
    private static final int INCREMENT_USERS = Integer.parseInt(System.getProperty("INCREMENT_USERS", "20"));
    ;
    private static final int STEPS_COUNT = Integer.parseInt(System.getProperty("STEPS_COUNT", "3"));
    private static final int STEP_TIME = RAMP_DURATION / (2 * STEPS_COUNT);
    private static final String API_GATEWAY_IP = System.getProperty("IP", IP_NOT_DEFINED_ERROR);
    //    private static final String API_GATEWAY_IP = "localhost";
    private static final String URL = "http://" + API_GATEWAY_IP + ":8080";

    //Feeder for test data
    private static final FeederBuilder.FileBased<Object> jsonFeeder = jsonFile("data/usersData.json").random();

    //HTTP calls
    private static final ChainBuilder createUser =
            feed(jsonFeeder)
                    .exec(http("Create user")
                            .post("/users")
                            .body(ElFileBody("bodies/newUserTemplate.json")).asJson()
                            .check(jmesPath("owners[0].id").optional().saveAs("ownerId"))
                            .check(jmesPath("id").optional().saveAs("userId")));
    private static final ChainBuilder getUserById =
            exec(http("Get user by id")
                    .get("/users/#{userId}"));
    private static final ChainBuilder createProperty =
            exec(http("Create property")
                    .post("/properties")
                    .body(ElFileBody("bodies/newProperty.json")).asJson()
                    .check(jmesPath("id").optional().saveAs("propertyId")));
    private static final ChainBuilder getPropertyById =
            exec(http("Get property by id")
                    .get("/properties/#{propertyId}")
                    .check(jmesPath("premises[0].meters[0].id").optional().saveAs("meterId")));
    private static final ChainBuilder createRental =
            exec(http("Create rental")
                    .post("/rentals")
                    .body(ElFileBody("bodies/newRentalInit.json")).asJson()
                    .check(jmesPath("id").optional().saveAs("rentalId")));
    private static final ChainBuilder getRentalById =
            exec(http("Get rental by id")
                    .get("/rentals/#{rentalId}"));
    private static final ChainBuilder createBill =
            exec(http("Create bill for rental")
                    .post("/bills")
                    .body(ElFileBody("bodies/newBill.json")).asJson()
                    .check(jmesPath("id").optional().saveAs("billId")));
    private static final ChainBuilder deleteUserById =
            exec(http("Delete user by id")
                    .delete("/users/#{userId}"));
    private static final ChainBuilder deletePropertyById =
            exec(http("Delete property by id")
                    .delete("/properties/#{propertyId}"));
    private static final ChainBuilder deleteRentalById =
            exec(http("Delete rental by id")
                    .delete("/rentals/#{rentalId}"));
    private final ChainBuilder addPayUCredentials =
            exec(http("Add PayU credentials")
                    .put("/owners/#{ownerId}")
                    .body(ElFileBody("bodies/payUcredentials.json")).asJson());
    private final ChainBuilder createPayment =
            exec(http("Create payment")
                    .post("/payments")
                    .body(ElFileBody("bodies/newPayment.json")).asJson()
                    .check(jmesPath("orderId").optional().saveAs("paymentId")));
    private final ChainBuilder deletePaymentById =
            exec(http("Delete payment by id")
                    .delete("/payments/#{paymentId}"));
    //Http configuration
    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl(URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    //Scenario definition
    private final ScenarioBuilder scn = scenario("User service perf test")
            .exec(createUser)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(getUserById)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(addPayUCredentials)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(createProperty)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(getPropertyById)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(createRental)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(getRentalById)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(createBill)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(createPayment)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(deletePaymentById)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(deletePropertyById)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(deleteRentalById)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(deleteUserById);

    //Load simulations
    //1 linear up
//    {
//        setUp(
//                scn.injectClosed(
//                        rampConcurrentUsers(START_USERS_COUNT).to(END_USERS_COUNT).during(RAMP_DURATION)
//                )
//        ).protocols(httpProtocol);
//    }

    //2 steps up
    {
        setUp(
                scn.injectClosed(
                        incrementConcurrentUsers(INCREMENT_USERS)
                                .times(STEPS_COUNT)
                                .eachLevelLasting(STEP_TIME)
                                .separatedByRampsLasting(STEP_TIME)
                                .startingFrom(0)
                )
        ).protocols(httpProtocol);
    }

    // 3
//    {
//        setUp(
//                scn.injectClosed(
//                        constantConcurrentUsers(START_USERS_COUNT).during(240),
//                        rampConcurrentUsers(START_USERS_COUNT).to(END_USERS_COUNT).during(60),
//                        constantConcurrentUsers(END_USERS_COUNT).during(600),
//                        rampConcurrentUsers(END_USERS_COUNT).to(START_USERS_COUNT).during(60),
//                        constantConcurrentUsers(START_USERS_COUNT).during(240)
//                )
//        ).protocols(httpProtocol);
//    }

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
