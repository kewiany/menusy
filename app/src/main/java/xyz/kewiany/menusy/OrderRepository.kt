package xyz.kewiany.menusy

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface OrderRepository {
    val order: StateFlow<List<Order>>
    fun updateOrder(productId: String, quantity: Int)
}

class OrderRepositoryImpl @Inject constructor() : OrderRepository {

    private val _order = MutableStateFlow<List<Order>>(emptyList())
    override val order = _order.asStateFlow()

    override fun updateOrder(productId: String, quantity: Int) {
        val mutableList = order.value.toMutableList()
        val inOrder = mutableList.firstOrNull { it.productId == productId }
            ?.run { true }
            ?: false

        if (inOrder) {
            val index = mutableList.indexOfFirst { it.productId == productId }
            mutableList.removeAt(index)
            if (quantity > 0) {
                mutableList.add(index, Order(productId, quantity))
            }
        } else {
            mutableList.add(Order(productId, quantity))
        }
        _order.tryEmit(mutableList)
    }
}