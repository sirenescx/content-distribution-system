package com.cds.itemkeeper.queries

import com.cds.itemkeeper.models.Source
import com.cds.itemkeeper.repositories.SourceRepository
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller


@Controller
class SourceQuery(private val sourceRepository: SourceRepository) : Query {
    @QueryMapping("sources")
    @GraphQLDescription("Returns all sources")
    fun getSource(): List<Source> = sourceRepository.findAll()

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
}
