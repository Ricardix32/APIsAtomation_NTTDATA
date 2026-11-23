package org.example.stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.util.EnvironmentVariables;
import org.example.models.Order;
import org.example.tasks.DeleteOrder;
import org.example.tasks.GetOrder;
import org.example.tasks.PostOrder;

import java.util.concurrent.ThreadLocalRandom;

public class StoreOrderStepDefinition {

    private EnvironmentVariables environmentVariables;
    private Order currentOrder;
    private boolean cleanupRequired = false;

    private String getBaseUrl() {
        return EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("petstore.baseurl");
    }

    @Given("el {actor} prepara una orden para {int} mascotas con estado {string}")
    public void elActorPreparaUnaOrdenParaMascotasConEstado(Actor actor, int cantidad, String estado) {
        actor.whoCan(CallAnApi.at(getBaseUrl()));
        // Swagger PetStore recomienda IDs de orden entre 1 y 10 para GET /store/order/{orderId}
        long orderId = ThreadLocalRandom.current().nextLong(1, 10);
        long petId = ThreadLocalRandom.current().nextLong(100000, 999999);
        currentOrder = new Order(orderId, petId, cantidad, estado);
    }

    @When("el {actor} registra la orden en la tienda")
    public void elActorRegistraLaOrdenEnLaTienda(Actor actor) {
        actor.attemptsTo(PostOrder.withInfo(currentOrder));
        cleanupRequired = true;
    }

    @And("el {actor} consulta la orden por su identificador")
    public void elActorConsultaLaOrdenPorSuIdentificador(Actor actor) {
        actor.attemptsTo(GetOrder.byId(currentOrder.getId()));
    }

    @When("el {actor} consulta una orden con identificador inexistente")
    public void elActorConsultaUnaOrdenConIdentificadorInexistente(Actor actor) {
        actor.whoCan(CallAnApi.at(getBaseUrl()));
        actor.attemptsTo(GetOrder.byId(999999L));
    }

    @After("@Orders")
    public void limpiarOrden() {
        if (cleanupRequired && currentOrder != null) {
            OnStage.theActorInTheSpotlight().attemptsTo(DeleteOrder.byId(currentOrder.getId()));
            cleanupRequired = false;
        }
    }
}