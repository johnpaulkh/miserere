package org.johnpaulkh.miserere.addon.dto

import org.johnpaulkh.miserere.addon.entity.AddOn

data class AddOnDto(
    val id: String? = null,
    val name: String,
    val price: Long,
) {
    companion object {
        fun fromEntity(
            entity: AddOn
        ) = AddOnDto(
            id = entity.id,
            name = entity.name,
            price = entity.price,
        )
    }
}