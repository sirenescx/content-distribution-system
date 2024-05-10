package com.cds.itemkeeper.jobs

import com.cds.itemkeeper.services.RssItemService
import com.cds.itemkeeper.services.SourceService
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean

@DisallowConcurrentExecution
class DataFetchingJob(private val rssItemService: RssItemService, private val sourceService: SourceService) : QuartzJobBean() {
    override fun executeInternal(context: JobExecutionContext) {
        println("It's working")
        val sources = sourceService.getSources()
        for (source in sources) {
            rssItemService.getItems(source.link, source.configurationFilename, source.id)
        }
    }
}
