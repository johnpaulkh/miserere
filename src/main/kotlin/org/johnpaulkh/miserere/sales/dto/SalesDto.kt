package org.johnpaulkh.miserere.sales.dto

import org.johnpaulkh.miserere.common.util.toDateString
import org.johnpaulkh.miserere.sales.entity.Sales
import org.johnpaulkh.miserere.sales.entity.SalesDetail

data class SalesDto(
    val id: String? = null,
    val date: String,
    val customer: String,
    val address: String,
    val logistic: String,
    val details: List<SalesDetailDto> = emptyList(),
) {
    companion object {
        fun fromEntity(
            sales: Sales,
            details: List<SalesDetail>,
        ) = SalesDto(
            id = sales.id,
            date = sales.date.toDateString(),
            customer = sales.customer,
            logistic = sales.logistic,
            address = sales.address,
            details =
                details.map { detail ->
                    SalesDetailDto(
                        id = detail.id,
                        productId = detail.productId,
                        productName = detail.productName,
                        variantId = detail.variantId,
                        variantName = detail.variantName,
                        price = detail.price,
                        cogs = detail.cogs,
                        quantity = detail.quantity,
                        adminFee = detail.adminFee,
                        packingFee = detail.packingFee,
                        packingFeePaid = detail.packingFeePaid,
                    )
                },
        )
    }

    data class SalesDetailDto(
        val id: String? = null,
        val productId: String,
        val productName: String,
        val variantId: String,
        val variantName: String,
        val price: Long,
        val cogs: Long,
        val quantity: Int,
        val adminFee: Long,
        val packingFee: Long,
        val packingFeePaid: Long,
    )
}

data class SalesCreateDto(
    val date: String,
    val customer: String,
    val logistic: String,
    val address: String,
    val details: List<SalesDetailCreateDto>,
) {
    data class SalesDetailCreateDto(
        val productId: String,
        val variantId: String,
        val quantity: Int,
        val price: Long,
        val cogs: Long,
        val adminFee: Long,
        val packingFee: Long,
        val packingFeePaid: Long,
    )
}
