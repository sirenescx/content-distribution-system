package com.cds.itemkeeper.models

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "sources")
class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    lateinit var id: UUID

    @Basic
    @Column(name = "name", nullable = false, length = 256)
    lateinit var name: String

    @Basic
    @Column(name = "link", nullable = false, length = 256)
    lateinit var link: String

    @Basic
    @Column(name = "configuration_filename", nullable = false, length = 256)
    lateinit var configurationFilename: String
}