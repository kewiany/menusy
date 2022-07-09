package xyz.kewiany.menusy

import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

interface SearchRepository {
    val searchText: SharedFlow<String>
    fun setSearchText(text: String)
    fun clearSearchText()
}

class SearchRepositoryImpl @Inject constructor(
    private val searchTextHolder: SearchTextHolder
) : SearchRepository {

    override val searchText: SharedFlow<String> = searchTextHolder.searchText

    override fun setSearchText(text: String) {
        searchTextHolder.setSearchText(text)
    }

    override fun clearSearchText() {
        searchTextHolder.setSearchText("")
    }
}