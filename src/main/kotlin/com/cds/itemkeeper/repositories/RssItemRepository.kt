package com.cds.itemkeeper.repositories

import com.cds.itemkeeper.models.RssItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface RssItemRepository : JpaRepository<RssItem, UUID> {
    @Query("select item from RssItem item where item.deletedAt is null")
    fun findAllActive(): List<RssItem>
}