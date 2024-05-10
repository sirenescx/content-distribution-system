package com.cds.itemkeeper.repositories

import com.cds.itemkeeper.models.Source
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface SourceRepository : JpaRepository<Source, UUID> {
    @Query("select source from Source source where source.name = :#{#name}")
    fun findByName(@Param("name") name: String): Source?
}