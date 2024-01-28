package com.cds.itemkeeper.repositories

import com.cds.itemkeeper.models.RssItem
import org.springframework.data.jpa.repository.JpaRepository

interface RssItemRepository : JpaRepository<RssItem, Long>