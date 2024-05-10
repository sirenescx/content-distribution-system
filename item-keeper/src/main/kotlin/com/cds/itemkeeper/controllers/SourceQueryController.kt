package com.cds.itemkeeper.controllers

import com.cds.itemkeeper.models.Source
import com.cds.itemkeeper.models.SourceInput
import com.cds.itemkeeper.repositories.SourceRepository
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.*


@Controller
class SourceQueryController(private val sourceRepository: SourceRepository) : Query {
    @QueryMapping("sources")
    @GraphQLDescription("Returns all sources")
    fun getSources(): List<Source> = sourceRepository.findAll()

    @QueryMapping("sourceByName")
    @GraphQLDescription("Returns source by source name")
    fun getSourceByName(@Argument name: String): Source? = sourceRepository.findByName(name)

    @MutationMapping("createSource")
    @GraphQLDescription("Creates new source")
    fun createSource(@Argument name: String, @Argument link: String, @Argument configurationFilename: String): Source {
        if (getSourceByName(name) != null) {
            throw Exception("Source already exists")
        }

        val source = Source()
        source.name = name
        source.link = link
        source.configurationFilename = configurationFilename
        sourceRepository.save(source)

        return source
    }

    @MutationMapping("updateSource")
    @GraphQLDescription("Updates existing source")
    fun updateSource(@Argument id: UUID, @Argument sourceInput: SourceInput?): Source {
        val source = sourceRepository.findByIdOrNull(id) ?: throw Exception("Source doesn't exists")

        if (sourceInput == null) {
            return source
        }

        if (sourceInput.link != null) {
            source.link = sourceInput.link!!
        }

        if (sourceInput.name != null) {
            source.name = sourceInput.name!!
        }

        if (sourceInput.configurationFilename != null) {
            source.configurationFilename = sourceInput.configurationFilename!!
        }

        sourceRepository.save(source)

        return source
    }

    @MutationMapping("deleteSource")
    @GraphQLDescription("Deletes existing source")
    fun deleteSource(@Argument id: UUID): Source {
        val source = sourceRepository.findByIdOrNull(id) ?: throw Exception("Source doesn't exists")

        sourceRepository.delete(source)

        return source
    }
}
