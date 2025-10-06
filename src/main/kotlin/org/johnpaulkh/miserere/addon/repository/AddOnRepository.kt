package org.johnpaulkh.miserere.addon.repository

import org.johnpaulkh.miserere.addon.entity.AddOn
import org.springframework.data.jpa.repository.JpaRepository

interface AddOnRepository : JpaRepository<AddOn, String> {

}