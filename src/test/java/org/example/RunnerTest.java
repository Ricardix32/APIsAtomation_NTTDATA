package org.example;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.example.stepDefinitions", //incluyo stepDeinitions para arrancar + rápido el test
        tags = "@PetStore or @Orders", //incluí el tag de las 2 features para testear todos los
                                        // casos de prueba (5 en total)
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class RunnerTest {
}