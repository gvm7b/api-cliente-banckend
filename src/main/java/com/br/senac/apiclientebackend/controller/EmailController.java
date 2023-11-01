package com.br.senac.apiclientebackend.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/enviar")
    public ResponseEntity<Void> enviarEmail(@RequestParam String mensagem, @RequestParam String destinatario){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(destinatario);
        simpleMailMessage.setText(mensagem);
        simpleMailMessage.setSubject("Teste de Mensagem");

        javaMailSender.send(simpleMailMessage);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/enviar_html")
    public ResponseEntity<Void> enviarEmailHtml(@RequestParam String mensagem, @RequestParam String destinatario){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        String htmlContent = "<p>{message}</p>";
        htmlContent =  htmlContent.replace("{message}", mensagem);

        try {
            helper.setTo(destinatario);
            helper.setSubject("Teste de email html");
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


        return ResponseEntity.ok(null);
    }
}
