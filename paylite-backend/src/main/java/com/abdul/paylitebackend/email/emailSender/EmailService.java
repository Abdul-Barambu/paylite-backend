package com.abdul.paylitebackend.email.emailSender;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

   private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmailAlert(EmailDto emailDto) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(emailDto.getRecipient());
            simpleMailMessage.setSubject(emailDto.getSubject());
            simpleMailMessage.setText(emailDto.getMessage());

            javaMailSender.send(simpleMailMessage);

        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }
}
