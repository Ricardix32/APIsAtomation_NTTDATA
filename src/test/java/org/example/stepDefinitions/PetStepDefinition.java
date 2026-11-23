package org.example.stepDefinitions;

import io.cucumber.java.After;
import org.example.models.Pet;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.util.EnvironmentVariables;
import org.example.questions.PetField;
import org.example.questions.ResponseCode;
import org.example.tasks.PutPet;
import org.example.tasks.PostPet;
import org.example.tasks.DeletePet;
import org.example.tasks.GetPet;

import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Predicates.equalTo;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

public class PetStepDefinition {

    private EnvironmentVariables environmentVariables;
    private Pet actualPet;
    private boolean cleanupRequired = false;

    private String getBaseUrl() {
        return EnvironmentSpecificConfiguration
                .from(environmentVariables)
                .getProperty("petstore.baseurl");
    }

    private long generateUniqueId() {
        return ThreadLocalRandom.current().nextLong(100000, 999999);
    }

    @Given("el {actor} prepara los datos de una mascota llamada {string} con estado {string}")
    public void elActorPreparaLosDatosDeUnaMascotaLlamadaConEstado(Actor actor, String nombre, String estado) {
        actor.whoCan(CallAnApi.at(getBaseUrl()));
        actualPet = new Pet(generateUniqueId(), nombre, estado);
    }

    @When("el {actor} registra la mascota en PetStore")
    public void elActorRegistraLaMascotaEnPetStore(Actor actor) {
        actor.attemptsTo(PostPet.withInfo(actualPet));
        cleanupRequired = true;
    }

    @Then("el {actor} verifica que el codigo de respuesta sea {int}")
    public void elActorVerificaQueElCodigoDeRespuestaSea(Actor actor, int codigoEsperado) {
        actor.should(
                seeThat("El codigo de respuesta", ResponseCode.getStatus(), equalTo(codigoEsperado))
        );
    }

    @And("el {actor} valida que el campo {string} de la respuesta sea {string}")
    public void elActorValidaQueElCampoDeLaRespuestaSea(Actor actor, String campo, String valorEsperado) {
        actor.should(
                seeThat("El campo " + campo, PetField.valueOf(campo), equalTo(valorEsperado))
        );
    }

    @Given("el {actor} tiene una mascota registrada llamada {string} con estado {string}")
    public void elActorTieneUnaMascotaRegistradaLlamadaConEstado(Actor actor, String nombre, String estado) {
        actor.whoCan(CallAnApi.at(getBaseUrl()));
        actualPet = new Pet(generateUniqueId(), nombre, estado);
        actor.attemptsTo(PostPet.withInfo(actualPet));
        actor.should(
                seeThat("El codigo de creacion", ResponseCode.getStatus(), equalTo(200))
        );
        cleanupRequired = true;
    }

    @When("el {actor} actualiza el nombre de la mascota a {string} y su estado a {string}")
    public void elActorActualizaElNombreDeLaMascotaAYSuEstadoA(Actor actor, String nuevoNombre, String nuevoEstado) {
        actualPet.setName(nuevoNombre);
        actualPet.setStatus(nuevoEstado);
        actor.attemptsTo(PutPet.withInfo(actualPet));
    }

    @And("el {actor} elimina la mascota del catalogo")
    public void elActorEliminaLaMascotaDelCatalogo(Actor actor) {
        actor.attemptsTo(DeletePet.byId(actualPet.getId()));
        cleanupRequired = false;
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @When("el {actor} consulta la mascota por su identificador")
    public void elActorConsultaLaMascotaPorSuIdentificador(Actor actor) {
        actor.attemptsTo(GetPet.byId(actualPet.getId()));
    }

    @After("@PetStore")
    public void limpiarMascota() {
        if (cleanupRequired && actualPet != null) {
            OnStage.theActorInTheSpotlight().attemptsTo(DeletePet.byId(actualPet.getId()));
            cleanupRequired = false;
        }
    }
}
