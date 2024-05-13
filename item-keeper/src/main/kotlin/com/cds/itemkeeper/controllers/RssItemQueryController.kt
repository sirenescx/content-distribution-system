package com.cds.itemkeeper.controllers

import com.cds.itemkeeper.models.RssItem
import com.cds.itemkeeper.models.RssItemInput
import com.cds.itemkeeper.repositories.RssItemRepository
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.sql.Timestamp
import java.time.LocalDate
import java.util.*


@Controller
class RssItemQueryController(private val rssItemRepository: RssItemRepository) : Query {
    @QueryMapping("rssItems")
    @GraphQLDescription("Returns all active (not deleted) rss items")
    fun getRssItems(
        @Argument page: Int,
        @Argument size: Int
    ): List<RssItem> {
        val pageable: Pageable = PageRequest.of(page, size)
        return rssItemRepository.findAllActive(pageable).toList()
    }

    @QueryMapping("rssItemById")
    @GraphQLDescription("Returns rss item by id")
    fun getRssItemById(@Argument id: UUID): RssItem = rssItemRepository.findById(id).orElse(null)

    @QueryMapping("rssItemsBySourceId")
    @GraphQLDescription("Returns rss items by id of the source")
    fun getRssItemsBySourceId(
        @Argument sourceId: UUID,
        @Argument page: Int,
        @Argument size: Int
    ): List<RssItem> {
        val pageable: Pageable = PageRequest.of(page, size)
        return rssItemRepository.findAllActiveBySourceId(sourceId, pageable).toList()
    }

    @MutationMapping("updateRssItem")
    @GraphQLDescription("Updates rss item")
    fun updateRssItem(@Argument id: UUID, @Argument rssItemInput: RssItemInput?): RssItem? {
        val rssItem = rssItemRepository.findById(id).orElse(null) ?: throw Exception("Item does not exist")

        if (rssItemInput == null) {
            return rssItem
        }

        if (rssItemInput.title != null) {
            rssItem.title = rssItemInput.title!!
        }

        if (rssItemInput.description != null) {
            rssItem.description = rssItemInput.description!!
        }

        if (rssItemInput.link != null) {
            rssItem.link = rssItemInput.link!!
        }

        if (rssItemInput.category != null) {
            rssItem.category = rssItemInput.category!!
        }

        if (rssItemInput.pdaLink != null) {
            rssItem.pdaLink = rssItemInput.pdaLink!!
        }

        if (rssItemInput.fullText != null) {
            rssItem.fullText = rssItemInput.fullText!!
        }

        if (rssItemInput.tags != null) {
            rssItem.tags = rssItemInput.tags!!
        }

        if (rssItemInput.newsLine != null) {
            rssItem.newsLine = rssItemInput.newsLine!!
        }

        return rssItemRepository.save(rssItem)
    }

    @MutationMapping("deleteRssItem")
    @GraphQLDescription("Soft deleted rss item")
    fun deleteRssItem(@Argument id: UUID): RssItem? {
        val rssItem = rssItemRepository.findByIdOrNull(id) ?: throw Exception("Item does not exist")

        rssItem.deletedAt = Timestamp(System.currentTimeMillis())

        return rssItemRepository.save(rssItem)
    }

    @QueryMapping("rssItemsByPublicationDate")
    @GraphQLDescription("Returns rss items by publicationDate")
    fun getRssItemsByPublicationDate(
        @Argument publicationDate: LocalDate,
        @Argument page: Int,
        @Argument size: Int
    ): List<RssItem>? {
        val pageable: Pageable = PageRequest.of(page, size)
        return rssItemRepository.findAllActiveByPublicationDate(publicationDate, pageable).toList()
    }

    @QueryMapping("rssItemsBeforeDate")
    @GraphQLDescription("Returns rss items published before specified date")
    fun getRssItemsBeforeDate(
        @Argument date: LocalDate,
        @Argument page: Int,
        @Argument size: Int
    ): List<RssItem>? {
        val pageable: Pageable = PageRequest.of(page, size)
        return rssItemRepository.findAllActiveBeforeDate(date, pageable).toList()
    }

    @QueryMapping("rssItemsAfterDate")
    @GraphQLDescription("Returns rss items published after specified date")
    fun getRssItemsAfterDate(
        @Argument date: LocalDate,
        @Argument page: Int,
        @Argument size: Int
    ): List<RssItem>? {
        val pageable: Pageable = PageRequest.of(page, size)
        return rssItemRepository.findAllActiveAfterDate(date, pageable).toList()
    }

    @QueryMapping("rssItemsByCategory")
    @GraphQLDescription("Returns rss items by category")
    fun getRssItemsByCategory(
        @Argument category: String,
        @Argument page: Int,
        @Argument size: Int
    ): List<RssItem>? {
        val pageable: Pageable = PageRequest.of(page, size)
        return rssItemRepository.findAllActiveByCategory(category, pageable).toList()
    }
}
