package org.johnpaulkh.miserere.sales.service

import jakarta.transaction.Transactional
import org.johnpaulkh.miserere.common.dto.Paginated
import org.johnpaulkh.miserere.common.util.toInstant
import org.johnpaulkh.miserere.common.util.today
import org.johnpaulkh.miserere.common.util.tommorow
import org.johnpaulkh.miserere.sales.dto.SalesDto
import org.johnpaulkh.miserere.sales.entity.Sales
import org.johnpaulkh.miserere.sales.entity.SalesAddOn
import org.johnpaulkh.miserere.sales.entity.SalesDetail
import org.johnpaulkh.miserere.sales.repository.SalesAddOnRepository
import org.johnpaulkh.miserere.sales.repository.SalesDetailRepository
import org.johnpaulkh.miserere.sales.repository.SalesRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.stereotype.Service
import java.time.temporal.ChronoUnit

@Service
class SalesService(
    private val salesRepository: SalesRepository,
    private val salesDetailRepository: SalesDetailRepository,
    private val salesAddOnRepository: SalesAddOnRepository,
) {
    @Transactional
    fun create(request: SalesDto): SalesDto {
        val sales =
            Sales(
                date = request.date.toInstant()!!,
                customer = request.customer,
                address = request.address,
                logistic = request.logistic,
            ).let { salesRepository.save(it) }

        val details =
            request.details
                .map { detail ->
                    SalesDetail(
                        salesId = sales.id!!,
                        productId = detail.productId,
                        productName = detail.productName,
                        variantId = detail.variantId,
                        variantName = detail.variantName,
                        price = detail.price,
                        cogs = detail.cogs,
                        quantity = detail.quantity,
                    )
                }.let { salesDetailRepository.saveAll(it) }

        val addOns =
            request.addOns
                ?.map { addOn ->
                    SalesAddOn(
                        salesId = sales.id!!,
                        addOnId = addOn.addOnId,
                        addOnName = addOn.addOnName,
                        addOnPrice = addOn.addOnPrice,
                        quantity = addOn.quantity,
                    )
                }?.let { salesAddOnRepository.saveAll(it) }

        return SalesDto.fromEntity(sales, details, addOns)
    }

    fun list(
        page: Int,
        size: Int,
        startDateS: String? = null,
        endDateS: String? = null,
    ): Paginated<SalesDto> {
        val startDate = startDateS?.toInstant() ?: today().minus(7, ChronoUnit.DAYS)
        val endDate = endDateS.toInstant() ?: tommorow()

        val pageable = PageRequest.of(page - 1, size, Sort.by(Direction.DESC, "date"))
        val salesPage = salesRepository.findByDateBetween(startDate, endDate, pageable)

        val salesIds = salesPage.toList().map { it.id!! }

        val detailsMapBySalesId =
            salesDetailRepository
                .findAllBySalesIdIn(salesIds)
                .groupBy { it.salesId }
        val addOnsMapBySalesId =
            salesAddOnRepository
                .findAllBySalesIdIn(salesIds)
                .groupBy { it.salesId }

        val salesResponse =
            salesPage
                .toList()
                .map {
                    SalesDto.fromEntity(
                        sales = it,
                        details = detailsMapBySalesId[it.id]!!,
                        addOns = addOnsMapBySalesId[it.id],
                    )
                }

        return Paginated.fromPage(salesPage, salesResponse)
    }
}
