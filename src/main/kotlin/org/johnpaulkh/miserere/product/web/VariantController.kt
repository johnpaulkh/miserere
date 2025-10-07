package org.johnpaulkh.miserere.product.web

import org.johnpaulkh.miserere.product.dto.ProductDto
import org.johnpaulkh.miserere.product.service.VariantService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class VariantController(
    private val variantService: VariantService,
) {
    @PostMapping("/{productId}/variants")
    fun createVariant(
        @PathVariable productId: String,
        @RequestBody request: ProductDto.VariantDto,
    ) = request
        .copy(productId = productId)
        .let(variantService::create)

    @PutMapping("/{productId}/variants/{variantId}")
    fun createVariant(
        @PathVariable productId: String,
        @PathVariable variantId: String,
        @RequestBody request: ProductDto.VariantDto,
    ) = request
        .copy(productId = productId, id = variantId)
        .let(variantService::update)
}
