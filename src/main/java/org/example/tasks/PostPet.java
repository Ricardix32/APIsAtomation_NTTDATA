package org.example.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.example.apis.PetStoreEndpoints;
import org.example.models.Pet;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PostPet implements Task {

    private final Pet pet;

    public PostPet(Pet pet) {
        this.pet = pet;
    }

    public static PostPet withInfo(Pet pet) {
        return instrumented(PostPet.class, pet);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to(PetStoreEndpoints.PET)
                        .with(request -> request
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body(pet)
                        )
        );
    }
}