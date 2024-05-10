package com.cds.itemkeeperadmin.clients

import com.cds.generated.*
import com.expediagroup.graphql.client.spring.GraphQLWebClient
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class ItemKeeperClient {
    @Autowired
    private lateinit var client: GraphQLWebClient

    fun getRssItems() = runBlocking { client.execute(GetRssItems()) }.data!!.rssItems

    fun getRssItemsBySourceId(sourceId: String) = runBlocking {
        client.execute(GetRssItemsBySourceId(GetRssItemsBySourceId.Variables(sourceId))).data!!.rssItemsBySourceId
    }

    fun getSources() = runBlocking { client.execute(GetSources()) }.data!!.sources

    fun getSourceByName(name: String) = runBlocking {
        client.execute(GetSourceByName(GetSourceByName.Variables(name))).data!!.sourceByName
    }

    fun createSource(name: String, link: String, configurationFilename: String) = runBlocking {
        client.execute(CreateSource(CreateSource.Variables(name, link, configurationFilename))).data!!.createSource
    }
}
