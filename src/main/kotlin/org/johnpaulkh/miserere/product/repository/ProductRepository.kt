package org.johnpaulkh.miserere.product.repository

import org.johnpaulkh.miserere.product.entity.Product
import org.johnpaulkh.miserere.product.entity.Variant
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductRepository : JpaRepository<Product, String> {

    @Query(
        """
            select distinct p
            from Product p
            join Variant v on p.id = v.productId
            where
                lower(p.name) like lower(concat('%', :search, '%'))
                or
                lower(v.name) like lower(concat('%', :search, '%'))
        """
    )
    fun findBySearch(
        @Param("search") search: String,
        pageable: Pageable
    ): Page<Product>

}

interface VariantRepository : JpaRepository<Variant, String> {
    fun findByProductId(id: String): List<Variant>
}