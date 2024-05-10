package com.cds.itemkeeper.repositories

import com.cds.itemkeeper.models.RssItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface RssItemRepository : JpaRepository<RssItem, UUID> {
    @Query("SELECT item FROM RssItem item WHERE item.deletedAt IS NULL")
    fun findAllActive(): List<RssItem>
}