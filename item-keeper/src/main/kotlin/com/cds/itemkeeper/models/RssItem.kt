package com.cds.itemkeeper.models

import com.cds.itemkeeper.utils.JsonDateSerializer
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.hypersistence.utils.hibernate.type.array.StringArrayType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import java.sql.Timestamp
import java.time.LocalDate
import java.util.*


@Entity
@Table(name = "items")
class RssItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    lateinit var id: UUID

    @Basic
    @Column(name = "title", nullable = false, length = 256)
    lateinit var title: String

    @Basic
    @Column(name = "link", nullable = false, length = 256)
    lateinit var link: String

    @Basic
    @Column(name = "source_id", nullable = false, length = 256)
    lateinit var sourceId: UUID

    @Basic
    @Column(name = "publication_date")
    var publicationDate: Date? = null

    @Basic
    @Column(name = "description", columnDefinition = "text")
    var description: String? = null

    @Basic
    @Column(name = "category", length = 256)
    var category: String? = null

    @Basic
    @Column(name = "guid", length = 256, nullable = false)
    lateinit var guid: String

    @Basic
    @Column(name = "pda_link", length = 256)
    var pdaLink: String? = null

    @Basic
    @Column(name = "full_text", columnDefinition = "text")
    var fullText: String? = null

    @Basic
    @Column(name = "news_id", length = 256)
    var newsId: String? = null

    @Basic
    @Column(name = "type", length = 128)
    var type: String? = null

    @Type(StringArrayType::class)
    @Column(name = "tags", columnDefinition = "text[]")
    var tags: Array<String>? = null

    @Basic
    @Column(name = "news_line", length = 256)
    var newsLine: String? = null

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
}
