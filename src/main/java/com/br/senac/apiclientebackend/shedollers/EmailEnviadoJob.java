package com.br.senac.apiclientebackend.shedollers;

import com.br.senac.apiclientebackend.entitys.Cliente;
import com.br.senac.apiclientebackend.repository.ClienteRepository;
import com.br.senac.apiclientebackend.services.EmailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmailEnviadoJob implements Job {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("--------------Inicio--------------");
        List<Cliente> clienteList = clienteRepository.carregarClientesNaoEnviado();

        for (Cliente cli : clienteList){
            logger.info("Enviando email cliente id: " + cli.getId());
            emailService.enviarEmailHtml("Cliente cadastrado com sucesso, id: " + cli.getId(),  cli.getEmail(), "Cadastro de Cliente");
            clienteRepository.findById(cli.getId()).map(record -> {
                record.setEmailEnviado(true);
                return clienteRepository.save(record);
            });
        }
        logger.info("---------------Final--------------");

    }
}
