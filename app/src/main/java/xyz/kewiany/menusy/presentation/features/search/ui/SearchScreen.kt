package xyz.kewiany.menusy.presentation.features.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import xyz.kewiany.menusy.common.ui.ErrorDialog
import xyz.kewiany.menusy.presentation.features.menu.items.ui.ProductItem
import xyz.kewiany.menusy.presentation.features.search.SearchViewModel
import xyz.kewiany.menusy.presentation.features.search.SearchViewModel.Event
import xyz.kewiany.menusy.presentation.utils.ProductUiItem
import xyz.kewiany.menusy.presentation.utils.ProgressBar

@Composable
fun SearchScreen(
    state: State<SearchViewModel.State>,
    eventHandler: (Event) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val results = state.value.results
        if (results.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(1f),
            ) {
                items(state.value.results) { item ->
                    item as ProductUiItem
                    ProductItem(
                        item.id,
                        item.name,
                        item.description,
                        item.price,
                        item.quantity,
                        { id -> eventHandler(Event.DecreaseQuantityClicked(id)) },
                        { id -> eventHandler(Event.IncreaseQuantityClicked(id)) }
                    )
                }
            }
        } else {
            Text(
                text = "No results"
            )
        }
        if (state.value.showLoading) {
            ProgressBar()
        }
    }
    if (state.value.showError) {
        ErrorDialog(
            onDismissRequest = { eventHandler(Event.TriggerDismissError) },
            onOKClicked = { eventHandler(Event.ErrorOKClicked) }
        )
    }
}