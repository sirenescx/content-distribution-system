package com.cds.itemkeeperadmin.clients

import com.cds.generated.*
import com.cds.generated.inputs.RssItemInput
import com.expediagroup.graphql.client.spring.GraphQLWebClient
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.Date


@Component
class ItemKeeperClient {
    @Autowired
    private lateinit var client: GraphQLWebClient
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd")

    fun getRssItemById(id: String) =
        runBlocking { client.execute(GetRssItemById(GetRssItemById.Variables(id))) }.data!!.rssItemById

    fun getRssItems() = runBlocking { client.execute(GetRssItems()) }.data!!.rssItems

    fun getRssItemsBySourceId(sourceId: String) = runBlocking {
        client.execute(GetRssItemsBySourceId(GetRssItemsBySourceId.Variables(sourceId))).data!!.rssItemsBySourceId
    }

    fun getRssItemsByPublicationDate(date: Date) = runBlocking {
        client
            .execute(GetRssItemsByPublicationDate(GetRssItemsByPublicationDate.Variables(dateFormatter.format(date))))
            .data!!
            .rssItemsByPublicationDate
    }

    fun getRssItemsBeforeDate(date: Date) = runBlocking {
        client
            .execute(GetRssItemsBeforeDate(GetRssItemsBeforeDate.Variables(dateFormatter.format(date))))
            .data!!
            .rssItemsBeforeDate
    }

    fun getRssItemsAfterDate(date: Date) = runBlocking {
        client
            .execute(GetRssItemsAfterDate(GetRssItemsAfterDate.Variables(dateFormatter.format(date))))
            .data!!
            .rssItemsAfterDate
    }

    fun updateRssItem(id: String, rssItemInput: RssItemInput?) = runBlocking {
        client.execute(UpdateRssItem(UpdateRssItem.Variables(id, rssItemInput))).data!!.updateRssItem
    }

    fun deleteRssItem(id: String) = runBlocking {
        client.execute(DeleteRssItem(DeleteRssItem.Variables(id))).data!!.deleteRssItem
    }

    fun getSourceById(id: String) = runBlocking {
        client.execute(GetSourceById(GetSourceById.Variables(id))).data!!.sourceById
    }

    fun getSourceByName(name: String) = runBlocking {
        client.execute(GetSourceByName(GetSourceByName.Variables(name))).data!!.sourceByName
    }

    fun getSources() = runBlocking { client.execute(GetSources()) }.data!!.sources

    fun getBannedSources() = runBlocking { client.execute(GetBannedSources()) }.data!!.bannedSources

    fun createSource(name: String, link: String, configurationFilename: String) = runBlocking {
        client.execute(CreateSource(CreateSource.Variables(name, link, configurationFilename))).data!!.createSource
    }

    fun deleteSource(id: String) = runBlocking {
        client.execute(DeleteSource(DeleteSource.Variables(id))).data!!.deleteSource
    }

    fun banSource(id: String) = runBlocking {
        client.execute(BanSource(BanSource.Variables(id))).data!!.banSource
    }

    fun unbanSource(id: String) = runBlocking {
        client.execute(UnbanSource(UnbanSource.Variables(id))).data!!.unbanSource
    }
}
