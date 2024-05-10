package com.cds.itemkeeper.queries

import com.cds.itemkeeper.models.RssItem
import com.cds.itemkeeper.repositories.RssItemRepository
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.sql.Timestamp
import java.util.*

@Controller
class RssItemQuery(private val rssItemRepository: RssItemRepository) : Query {
    @QueryMapping("rssItems")
    @GraphQLDescription("Returns all rss items")
    fun getRssItems(): List<RssItem> = rssItemRepository.findAllActive()

    @QueryMapping("rssItemById")
    @GraphQLDescription("Returns rss item by id")
    fun getRssItemById(@Argument id: UUID): RssItem = rssItemRepository.findById(id).orElse(null)

    @MutationMapping("updateRssItem")
    @GraphQLDescription("Updates rss item")
    // return 404 if not found by id
    fun updateRssItem(@Argument id: UUID, @Argument title: String, @Argument description: String): RssItem? {
        val rssItem = rssItemRepository.findById(id).orElse(null) ?: return null

        rssItem.title = title
        rssItem.description = description

        return rssItemRepository.save(rssItem)
    }

    @MutationMapping("deleteRssItem")
    @GraphQLDescription("Soft deleted rss item")
    // return status code 200 if deleted ok
    // return 404 if not found by id
    fun deleteRssItem(@Argument id: UUID): RssItem? {
        val rssItem = rssItemRepository.findById(id).orElse(null) ?: return null

        rssItem.deletedAt = Timestamp(System.currentTimeMillis())

        return rssItemRepository.save(rssItem)
    }
}
