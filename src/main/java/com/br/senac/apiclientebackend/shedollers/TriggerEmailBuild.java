package com.br.senac.apiclientebackend.shedollers;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

public class TriggerEmailBuild {

    @Bean(name = "jobDetailClienteEmail")
    public JobDetail jobDetail(){
        JobDetail job = JobBuilder.newJob(EmailEnviadoJob.class)
                .withIdentity("emailsClienteJob", "grupo2")
                .storeDurably()
                .build();

        return job;
    }

   @Bean(name = "triggerClienteEmail")
    public Trigger trigger(@Qualifier("JobDetailClienteEmail") JobDetail job){
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("triggerClienteEmails", "triggerGrupo1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *"))
                .forJob(job)
                .build();

        return trigger;
    }
}
