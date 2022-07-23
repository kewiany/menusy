package xyz.kewiany.menusy.test

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    glue = ["xyz.kewiany.menusy.steps"],
    features = ["features"],
    strict = true,
)
class CucumberOptions