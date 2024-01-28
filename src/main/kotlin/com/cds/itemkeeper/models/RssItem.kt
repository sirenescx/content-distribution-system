package com.cds.itemkeeper.models

import jakarta.persistence.*

@Entity
@Table(name = "items")
class RssItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var title: String? = null,
    var link: String? = null,
    var description: String? = null
)