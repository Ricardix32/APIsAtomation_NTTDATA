package org.example.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.example.apis.PetStoreEndpoints;
import org.example.models.Order;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PostOrder implements Task {

    private final Order order;

    public PostOrder(Order order) {
        this.order = order;
    }

    public static PostOrder withInfo(Order order) {
        return instrumented(PostOrder.class, order);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to(PetStoreEndpoints.STORE_ORDER)
                        .with(request -> request
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body(order)
                        )
        );
    }
}