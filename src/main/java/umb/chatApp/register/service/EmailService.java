package umb.chatApp.register.service;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    //EMAIL je potrebne na free uctoch pridat cez app.mailgun
    @Value("${mailgun.api-key}")
    private String MAILGUN_API_KEY;
    private static final String MAILGUN_DOMAIN = "postmaster@sandboxf9fc71cc544f42cf8ce4173b5bc1719d.mailgun.org";
    public void sendEmail(String to, String subject, String body){
        System.out.println("API KEY: " +MAILGUN_API_KEY);
        Email email = EmailBuilder.startingBlank()
                .from("ChatApp", "chatapplicationnoreply@gmail.com")
                .to("you", to)
                .withSubject(subject)
                .withPlainText(body)
                .buildEmail();
        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.mailgun.org", 587, MAILGUN_DOMAIN, MAILGUN_API_KEY)
                .buildMailer();
        mailer.sendMail(email);

        /*
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        this.mailSender.send(message);
        */
    }

}
