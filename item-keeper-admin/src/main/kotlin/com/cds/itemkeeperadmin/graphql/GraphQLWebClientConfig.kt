package com.cds.itemkeeperadmin.graphql

import com.expediagroup.graphql.client.spring.GraphQLWebClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GraphQLWebClientConfig {
    @Value("#{environment['graphql.client.url']}")
    private lateinit var url: String

    @Bean
    fun getGraphQLWebClient() = GraphQLWebClient(url)
}