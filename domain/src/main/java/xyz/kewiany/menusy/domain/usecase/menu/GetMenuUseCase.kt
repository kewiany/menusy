package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.domain.repository.MenuRepository
import xyz.kewiany.menusy.model.Menu
import javax.inject.Inject

class GetMenuUseCase @Inject constructor(private val menuRepository: MenuRepository) {

    private val logger = LoggerFactory.getLogger(GetMenuUseCase::class.java)

    suspend operator fun invoke(menuId: String): Result<Menu> = withContext(Dispatchers.IO) {
        try {
            val data = menuRepository.getMenu(menuId)
            Result.Success(data)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            logger.warn(e.stackTraceToString(), e)
            Result.Error(e)
        }
    }
}