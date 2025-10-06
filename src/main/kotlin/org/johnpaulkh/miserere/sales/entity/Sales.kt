package org.johnpaulkh.miserere.sales.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.UuidGenerator
import java.time.Instant

@Entity
@Table(name = "sales")
data class Sales(
    @Id
    @UuidGenerator
    val id: String? = null,

    val date: Instant,
    val customer: String,
    val address: String,
    val logistic: String,
)

@Entity
@Table(name = "sales_details")
data class SalesDetail(
    @Id
    @UuidGenerator
    val id: String? = null,

    val salesId: String,
    val productId: String,
    val productName: String,
    val variantId: String,
    val variantName: String,
    val price: Long,
    val cogs: Long,
    val quantity: Int,
)

@Entity
@Table(name = "sales_addons")
data class SalesAddOn(
    @Id
    @UuidGenerator
    val id: String? = null,

    val salesId: String,
    val addOnId: String,
    val addOnName: String,
    val addOnPrice: Long,
    val quantity: Int,
)
