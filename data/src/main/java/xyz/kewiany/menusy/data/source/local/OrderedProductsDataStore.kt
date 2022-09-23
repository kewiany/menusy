package xyz.kewiany.menusy.data.source.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.kewiany.menusy.data.source.local.dao.CacheDao
import xyz.kewiany.menusy.data.source.local.entity.toCachedOrderedProduct
import xyz.kewiany.menusy.data.source.local.entity.toOrderedProduct
import xyz.kewiany.menusy.domain.model.OrderedProduct
import javax.inject.Inject

interface OrderedProductsDataStore {
    val orderedProductsCount: Flow<Int>
    suspend fun getOrderedProducts(): List<OrderedProduct>
    suspend fun updateOrderedProducts(orderedProducts: List<OrderedProduct>)
}

class OrderedProductsDataStoreImpl @Inject constructor(
    private val cacheDao: CacheDao,
) : OrderedProductsDataStore {

    override val orderedProductsCount: Flow<Int> = cacheDao.getQuantity()
        .map(List<Int>::sum)

    override suspend fun getOrderedProducts(): List<OrderedProduct> {
        return cacheDao.getAll()
            .map { cachedOrderedProduct -> cachedOrderedProduct.toOrderedProduct() }
    }

    override suspend fun updateOrderedProducts(orderedProducts: List<OrderedProduct>) {
        val cachedOrderedProducts = orderedProducts.map { it.toCachedOrderedProduct() }
        cacheDao.deleteAll()
        cacheDao.insertAll(cachedOrderedProducts)
    }
}