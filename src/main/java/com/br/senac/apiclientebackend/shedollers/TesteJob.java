package com.br.senac.apiclientebackend.shedollers;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TesteJob implements Job {

    Logger logger = LoggerFactory.getLogger(TesteJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.error("Teste Job");
    }
}
