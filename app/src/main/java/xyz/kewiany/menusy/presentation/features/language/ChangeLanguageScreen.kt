package xyz.kewiany.menusy.presentation.features.language

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import xyz.kewiany.menusy.presentation.features.language.ChangeLanguageViewModel.Event

@Composable
fun ChangeLanguageScreen(
    state: State<ChangeLanguageViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    Dialog(onDismissRequest = {
        eventHandler(Event.OutsideClicked)
    }) {
        Card(
            modifier = Modifier.padding(12.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp,
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Change language",
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                val currentLanguage = state.value.currentLanguage
                val languages = state.value.languages
                languages.forEach { language ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (currentLanguage == language) Color.Red else Color.Yellow)
                            .clickable { eventHandler(Event.LanguageClicked(language)) },
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(20.dp),
                            text = language.name
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = {
                        eventHandler(Event.CancelClicked)
                    }) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {
                        eventHandler(Event.OKClicked)
                    }) {
                        Text(text = "Ok")
                    }
                }
            }
        }
    }
}