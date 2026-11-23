package org.example.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.example.apis.PetStoreEndpoints;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetOrder implements Task {

    private final long orderId;

    public GetOrder(long orderId) {
        this.orderId = orderId;
    }

    public static GetOrder byId(long orderId) {
        return instrumented(GetOrder.class, orderId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource(PetStoreEndpoints.STORE_ORDER_BY_ID)
                        .with(request -> request
                                .pathParam("orderId", orderId)
                                .accept(ContentType.JSON)
                        )
        );
    }
}