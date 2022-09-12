package xyz.kewiany.menusy.presentation.features.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import xyz.kewiany.menusy.presentation.features.welcome.WelcomeViewModel.Event

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
            Button(onClick = {
                eventHandler(Event.MenuButtonClicked)
            }) {
                Text(text = "Menu")
            }
        }
    }
}