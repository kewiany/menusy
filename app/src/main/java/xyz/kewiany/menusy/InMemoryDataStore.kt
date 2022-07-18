package xyz.kewiany.menusy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import xyz.kewiany.menusy.domain.model.OrderedProduct
import javax.inject.Inject

interface InMemoryDataStore {
    val orderedProducts: StateFlow<List<OrderedProduct>>
    val orderedProductsCount: Flow<Int>
    suspend fun updateOrderedProducts(orderedProducts: List<OrderedProduct>)
}

class InMemoryDataStoreImpl @Inject constructor() : InMemoryDataStore {

    private val _orderedProducts: MutableStateFlow<List<OrderedProduct>> = MutableStateFlow(emptyList())
    override val orderedProducts: StateFlow<List<OrderedProduct>> = _orderedProducts

    override val orderedProductsCount: Flow<Int> = _orderedProducts
        .mapLatest {
            var quantity = 0; it.forEach { quantity += it.quantity }; quantity
        }

    override suspend fun updateOrderedProducts(orderedProducts: List<OrderedProduct>) {
        _orderedProducts.emit(orderedProducts)
    }
}