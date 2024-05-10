package com.cds.itemkeeperadmin.services

import com.cds.itemkeeperadmin.clients.ItemKeeperClient
import com.cds.itemkeeperadmin.models.RssItem
import com.cds.itemkeeperadmin.models.Source
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class ItemKeeperService(private val itemKeeperClient: ItemKeeperClient, private val modelMapper: ModelMapper) {
    fun rssItems(): List<RssItem>? {
        return itemKeeperClient.getRssItems()?.map { modelMapper.map(it, RssItem::class.java) }
    }

    fun rssItemsBySource(sourceId: String): List<RssItem>? {
        return itemKeeperClient.getRssItemsBySourceId(sourceId)?.map { modelMapper.map(it, RssItem::class.java) }
    }

    fun sources(): List<Source>? {
        return itemKeeperClient.getSources()?.map { modelMapper.map(it, Source::class.java) }
    }

    fun source(name: String): Source {
        return modelMapper.map(itemKeeperClient.getSourceByName(name), Source::class.java)
    }

    fun createSource(name: String, link: String, configurationFilename: String): Source? {
        return modelMapper.map(itemKeeperClient.createSource(name, link, configurationFilename), Source::class.java)
    }
}