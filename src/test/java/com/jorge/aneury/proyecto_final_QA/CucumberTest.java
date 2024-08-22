package com.jorge.aneury.proyecto_final_QA;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",glue = {"com.jorge.aneury.proyecto_final_QA.steps"})
@CucumberContextConfiguration
public class CucumberTest {
}
