package xyz.kewiany.menusy.ui.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import xyz.kewiany.menusy.ui.welcome.WelcomeViewModel.Event

@Composable
fun WelcomeScreen(
    state: State<WelcomeViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val showProgress = state.value.showProgress
            Text(
                text = showProgress.toString()
            )
            Button(onClick = {
                eventHandler(Event.ShowProgress(!showProgress))
            }) {
                Text(text = "Change")
            }
            Button(onClick = {
                eventHandler(Event.FoodButtonClicked)
            }) {
                Text(text = "Menu")
            }
        }
    }
}