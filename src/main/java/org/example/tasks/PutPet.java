package org.example.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;
import org.example.apis.PetStoreEndpoints;
import org.example.models.Pet;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PutPet implements Task {

    private final Pet pet;

    public PutPet(Pet pet) {
        this.pet = pet;
    }

    public static PutPet withInfo(Pet pet) {
        return instrumented(PutPet.class, pet);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to(PetStoreEndpoints.PET)
                        .with(request -> request
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body(pet)
                        )
        );
    }
}