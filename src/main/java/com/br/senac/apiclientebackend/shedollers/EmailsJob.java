package com.br.senac.apiclientebackend.shedollers;

import com.br.senac.apiclientebackend.entitys.Emails;
import com.br.senac.apiclientebackend.repository.EmailsRepository;
import com.br.senac.apiclientebackend.services.EmailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmailsJob implements Job {

    Logger logger = LoggerFactory.getLogger(EmailsJob.class);

    @Autowired
    private EmailsRepository emailsRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("--------------Inicio--------------");
        List<Emails> resultEmails = emailsRepository.findAll();

        for(Emails email : resultEmails){

            logger.info("Enviando email id " + email.getId() + "\n" +
                        "Destinatario: " + email.getDestinatario() + "\n" +
                        "Assunto: " + email.getAssunto());
            emailService.enviarEmailHtml(email.getMensagem(), email.getDestinatario(), email.getAssunto());

            emailsRepository.deleteById(email.getId());
        }
        logger.info("---------------Final--------------");
    }


}
