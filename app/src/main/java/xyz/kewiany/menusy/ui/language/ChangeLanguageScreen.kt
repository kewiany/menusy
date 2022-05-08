package xyz.kewiany.menusy.ui.language

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import xyz.kewiany.menusy.ui.language.ChangeLanguageViewModel.Event

@Composable
fun ChangeLanguageScreen(
    state: State<ChangeLanguageViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    Dialog(onDismissRequest = {

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
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = {

                    }) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {
                    }) {
                        Text(text = "Ok")
                    }
                }
            }
        }
    }
}