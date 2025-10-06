package org.johnpaulkh.miserere.addon.service

import org.johnpaulkh.miserere.addon.dto.AddOnDto
import org.johnpaulkh.miserere.addon.entity.AddOn
import org.johnpaulkh.miserere.addon.repository.AddOnRepository
import org.springframework.stereotype.Service

@Service
class AddOnService(
    private val addOnRepository: AddOnRepository,
) {

    fun create(
        request: AddOnDto
    ) = request
        .let {
            AddOn(
                name = it.name,
                price = it.price,
            )
        }
        .let { addOnRepository.save(it) }
        .let { AddOnDto.fromEntity(it) }

    fun list() = addOnRepository.findAll().map { AddOnDto.fromEntity(it) }
}