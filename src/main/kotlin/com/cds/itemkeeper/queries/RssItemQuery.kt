package com.cds.itemkeeper.queries

import com.cds.itemkeeper.models.RssItem
import com.cds.itemkeeper.repositories.RssItemRepository
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Component
import java.util.*

@Component
class RssItemQuery(private val rssItemRepository: RssItemRepository) : Query {
    private val logger = LoggerFactory.getLogger(RssItemQuery::class.java)

    @QueryMapping
    @GraphQLDescription("Returns all rss items")
    fun getRssItems(): List<RssItem> {
        val items = rssItemRepository.findAll()
        logger.info("Fetching all rssItems: found ${items.size} items")
        return items
    }

    @QueryMapping
    @GraphQLDescription("Returns rss item by id")
    fun getRssItemById(@Argument id: UUID): RssItem {
        val item = rssItemRepository.findById(id)
        logger.info("Fetching rssItem by id: found item")
        return item.orElse(null)
    }

    @QueryMapping("greeting")
    fun getGreeting(@Argument name: String) : String {
        println("Fuck it, it should work!")
        return "Hello, $name"
    }
}
