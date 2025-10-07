package org.johnpaulkh.miserere.product.service

import org.johnpaulkh.miserere.common.exception.InvalidParameterException
import org.johnpaulkh.miserere.common.exception.MissingParameterException
import org.johnpaulkh.miserere.common.exception.NotFoundException
import org.johnpaulkh.miserere.product.dto.ProductDto
import org.johnpaulkh.miserere.product.entity.Variant
import org.johnpaulkh.miserere.product.repository.ProductRepository
import org.johnpaulkh.miserere.product.repository.VariantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class VariantService(
    private val productRepository: ProductRepository,
    private val variantRepository: VariantRepository,
) {
    fun update(request: ProductDto.VariantDto): ProductDto.VariantDto {
        val variantId =
            request.id ?: throw MissingParameterException(
                code = "VAR-400",
                message = "Missing variant id",
            )
        val variant =
            variantRepository.findByIdOrNull(variantId)
                ?: throw NotFoundException(
                    code = "VAR-404",
                    message = "Variant with id ${request.id} not found",
                )

        return variantRepository
            .save(
                Variant(
                    id = variantId,
                    productId = variant.productId,
                    name = request.name,
                    price = request.price,
                    cogs = request.cogs,
                ),
            ).let(ProductDto.VariantDto::fromEntity)
    }

    fun create(request: ProductDto.VariantDto): ProductDto.VariantDto {
        val productId =
            request.productId ?: throw MissingParameterException(code = "VAR-400", message = "Missing product id")
        val product =
            productRepository.findByIdOrNull(productId) ?: throw NotFoundException(
                code = "PRD-404",
                message = "Produk dengan id $productId tidak ditemukan",
            )

        val variants = variantRepository.findByProductId(productId)
        val isUnique = variants.none { it.name == request.name }

        if (!isUnique) {
            throw InvalidParameterException(
                code = "VAR-400",
                message = "Variant ${request.name} untuk produk ${product.name} sudah ada",
            )
        }

        return variantRepository
            .save(
                Variant(
                    productId = productId,
                    name = request.name,
                    price = request.price,
                    cogs = request.cogs,
                ),
            ).let(ProductDto.VariantDto::fromEntity)
    }
}
