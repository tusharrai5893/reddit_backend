package com.reddit.backend.service;

import com.reddit.backend.exceptions.RedditCustomException;
import com.reddit.backend.models.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final MailContentBuilder mailContentBuilder;
    private final JavaMailSender javaMailSender;

    void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setFrom("customMail@email.com");
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(mailContentBuilder.buildMail(notificationEmail.getBody()));
        };

        try {
            javaMailSender.send(preparator);
            log.info("Activation Email Has been sent to your Mail, Please verify");

        } catch (MailException e) {
            throw new RedditCustomException("Exception occured while sending mail to "+ notificationEmail.getRecipient() );
        }

    }
}
