package com.cds.itemkeeper.models

import jakarta.persistence.*
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import java.sql.Timestamp
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

    @Basic
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    var createdAt: Timestamp = Timestamp(System.currentTimeMillis())

    @Basic
    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: Timestamp? = null

    @Basic
    @Column(name = "deleted_at")
    var deletedAt: Timestamp? = null

    @Basic
    @Column(name = "is_banned")
    var isBanned: Boolean = false
}