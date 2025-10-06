package org.johnpaulkh.miserere.sales.repository

import org.johnpaulkh.miserere.sales.entity.Sales
import org.johnpaulkh.miserere.sales.entity.SalesAddOn
import org.johnpaulkh.miserere.sales.entity.SalesDetail
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface SalesRepository : JpaRepository<Sales, String> {
    @Query(
        """
            select s
            from Sales s
            where (:startDate is null or s.date >= :startDate)
              and (:endDate is null or s.date <= :endDate)
        """
    )
    fun findByDateBetween(
        @Param("startDate") startDate: Instant? = null,
        @Param("endDate") endDate: Instant? = null,
        pageable: Pageable,
    ) : Page<Sales>
}

interface SalesDetailRepository : JpaRepository<SalesDetail, String> {

    fun findAllBySalesIdIn(salesIds: List<String>) : List<SalesDetail>
}

interface SalesAddOnRepository : JpaRepository<SalesAddOn, String> {

    fun findAllBySalesIdIn(salesIds: List<String>) : List<SalesAddOn>
}