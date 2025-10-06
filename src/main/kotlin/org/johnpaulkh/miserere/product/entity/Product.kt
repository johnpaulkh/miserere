package org.johnpaulkh.miserere.product.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "products")
data class Product(
    @Id
    @UuidGenerator
    val id: String? = null,
    val name: String,
)

@Entity
@Table(name = "variants")
data class Variant(
    @Id
    @UuidGenerator
    val id: String? = null,
    val productId: String,
    val name: String,
    val price: Long,
    val cogs: Long,
)
