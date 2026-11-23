package org.example.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class PetField implements Question<String> {
    private final String fieldName;

    public PetField(String fieldName) {
        this.fieldName = fieldName;
    }

    public static PetField valueOf(String fieldName) {
        return new PetField(fieldName);
    }

    @Override
    public String answeredBy(Actor actor) {
        Object value = SerenityRest.lastResponse().jsonPath().get(fieldName);
        return value != null ? String.valueOf(value) : "";
    }
}
