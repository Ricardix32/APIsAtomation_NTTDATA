package org.example.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import org.example.apis.PetStoreEndpoints;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DeletePet implements Task {

    private final long petId;

    public DeletePet(long petId) {
        this.petId = petId;
    }

    public static DeletePet byId(long petId) {
        return instrumented(DeletePet.class, petId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Delete.from(PetStoreEndpoints.PET_BY_ID)
                        .with(request -> request
                                .pathParam("petId", petId)
                                .accept(ContentType.JSON)
                        )
        );
    }
}