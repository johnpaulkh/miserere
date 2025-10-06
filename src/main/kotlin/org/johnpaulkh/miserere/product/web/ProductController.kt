package org.johnpaulkh.miserere.product.web

import org.johnpaulkh.miserere.product.dto.ProductDto
import org.johnpaulkh.miserere.product.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService,
) {

    @GetMapping("/{id}")
    fun get(
        @PathVariable id: String,
    ) = productService.get(id)

    @GetMapping
    fun list(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) search: String? = null,
    ) = productService.list(page, size, search)

    @PostMapping
    fun create(
        @RequestBody request: ProductDto
    ) = productService.create(request)
}