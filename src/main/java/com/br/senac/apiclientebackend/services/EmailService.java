package com.br.senac.apiclientebackend.services;

import com.br.senac.apiclientebackend.entitys.Emails;
import com.br.senac.apiclientebackend.repository.EmailsRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailsRepository emailsRepository;

    public void enviarEmailHtml(String mensagem, String destinatario, String assunto){

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        String htmlContent = "<p>{message}</p>";
        htmlContent =  htmlContent.replace("{message}", mensagem);

        try {
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void criarFilaEmail(String mensagem, String destinatario, String assunto){

        Emails emails = new Emails();
        emails.setMensagem(mensagem);
        emails.setAssunto(assunto);
        emails.setDestinatario(destinatario);

        emailsRepository.save(emails);
    }
}
