package xyz.kewiany.menusy

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

interface SearchTextHolder {
    val searchText: SharedFlow<String>
    fun setSearchText(text: String)
}

class SearchTextHolderImpl @Inject constructor() : SearchTextHolder {

    private val _searchText = MutableSharedFlow<String>(extraBufferCapacity = 1)
    override val searchText = _searchText.asSharedFlow()

    override fun setSearchText(text: String) {
        _searchText.tryEmit(text)
    }
}