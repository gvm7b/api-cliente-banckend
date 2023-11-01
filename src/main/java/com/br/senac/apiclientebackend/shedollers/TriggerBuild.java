package com.br.senac.apiclientebackend.shedollers;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class TriggerBuild {

//    @Bean
//    public JobDetail jobDetail(){
//        JobDetail job = JobBuilder.newJob(TesteJob.class)
//                .withIdentity("testeJob", "grupo1")
//                .storeDurably()
//                .build();
//
//        return job;
//    }
//
//    @Bean
//    public Trigger trigger(JobDetail job){
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity("triggerTeste", "triggerGrupo1")
//                .withSchedule(CronScheduleBuilder.cronSchedule(" 0/20 * * 1/1 * ? *"))
//                .forJob(job)
//                .build();
//
//        return trigger;
//    }

    @Bean
    public JobDetail jobDetailEmail(){
        JobDetail job = JobBuilder.newJob(EmailsJob.class)
                .withIdentity("emailsJob", "grupo1")
                .storeDurably()
                .build();

        return job;
    }

    @Bean
    public Trigger triggerEmail(JobDetail job){
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("triggerEmails", "triggerGrupo1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 */1 * ? * * *"))
                .forJob(job)
                .build();

        return trigger;
    }

}
