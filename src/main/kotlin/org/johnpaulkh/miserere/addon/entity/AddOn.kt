package org.johnpaulkh.miserere.addon.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "addons")
data class AddOn(
    @Id
    @UuidGenerator
    val id: String? = null,
    val name: String,
    val price: Long,
)
