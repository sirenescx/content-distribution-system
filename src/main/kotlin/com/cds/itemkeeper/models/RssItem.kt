package com.cds.itemkeeper.models

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "items")
class RssItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: UUID? = null,
    var title: String? = null, // swap with description via JsonPointer
    var link: String? = null,
    val publicationDate: Date? = null,
    var description: String? = null,
    val category: String? = null,
    val guid: String? = null,
    val pdaLink: String? = null,
    val fullText: String? = null,
    val newsId: String? = null,
    val type: String? = null,
    @ElementCollection
    val tags: List<String>? = null,
    val newsLine: String? = null
    // created / updated / deleted ts
)
