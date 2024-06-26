package com.cds.itemkeeperadmin.services

import com.cds.generated.inputs.RssItemInput
import com.cds.generated.inputs.SourceInput
import com.cds.itemkeeperadmin.clients.ItemKeeperClient
import com.cds.itemkeeperadmin.models.RssItem
import com.cds.itemkeeperadmin.models.Source
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemKeeperService(private val itemKeeperClient: ItemKeeperClient, private val modelMapper: ModelMapper) {
    fun getRssItemById(id: String): RssItem? {
        return modelMapper.map(itemKeeperClient.getRssItemById(id), RssItem::class.java)
    }

    fun getRssItems(page: Int, size: Int): List<RssItem>? {
        return itemKeeperClient.getRssItems(page, size)?.map { modelMapper.map(it, RssItem::class.java) }
    }

    fun getRssItemsBySourceId(sourceId: String, page: Int, size: Int): List<RssItem>? {
        return itemKeeperClient.getRssItemsBySourceId(sourceId, page, size)
            ?.map { modelMapper.map(it, RssItem::class.java) }
    }

    fun getRssItemsByPublicationDate(publicationDate: Date, page: Int, size: Int): List<RssItem>? {
        return itemKeeperClient.getRssItemsByPublicationDate(publicationDate, page, size)
            ?.map { modelMapper.map(it, RssItem::class.java) }
    }

    fun getRssItemsBeforeDate(date: Date, page: Int, size: Int): List<RssItem>? {
        return itemKeeperClient.getRssItemsBeforeDate(date, page, size)
            ?.map { modelMapper.map(it, RssItem::class.java) }
    }

    fun getRssItemsAfterDate(date: Date, page: Int, size: Int): List<RssItem>? {
        return itemKeeperClient.getRssItemsAfterDate(date, page, size)
            ?.map { modelMapper.map(it, RssItem::class.java) }
    }

    fun updateRssItem(id: String, rssItemInput: RssItemInput): RssItem? {
        return modelMapper.map(itemKeeperClient.updateRssItem(id, rssItemInput), RssItem::class.java)
    }

    fun deleteRssItem(id: String): RssItem? {
        return modelMapper.map(itemKeeperClient.deleteRssItem(id), RssItem::class.java)
    }

    fun getSourceById(id: String): Source {
        return modelMapper.map(itemKeeperClient.getSourceById(id), Source::class.java)
    }

    fun getSourceByName(name: String): Source {
        return modelMapper.map(itemKeeperClient.getSourceByName(name), Source::class.java)
    }

    fun getSources(): List<Source>? {
        return itemKeeperClient.getSources()?.map { modelMapper.map(it, Source::class.java) }
    }

    fun getBannedSources(): List<Source>? {
        return itemKeeperClient.getBannedSources()?.map { modelMapper.map(it, Source::class.java) }
    }

    fun createSource(name: String, link: String, configurationFilename: String): Source? {
        return modelMapper.map(itemKeeperClient.createSource(name, link, configurationFilename), Source::class.java)
    }

    fun updateSource(id: String, sourceInput: SourceInput): Source? {
        return modelMapper.map(itemKeeperClient.updateSource(id, sourceInput), Source::class.java)
    }

    fun deleteSource(id: String): Source? {
        return modelMapper.map(itemKeeperClient.deleteSource(id), Source::class.java)
    }

    fun banSource(id: String): Source? {
        return modelMapper.map(itemKeeperClient.banSource(id), Source::class.java)
    }

    fun unbanSource(id: String): Source? {
        return modelMapper.map(itemKeeperClient.unbanSource(id), Source::class.java)
    }
}