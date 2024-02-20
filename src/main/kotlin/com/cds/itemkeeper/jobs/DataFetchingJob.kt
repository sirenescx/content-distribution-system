package com.cds.itemkeeper.jobs

import com.cds.itemkeeper.services.RssService
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean

@DisallowConcurrentExecution
class DataFetchingJob(private val rssService: RssService) : QuartzJobBean() {
    override fun executeInternal(context: JobExecutionContext) {
        println("It's working!")
//        rssService.fetchDataFromRss("http://static.feed.rbc.ru/rbc/logical/footer/news.rss");
    }
}
