package be.anderlecht.ngo.service;

import be.anderlecht.ngo.form.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.admin.email:admin@anderlecht-ngo.be}")
    private String adminEmail;

    public void stuurContactMail(ContactForm form) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setFrom(form.getEmail());
        message.setReplyTo(form.getEmail());
        message.setSubject("Nieuw contactbericht van " + form.getNaam());
        message.setText(
            "Naam: " + form.getNaam() + "\n" +
            "E-mail: " + form.getEmail() + "\n\n" +
            "Bericht:\n" + form.getBericht()
        );
        mailSender.send(message);
    }
}
