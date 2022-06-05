package xyz.kewiany.menusy.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(
    state: State<SearchViewModel.State>,
    eventHandler: (SearchViewModel.Event) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val results = state.value.results
        if (results.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(1f),
            ) {
                items(state.value.results) { item ->
                    SearchItem(
                        id = item.id,
                        name = item.name,
                        onItemClicked = {
                            eventHandler(SearchViewModel.Event.SearchItemClicked(item.id))
                        })
                }
            }
        } else {
            Text(
                text = "No results"
            )
        }
    }
}

@Composable
private fun SearchItem(
    id: String,
    name: String,
    onItemClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onItemClicked(id) }
            .background(Color.Yellow)
            .padding(10.dp)
    ) {
        Text(
            text = "$id $name",
        )
    }

}