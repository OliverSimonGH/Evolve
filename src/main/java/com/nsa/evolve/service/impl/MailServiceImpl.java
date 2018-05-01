package com.nsa.evolve.service.impl;

import com.nsa.evolve.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger errorLog = LoggerFactory.getLogger("ErrorLog");
    private final String EMAIL_USERNAME = "evolve.register@gmail.com";
    private final String EMAIL_PASSWORD = "evolvepassword";
    private final String EMAIL_HOST = "smtp.gmail.com";
    private final String EMAIL_PORT = "587"; //587, 465

    @Override
    public void send(String to, String subject, String content) throws MessagingException {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", EMAIL_HOST);
            properties.put("mail.smtp.port", EMAIL_PORT);
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            EMAIL_USERNAME,
                            EMAIL_PASSWORD
                    );
                }
            });

            Message message = new MimeMessage(session);
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content);
            message.saveChanges();

            Transport transport = session.getTransport("smtp");
            transport.connect(EMAIL_HOST, 587, EMAIL_USERNAME, EMAIL_PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e){
            errorLog.error("Email failed to send\n" + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
