package org.johnpaulkh.miserere.common.dto

import org.springframework.data.domain.Page

data class Paginated<T>(
    val size: Int,
    val count: Long,
    val totalPages: Int,
    val data: List<T>
) {
    companion object {
        fun <T> fromPage(
            page: Page<out Any>,
            data: List<T>,
        ) = Paginated<T>(
            size = page.size,
            count = page.totalElements,
            totalPages = page.totalPages,
            data = data
        )
    }
}
