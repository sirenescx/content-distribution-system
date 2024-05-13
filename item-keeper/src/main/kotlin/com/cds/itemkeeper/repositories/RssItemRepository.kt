package com.cds.itemkeeper.repositories

import com.cds.itemkeeper.models.RssItem
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*

@Repository
interface RssItemRepository : PagingAndSortingRepository<RssItem, UUID>, JpaRepository<RssItem, UUID> {
    @Query("select item from RssItem item where item.deletedAt is null")
    fun findAllActive(pageable: Pageable): Page<RssItem>

    @Query("select item from RssItem item where item.deletedAt is null and item.sourceId = :#{#sourceId}")
    fun findAllActiveBySourceId(sourceId: UUID, pageable: Pageable): Page<RssItem>

    @Query(
        "select item from RssItem item where item.deletedAt is null and cast(item.publicationDate as LocalDate) = :#{#publicationDate}"
    )
    fun findAllActiveByPublicationDate(publicationDate: LocalDate, pageable: Pageable): Page<RssItem>

    @Query(
        "select item from RssItem item where item.deletedAt is null and cast(item.publicationDate as LocalDate) < :#{#date}"
    )
    fun findAllActiveAfterDate(date: LocalDate, pageable: Pageable): Page<RssItem>

    @Query(
        "select item from RssItem item where item.deletedAt is null and cast(item.publicationDate as LocalDate) > :#{#date}"
    )
    fun findAllActiveBeforeDate(date: LocalDate, pageable: Pageable): Page<RssItem>

    @Query(
        "select item from RssItem item where item.deletedAt is null and item.category = :#{#category}"
    )
    fun findAllActiveByCategory(category: String, pageable: Pageable): Page<RssItem>

    @Query("select item from RssItem item where item.guid = :#{#guid}")
    fun findByGuid(guid: String): RssItem?
}