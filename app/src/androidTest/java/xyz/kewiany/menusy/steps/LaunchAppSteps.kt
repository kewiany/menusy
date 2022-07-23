package xyz.kewiany.menusy.steps

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import io.cucumber.java.en.Given
import xyz.kewiany.menusy.ActivityScenarioHolder
import xyz.kewiany.menusy.core.MainActivity

class LaunchAppSteps {

    @Given("I launch the app")
    fun iLaunchTheApp() {
        val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, MainActivity::class.java)
        ActivityScenarioHolder().launch(intent)
    }
}