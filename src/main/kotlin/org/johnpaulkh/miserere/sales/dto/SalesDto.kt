package org.johnpaulkh.miserere.sales.dto

import org.johnpaulkh.miserere.common.util.toDateString
import org.johnpaulkh.miserere.sales.entity.Sales
import org.johnpaulkh.miserere.sales.entity.SalesAddOn
import org.johnpaulkh.miserere.sales.entity.SalesDetail

data class SalesDto(
    val id: String? = null,
    val date: String,
    val customer: String,
    val address: String,
    val logistic: String,
    val details: List<SalesDetailDto> = emptyList(),
    val addOns: List<SalesAddOnDto>? = emptyList(),
) {
    companion object {
        fun fromEntity(
            sales: Sales,
            details: List<SalesDetail>,
            addOns: List<SalesAddOn>? = null,
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
                    )
                },
            addOns =
                addOns?.map { addOn ->
                    SalesAddOnDto(
                        id = addOn.id,
                        addOnId = addOn.addOnId,
                        addOnName = addOn.addOnName,
                        addOnPrice = addOn.addOnPrice,
                        quantity = addOn.quantity,
                    )
                } ?: emptyList<SalesAddOnDto>(),
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
    )

    data class SalesAddOnDto(
        val id: String? = null,
        val addOnId: String,
        val addOnName: String,
        val addOnPrice: Long,
        val quantity: Int,
    )
}
