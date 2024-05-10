package com.cds.itemkeeperadmin.models

import com.cds.itemkeeperadmin.utils.NoArg
import java.util.*

@NoArg
data class Source(val id: UUID, val name: String, val link: String)