package com.cds.itemkeeper.jobs

import org.quartz.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.stereotype.Component

@Component
@Configuration
class DataFetchingJobConfig {
    @Value("#{environment['spring.quartz.properties.cron-expression']}")
    val cronExpression: String = ""

    @Bean
    fun dataFetchingJobDetail(): JobDetail {
        return JobBuilder
            .newJob(DataFetchingJob::class.java)
            .withIdentity("DataFetchingJob")
            .requestRecovery(true)
            .storeDurably()
            .build()
    }

    @Bean
    fun dataFetchingJobTrigger(jobDetail: JobDetail): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity("DataFetchingJobTrigger")
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .build()
    }

    @Bean
    fun scheduler(triggers: List<Trigger>, jobDetails: List<JobDetail>, factory: SchedulerFactoryBean): Scheduler {
        val scheduler = factory.scheduler
        scheduler.start()
        return scheduler
    }
}

