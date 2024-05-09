package services;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class PaymentServiceSimulation extends Simulation {

    public static final String IP_NOT_DEFINED_ERROR = "IP_NOT_DEFINED_ERROR";
    //parameters
    private static final int PAUSE_TIME_IN_SECONDS = 1;
    //    private static final int START_USERS_COUNT = Integer.parseInt(System.getProperty("START_USERS", "50"));
//    private static final int END_USERS_COUNT = Integer.parseInt(System.getProperty("END_USERS", "500"));
    private static final int RAMP_DURATION = Integer.parseInt(System.getProperty("RAMP_DURATION", "500"));
    private static final int INCREMENT_USERS = Integer.parseInt(System.getProperty("INCREMENT_USERS", "20"));
    private static final int STEPS_COUNT = Integer.parseInt(System.getProperty("STEPS_COUNT", "3"));
    private static final int STEP_TIME = RAMP_DURATION / 2 * STEPS_COUNT;
    private static final String API_GATEWAY_IP = System.getProperty("IP", IP_NOT_DEFINED_ERROR);
    ;
    //    private static final String URL = "http://" + API_GATEWAY_IP + ":8080";
    private static final String URL = "http://localhost:8080";
    //HTTP calls
    //Initial scenario
    private final ChainBuilder createOwner =
            exec(http("Create owner")
                    .post("/users")
                    .body(ElFileBody("bodies/newOwner.json")).asJson()
                    .check(jmesPath("owners[0].id").optional().saveAs("ownerId"))
                    .check(jmesPath("id").optional().saveAs("userId")));
    private final ChainBuilder addPayUCredentials =
            exec(http("Add PayU credentials")
                    .put("/owners/#{ownerId}")
                    .body(ElFileBody("bodies/payUcredentials.json")).asJson());
    private final ChainBuilder createRental =
            exec(http("Create rental")
                    .post("/rentals")
                    .body(ElFileBody("bodies/newRentalInit.json")).asJson()
                    .check(jmesPath("id").optional().saveAs("rentalId")));
    private final ChainBuilder createBill =
            exec(http("Create bill for rental")
                    .post("/bills")
                    .body(ElFileBody("bodies/newBill.json")).asJson()
                    .check(jmesPath("id").optional().saveAs("billId")));
    //main scenario
    private final ChainBuilder getAllPayments =
            exec(http("Get all payments")
                    .get("/payments"));
    private final ChainBuilder createPayment =
            exec(http("Create payment")
                    .post("/payments")
                    .body(ElFileBody("bodies/newPayment.json")).asJson()
                    .check(jmesPath("orderId").optional().saveAs("paymentId")));
    private final ChainBuilder deletePaymentById =
            exec(http("Delete payment by id")
                    .delete("/payments/#{paymentId}"));
    //Final scenario
    private final ChainBuilder deleteUserById =
            exec(http("Delete user by id")
                    .delete("/users/#{userId}"));
    private final ChainBuilder deleteRentalById =
            exec(http("Delete rental by id")
                    .delete("/rentals/#{rentalId}"));
    //Http configuration
    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl(URL)
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");
    private String userId = "NOT_DEFINED";
    private String rentalId = "NOT_DEFINED";
    private final ScenarioBuilder finalScenario = scenario("Final payment service scenario")
            .exec(session -> {
                Session newSession = session.set("userId", userId);
                return newSession;
            })
            .exec(deleteUserById)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(session -> {
                Session newSession = session.set("rentalId", rentalId);
                return newSession;
            })
            .exec(deleteRentalById);
    private String billId = "NOT_DEFINED";
    private final ScenarioBuilder initialScenario = scenario("Initial scenario for rental service")
            .exec(createOwner)
            .exec(session -> {
                userId = session.getString("userId");
                System.out.println("userId: " + userId);
                return session;
            })
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(addPayUCredentials)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(createRental)
            .exec(session -> {
                rentalId = session.getString("rentalId");
                System.out.println("rentalId: " + rentalId);
                return session;
            })
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(createBill)
            .exec(session -> {
                billId = session.getString("billId");
                System.out.println("billId: " + billId);
                return session;
            })
            .pause(5);
    private final ScenarioBuilder mainScenario = scenario("Payment service main scenario")
            .exec(session -> {
                Session newSession = session.set("billId", billId);
                return newSession;
            })
            .exec(getAllPayments)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(createPayment)
            .pause(PAUSE_TIME_IN_SECONDS)
            .exec(deletePaymentById)
            .pause(5);

    //Load simulation
    {
        setUp(
                initialScenario.injectOpen(
                                atOnceUsers(1)
                        )
                        .andThen(
                                mainScenario.injectClosed(
//                        LINEAR:
                                        rampConcurrentUsers(1).to(3000).during(15)
//                        STEPS:
//                        incrementConcurrentUsers(INCREMENT_USERS)
//                                .times(STEPS_COUNT)
//                                .eachLevelLasting(STEP_TIME)
//                                .separatedByRampsLasting(STEP_TIME)
//                                .startingFrom(0)
                                ))
                        .andThen(
                                finalScenario.injectOpen(
                                        atOnceUsers(1)
                                )
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
//            System.exit(0);
        }
    }
}