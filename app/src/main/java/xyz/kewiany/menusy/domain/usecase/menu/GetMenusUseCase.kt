package xyz.kewiany.menusy.domain.usecase.menu

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.domain.model.Menu
import xyz.kewiany.menusy.domain.repository.MenuRepository
import xyz.kewiany.menusy.domain.usecase.menu.GetMenusError.Unknown
import xyz.kewiany.menusy.domain.usecase.menu.GetMenusResponse.Error
import xyz.kewiany.menusy.domain.usecase.menu.GetMenusResponse.Success
import javax.inject.Inject

class GetMenusUseCase @Inject constructor(private val menuRepository: MenuRepository) {

    suspend operator fun invoke(): GetMenusResponse = withContext(Dispatchers.IO) {
        try {
            val data = menuRepository.getMenus()
            delay(1500)
            Success(data)
        } catch (e: Exception) {
            Error(Unknown)
        }
    }
}

sealed class GetMenusResponse {
    data class Success(val menus: List<Menu>) : GetMenusResponse()
    data class Error(val error: GetMenusError) : GetMenusResponse()
}

sealed class GetMenusError {
    object Unknown : GetMenusError()
}