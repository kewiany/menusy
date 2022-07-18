package xyz.kewiany.menusy.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import xyz.kewiany.menusy.data.source.remote.api.MenuApi
import xyz.kewiany.menusy.domain.model.Menu
import xyz.kewiany.menusy.domain.usecase.GetMenusError.Unknown
import xyz.kewiany.menusy.domain.usecase.GetMenusResponse.Error
import xyz.kewiany.menusy.domain.usecase.GetMenusResponse.Success
import javax.inject.Inject

interface GetMenusUseCase {
    suspend operator fun invoke(): GetMenusResponse
}

class GetMenusUseCaseImpl @Inject constructor(private val menuApi: MenuApi) : GetMenusUseCase {

    override suspend fun invoke(): GetMenusResponse = withContext(Dispatchers.IO) {
        try {
            val response = menuApi.getMenus()
            val data = response?.menus
            delay(1500)
            Success(requireNotNull(data))
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