package xyz.kewiany.menusy.common.ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(
    onDismissRequest: () -> Unit,
    onOKClicked: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            Text(text = "Error")
        },
        text = {
            Text("Something went wrong...")
        },
        confirmButton = {
            Button(
                onClick = {
                    onOKClicked()
                }) {
                Text("OK")
            }
        }
    )
}