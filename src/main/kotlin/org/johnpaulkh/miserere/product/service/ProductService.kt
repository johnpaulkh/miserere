package org.johnpaulkh.miserere.product.service

import jakarta.transaction.Transactional
import org.johnpaulkh.miserere.common.dto.Paginated
import org.johnpaulkh.miserere.common.exception.NotFoundException
import org.johnpaulkh.miserere.product.dto.ProductDto
import org.johnpaulkh.miserere.product.entity.Product
import org.johnpaulkh.miserere.product.entity.Variant
import org.johnpaulkh.miserere.product.repository.ProductRepository
import org.johnpaulkh.miserere.product.repository.VariantRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val variantRepository: VariantRepository,
) {
    fun get(id: String): ProductDto {
        val product =
            productRepository.findByIdOrNull(id)
                ?: throw NotFoundException(
                    message = "No product with id : $id found",
                    code = "PRD-404",
                )

        val variants = variantRepository.findByProductId(id)

        if (variants.isEmpty()) {
            throw NotFoundException(
                message = "No variant found for product ${product.name}",
                code = "PRD-VAR-404",
            )
        }

        return ProductDto.fromEntity(product, variants)
    }

    fun list(
        page: Int = 1,
        size: Int = 10,
        search: String? = null,
    ): Paginated<ProductDto> {
        val pageable = PageRequest.of(page - 1, size, Sort.by("name"))

        val productPage =
            when (search != null) {
                true -> productRepository.findBySearch(search, pageable)
                else -> productRepository.findAll(pageable)
            }.map {
                val variants = variantRepository.findByProductId(it.id!!)
                ProductDto.fromEntity(it, variants)
            }

        return Paginated.fromPage(
            productPage,
            data = productPage.toList(),
        )
    }

    @Transactional
    fun create(request: ProductDto): ProductDto {
        val product =
            Product(
                name = request.name,
                adminFeePercentage = request.adminFeePercentage,
            ).let {
                productRepository.save(it)
            }

        val variants =
            request.variants
                .map { variant ->
                    Variant(
                        productId = product.id!!,
                        name = variant.name,
                        price = variant.price,
                        cogs = variant.cogs,
                    )
                }.let { variantRepository.saveAll(it) }

        return ProductDto.fromEntity(product, variants)
    }

    @Transactional
    fun update(
        id: String,
        request: ProductDto,
    ): ProductDto {
        val product =
            productRepository.findByIdOrNull(id)
                ?: throw NotFoundException(message = "Product with id : $id not found", code = "PRD-404")

        val variants = variantRepository.findByProductId(id)

        return product
            .copy(
                name = request.name,
                adminFeePercentage = request.adminFeePercentage,
            ).let(productRepository::save)
            .let { ProductDto.fromEntity(product, variants) }
    }
}
