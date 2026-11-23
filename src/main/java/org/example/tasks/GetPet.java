package org.example.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.example.apis.PetStoreEndpoints;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetPet implements Task {

    private final long petId;

    public GetPet(long petId) {
        this.petId = petId;
    }

    public static GetPet byId(long petId) {
        return instrumented(GetPet.class, petId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource(PetStoreEndpoints.PET_BY_ID)
                        .with(request -> request
                                .pathParam("petId", petId)
                                .accept(ContentType.JSON)
                        )
        );
    }
}