package com.nsa.evolve.service.impl;

import com.nsa.evolve.service.MailService;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by c1633899 on 27/11/2017.
 */
@Service
public class MailServiceImpl implements MailService {

    private final String EMAIL_USERNAME = "evolve.register@gmail.com";
    private final String EMAIL_PASSWORD = "evolvepassword";

    @Override
    public void send(String to, String subject, String content) throws MessagingException {

//        try {
//            Properties properties = new Properties();
//            properties.put("mail.smtp.auth", "true");
//            properties.put("mail.smtp.starttls.enable", "true");
//            properties.put("mail.smtp.host", "smtp.gmail.com");
//            properties.put("mail.smtp.port", "465");
//            properties.put("mail.smtp.debug", "true");
//
////        properties.put("mail.smtp.socketFactory.port", "587");
////        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
////        properties.put("mail.smtp.socketFactory.fallback", "true");
//
//            Session session = Session.getInstance(properties, new Authenticator() {
//                @Override
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(
//                            EMAIL_USERNAME,
//                            EMAIL_PASSWORD
//                    );
//                }
//            });
//            session.setDebug(true);
//
//            Message message = new MimeMessage(session);
//            message.setSentDate(new Date());
//            message.setFrom(new InternetAddress(EMAIL_USERNAME));
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            message.setSubject(subject);
//            message.setText(content);
//            message.saveChanges();
//
//            Transport.send(message);
//        } catch (Exception e){
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }

//        Email email = EmailBuilder
//                .startingBlank()
//                .from("Evolve", "evolve.register@gmail.com")
//                .to(to)
//                .withSubject(subject)
//                .withPlainText(content)
//                .buildEmail();
//
//
//        MailerBuilder
//                .withSMTPServerHost("smtp.gmail.com")
//                .withSMTPServerPort(465)
//                .withSMTPServerUsername("evolve.register@gmail.com")
//                .withSMTPServerPassword("evolvepassword")
//                .withTransportStrategy(TransportStrategy.SMTP_TLS)
//                .buildMailer()
//                .sendMail(email);
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
//
//        messageHelper.setSubject(subject);
//        messageHelper.setTo(to);
//        messageHelper.setText(content, true);
//
//        javaMailSender.send(message);
    }
}
