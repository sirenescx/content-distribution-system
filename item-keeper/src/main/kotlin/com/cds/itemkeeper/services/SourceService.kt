package com.cds.itemkeeper.services

import com.cds.itemkeeper.models.Source
import com.cds.itemkeeper.repositories.SourceRepository
import org.springframework.stereotype.Service


@Service
class SourceService(private val sourceRepository: SourceRepository) {
    fun getSources() : List<Source> {
        return sourceRepository.findAllWithoutDeleted()
    }
}