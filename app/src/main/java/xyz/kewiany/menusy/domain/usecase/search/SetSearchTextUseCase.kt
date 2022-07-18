package xyz.kewiany.menusy.domain.usecase.search

import xyz.kewiany.menusy.domain.repository.SearchRepository
import javax.inject.Inject

class SetSearchTextUseCase @Inject constructor(private val searchRepository: SearchRepository) {

    operator fun invoke(text: String) {
        searchRepository.setSearchText(text)
    }
}