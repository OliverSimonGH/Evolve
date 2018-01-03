package com.nsa.evolve.service;

import javax.mail.MessagingException;

/**
 * Created by c1633899 on 28/11/2017.
 */
public interface MailService {
    void send(String to, String subject, String content) throws MessagingException;
}
