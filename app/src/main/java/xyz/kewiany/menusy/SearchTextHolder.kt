package xyz.kewiany.menusy

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface SearchTextHolder {
    val searchText: StateFlow<String>
    fun setSearchText(text: String)
}

class SearchTextHolderImpl @Inject constructor() : SearchTextHolder {

    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    override val searchText: StateFlow<String> = _searchText

    override fun setSearchText(text: String) {
        _searchText.tryEmit(text)
    }
}