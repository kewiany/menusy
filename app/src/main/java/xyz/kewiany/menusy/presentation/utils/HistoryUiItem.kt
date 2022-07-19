package xyz.kewiany.menusy.presentation.utils

interface HistoryUiItem : UiItem

data class HistoryOrderUiItem(
    override val id: String,
    val date: String,
    val totalPrice: String,
    val totalQuantity: String,
) : HistoryUiItem

data class HistoryProductUiItem(
    override val id: String,
    val name: String,
    val description: String,
    val price: String,
    val quantity: String
) : HistoryUiItem