package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.common.Result
import xyz.kewiany.menusy.domain.model.Menu
import xyz.kewiany.menusy.domain.repository.MenuRepository
import javax.inject.Inject

class GetMenuUseCase @Inject constructor(private val menuRepository: MenuRepository) {

    suspend operator fun invoke(menuId: String): Result<Menu> = withContext(Dispatchers.IO) {
        try {
            val data = menuRepository.getMenu(menuId)
            delay(1500)
            Result.Success(data)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}