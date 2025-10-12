package org.johnpaulkh.miserere.sales.service

import jakarta.transaction.Transactional
import org.johnpaulkh.miserere.common.dto.Paginated
import org.johnpaulkh.miserere.common.util.toInstant
import org.johnpaulkh.miserere.common.util.today
import org.johnpaulkh.miserere.common.util.tommorow
import org.johnpaulkh.miserere.product.repository.ProductRepository
import org.johnpaulkh.miserere.product.repository.VariantRepository
import org.johnpaulkh.miserere.sales.dto.SalesCreateDto
import org.johnpaulkh.miserere.sales.dto.SalesDto
import org.johnpaulkh.miserere.sales.entity.Sales
import org.johnpaulkh.miserere.sales.entity.SalesDetail
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
    private val productRepository: ProductRepository,
    private val variantRepository: VariantRepository,
) {
    @Transactional
    fun create(request: SalesCreateDto): SalesDto {
        val productIds = request.details.map { it.productId }
        val productMap = productRepository.findAllById(productIds).associateBy { it.id }
        val variantMap = variantRepository.findAllByProductIdIn(productIds).associateBy { it.id }

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
                        productName = productMap[detail.productId]?.name ?: "",
                        variantId = detail.variantId,
                        variantName = variantMap[detail.variantId]?.name ?: "",
                        price = detail.price,
                        cogs = detail.cogs,
                        quantity = detail.quantity,
                        adminFee = detail.adminFee,
                        packingFee = detail.packingFee,
                        packingFeePaid = detail.packingFeePaid,
                    )
                }.let { salesDetailRepository.saveAll(it) }

        return SalesDto.fromEntity(sales, details)
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

        val salesResponse =
            salesPage
                .toList()
                .map {
                    SalesDto.fromEntity(
                        sales = it,
                        details = detailsMapBySalesId[it.id] ?: emptyList(),
                    )
                }

        return Paginated.fromPage(salesPage, salesResponse)
    }
}
