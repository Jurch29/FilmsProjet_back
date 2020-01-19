package bzh.jap.util;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class Email {
	
	public Email() {
		// TODO Auto-generated constructor stub
	}
	
	public void sendEmail(JavaMailSender javaMailSender, String message, String titre, String destinataire) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(destinataire);
        msg.setSubject(titre);
        msg.setText(message);

        javaMailSender.send(msg);

    }
	
	void sendEmailWithAttachment(JavaMailSender javaMailSender,String message, String titre, String destinataire, String fileName, String filePath) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(destinataire);

        helper.setSubject(message);

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText(message, true);

        helper.addAttachment(fileName, new ClassPathResource(filePath));

        javaMailSender.send(msg);
    }

}