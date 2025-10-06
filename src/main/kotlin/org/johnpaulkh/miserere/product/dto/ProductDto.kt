package org.johnpaulkh.miserere.product.dto

import org.johnpaulkh.miserere.product.entity.Product
import org.johnpaulkh.miserere.product.entity.Variant

data class ProductDto(
    val id: String? = null,
    val name: String,
    val variants: List<VariantDto>,
) {
    companion object {
        fun fromEntity(
            product: Product,
            variants: List<Variant>,
        ) = ProductDto(
            id = product.id,
            name = product.name,
            variants = variants.map { variant ->
                VariantDto(
                    id = variant.id,
                    name = variant.name,
                    price = variant.price,
                    cogs = variant.cogs,
                )
            }
        )
    }

    data class VariantDto(
        val id: String? = null,
        val name: String,
        val price: Long,
        val cogs: Long,
    )
}

data class ProductListDto(
    val id: String? = null,
    val name: String,
) {
    companion object {
        fun fromEntity(
            product: Product,
        ) = ProductListDto(
            id = product.id,
            name = product.name
        )
    }
}
