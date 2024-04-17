package userService;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;

public class UserSimulation extends Simulation {


    //Http configuration
    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://www.google.com");

    //Scenario definition
    private ScenarioBuilder scn = scenario("User service perf test")
            .exec(http("Get all users")
                    .get("/"));

    //Load simulation
     {
        setUp(
                scn.injectOpen(atOnceUsers(10))
        ).protocols(httpProtocol).maxDuration(Duration.ofSeconds(10));
    }
}
