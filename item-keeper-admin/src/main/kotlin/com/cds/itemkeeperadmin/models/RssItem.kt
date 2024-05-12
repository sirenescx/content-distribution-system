package com.cds.itemkeeperadmin.models

import com.cds.itemkeeperadmin.utils.NoArg
import java.sql.Timestamp
import java.util.UUID

@NoArg
data class RssItem(
    val id: UUID,
    val title: String,
    val link: String,
    val sourceId: String?,
    val publicationDate: String?,
    val description: String?,
    val category: String?,
    val guid: String?,
    val pdaLink: String?,
    val fullText: String?,
    val newsId: String?,
    val type: String?,
    val tags: Array<String>?,
    val newsLine: String?,
    val deletedAt: Timestamp
)