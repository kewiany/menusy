package xyz.kewiany.menusy

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

interface OrderRepository {
    val order: SharedFlow<List<Order>>
    fun updateOrder(productId: String, quantity: Int)
}

class OrderRepositoryImpl @Inject constructor() : OrderRepository {

    override val order = MutableSharedFlow<List<Order>>(extraBufferCapacity = 1)
    private val _order: MutableList<Order> = mutableListOf()

    override fun updateOrder(productId: String, quantity: Int) {
        val inOrder = _order.firstOrNull { it.productId == productId }?.run { true } ?: false

        when {
            inOrder && quantity == 0 -> {
                val index = _order.indexOfFirst { it.productId == productId }
                _order.removeAt(index)
            }
            inOrder -> {
                val index = _order.indexOfFirst { it.productId == productId }
                _order.removeAt(index)
                _order.add(index, Order(productId, quantity))
            }
            else -> {
                _order.add(Order(productId, quantity))
            }
        }
        order.tryEmit(_order)
    }
}