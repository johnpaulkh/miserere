package org.johnpaulkh.miserere.sales.repository

import org.johnpaulkh.miserere.sales.entity.Sales
import org.johnpaulkh.miserere.sales.entity.SalesDetail
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.Instant

interface SalesRepository : JpaRepository<Sales, String> {
    @Query(
        nativeQuery = true,
        value = """
            SELECT *
            FROM sales s
            WHERE s.date >= ?1
              AND s.date <= ?2
        """,
    )
    fun findByDateBetween(
        startDate: Instant,
        endDate: Instant,
        pageable: Pageable,
    ): Page<Sales>
}

interface SalesDetailRepository : JpaRepository<SalesDetail, String> {
    fun findAllBySalesIdIn(salesIds: List<String>): List<SalesDetail>
}
