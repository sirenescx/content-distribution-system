package com.cds.itemkeeperadmin.models

import com.cds.itemkeeperadmin.utils.NoArg
import java.sql.Timestamp
import java.util.*

@NoArg
data class Source(
    val id: UUID,
    val name: String,
    val link: String,
    val configurationFilename: String,
    val deletedAt: Timestamp,
    val isBanned: Boolean
)