package br.com.abracowebmanagement.email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage; 
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication; 



/**
 * class that returns an authentication to be sent 
 * and verified by the SMTP server
 *
 */
public class SendMail {

	private String mailSMTPServer;
	private String mailSMTPServerPort;

	

	/**
	 * When instantiating an Object, the GMAIL SMTP server 
	 * and the port used by it will already be assigned.
	 */
	public SendMail () { //For GMAIL 
		mailSMTPServer = "smtp.gmail.com";
		mailSMTPServerPort = "465";
	}
	
	
	/**
	 * If you want to change the server and port, 
	 * just send the values to the constructor as a string.
	 * @param mailSMTPServer
	 * @param mailSMTPServerPort
	 */
	public SendMail (String mailSMTPServer, String mailSMTPServerPort) { //To another Server
		this.mailSMTPServer = mailSMTPServer;
		this.mailSMTPServerPort = mailSMTPServerPort;
	}

	/**
	 * Send mail
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param message
	 */
	public void sendMail(String from, String to, String subject, String message) {

		Properties props = new Properties();

        // Whoever is using a PROXY SERVER uncomment this part 
		//and assign the properties of the PROXY SERVER used.

        /*
        props.setProperty("proxySet","true");
        props.setProperty("socksProxyHost","192.168.155.1"); //Proxy Server IP
        props.setProperty("socksProxyPort","1080");  //Proxy server port
        */

		props.put("mail.transport.protocol", "smtp"); //Defines sending protocol as SMTP.
		props.put("mail.smtp.starttls.enable","true"); 
		props.put("mail.smtp.host", mailSMTPServer); //Server SMTP do GMAIL.
		props.put("mail.smtp.auth", "true"); //Active authentication.
		props.put("mail.smtp.user", from); //User or the account sending the email (must be from GMAIL).
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", mailSMTPServerPort); //Port.
		props.put("mail.smtp.socketFactory.port", mailSMTPServerPort); //Same port for the socket.
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		
		//Creates an authenticator that will be used next.
		SimpleAuth auth = null;
		auth = new SimpleAuth ("seu_emailgmail.com","sua_senha");

		
		//Session - object that will make the connection to the server.
		
		/*
		 * As there is a need for authentication, 
		 * an authentication is created that is responsible 
		 * for requesting and returning the user 
		 * and password for authentication.
		 * 
		 */
		Session session = Session.getDefaultInstance(props, auth);
		session.setDebug(true); //Enables the LOG of the actions performed during the sending of the email.

		//Object containing the message.
		Message msg = new MimeMessage(session);

		try {
			
			//Set the recipient.
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			//Set the origin of the email.
			msg.setFrom(new InternetAddress(from));
			
			//Set the subject.
			msg.setSubject(subject);
			
			//Set the Email Content/Body.
			msg.setContent(message,"text/plain");

		} catch (Exception e) {
			System.out.println(">> Erro: Completar Mensagem");
			e.printStackTrace();
		}

		
		//Object in charge of sending data to email.
		Transport tr;
		
		try {
			
			tr = session.getTransport("smtp"); //Defines SMTP for transport.
			
			/*
			 * 1 - define the SMTP server
			 * 2 - your GMAIL user name
			 * 3 - your gmGMAILail password
			 */
			tr.connect(mailSMTPServer, "seu_emailgmail.com", "sua_senha");
			msg.saveChanges(); // don't forget this
			
			//Send message.
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(">> Erro: Envio Mensagem");
			e.printStackTrace();
		}

	}
}


/**
 * 
 * Simple Authentication
 *
 */
class SimpleAuth extends Authenticator {
		public String username = null;
		public String password = null;
	
	
		public SimpleAuth(String user, String pwd) {
			username = user;
			password = pwd;
		}
	
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication (username,password);
		}
}