package org.johnpaulkh.miserere.sales.web

import org.johnpaulkh.miserere.sales.dto.SalesCreateDto
import org.johnpaulkh.miserere.sales.service.SalesService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/sales")
class SalesController(
    private val salesService: SalesService,
) {
    @GetMapping
    fun list(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) startDate: String? = null,
        @RequestParam(required = false) endDate: String? = null,
    ) = salesService.list(page, size, startDate, endDate)

    @PostMapping
    fun create(
        @RequestBody request: SalesCreateDto,
    ) = salesService.create(request)
}
