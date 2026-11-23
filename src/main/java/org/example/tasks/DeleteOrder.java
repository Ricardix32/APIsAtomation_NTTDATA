package org.example.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import org.example.apis.PetStoreEndpoints;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DeleteOrder implements Task {

    private final long orderId;

    public DeleteOrder(long orderId) {
        this.orderId = orderId;
    }

    public static DeleteOrder byId(long orderId) {
        return instrumented(DeleteOrder.class, orderId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Delete.from(PetStoreEndpoints.STORE_ORDER_BY_ID)
                        .with(request -> request
                                .pathParam("orderId", orderId)
                                .accept(ContentType.JSON)
                        )
        );
    }
}