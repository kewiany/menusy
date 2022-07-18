package xyz.kewiany.menusy.domain.usecase.search

import xyz.kewiany.menusy.domain.repository.SearchRepository
import javax.inject.Inject

class ClearSearchTextUseCase @Inject constructor(private val searchRepository: SearchRepository) {

    operator fun invoke() {
        searchRepository.clearSearchText()
    }
}