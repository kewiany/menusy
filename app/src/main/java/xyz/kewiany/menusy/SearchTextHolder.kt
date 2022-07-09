package xyz.kewiany.menusy

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject


interface SearchTextHolder {
    val searchText: SharedFlow<String>
    fun setSearchText(text: String)
}

class SearchTextHolderImpl @Inject constructor() : SearchTextHolder {

    private val _searchText = MutableSharedFlow<String>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val searchText: SharedFlow<String> = _searchText

    override fun setSearchText(text: String) {
        _searchText.tryEmit(text)
    }
}