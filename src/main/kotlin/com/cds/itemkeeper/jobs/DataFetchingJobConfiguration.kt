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
//        factory.setWaitForJobsToCompleteOnShutdown(true)
        val scheduler = factory.scheduler
//        factory.setOverwriteExistingJobs(true)
//        factory.setTransactionManager(JdbcTransactionManager())
//        rescheduleTriggers(triggers, scheduler)
        scheduler.start()
        return scheduler
    }

//    private fun rescheduleTriggers(triggers: List<Trigger>, scheduler: Scheduler) {
//        triggers.forEach {
//            if (!scheduler.checkExists(it.key)) {
//                scheduler.scheduleJob(it)
//            } else {
//                scheduler.rescheduleJob(it.key, it)
//            }
//        }
//    }
}

