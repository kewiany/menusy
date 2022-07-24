package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.core.DispatcherProvider
import xyz.kewiany.menusy.domain.model.Product
import xyz.kewiany.menusy.domain.repository.MenuRepository
import javax.inject.Inject

class GetProductsByQueryUseCase @Inject constructor(
    private val menuRepository: MenuRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(query: String): Result<List<Product>> = withContext(dispatcherProvider.io()) {
        try {
            val products = menuRepository.getProductsByQuery(query)
            delay(1500)
            Result.Success(products)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}