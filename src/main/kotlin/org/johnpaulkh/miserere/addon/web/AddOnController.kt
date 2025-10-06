package org.johnpaulkh.miserere.addon.web

import org.johnpaulkh.miserere.addon.dto.AddOnDto
import org.johnpaulkh.miserere.addon.service.AddOnService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/addons")
class AddOnController(
    private val addOnService: AddOnService,
) {

    @GetMapping
    fun list() = addOnService.list()

    @PostMapping
    fun create(@RequestBody request: AddOnDto) = addOnService.create(request)
}