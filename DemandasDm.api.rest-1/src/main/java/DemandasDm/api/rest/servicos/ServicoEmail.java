package DemandasDm.api.rest.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ServicoEmail {

	 	@Autowired
	    private JavaMailSender mailSender;

	    public String sendMail(String email) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setText("Hello from Spring Boot Application");
	        message.setTo("wolmirgarbin@gmail.com");
	        message.setFrom(email);
	     
	        try {
	            mailSender.send(message);
	            return "Email enviado com sucesso!";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Erro ao enviar email.";
	        }
	    }

	
	
}