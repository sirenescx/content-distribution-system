package com.cds.itemkeeperadmin.clients

import com.cds.generated.AllRssItems
import com.expediagroup.graphql.client.spring.GraphQLWebClient
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ItemKeeperClient {
    @Autowired
    private lateinit var client: GraphQLWebClient

    fun allRssItems() = runBlocking { client.execute(AllRssItems()) }.data!!.rssItems
}
