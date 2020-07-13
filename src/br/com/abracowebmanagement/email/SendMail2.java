package br.com.abracowebmanagement.email;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

public class SendMail2 {

	// Create the attachment

	public void sendEmailWithAttachment() {

		try {
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath("mypictures/john.jpg");
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("Picture of John");
			attachment.setName("John");

			// Create the email message
			MultiPartEmail email = new MultiPartEmail();
			email.setHostName("mail.myserver.com");

			email.addTo("jdoe@somewhere.org", "John Doe");

			email.setFrom("me@apache.org", "Me");
			email.setSubject("The picture");
			email.setMsg("Here is the picture you wanted");

			// add the attachment
			email.attach(attachment);

			// send the email
			email.send();

		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendFormatedEmailWithEmbededImage() {

		try {

			// load your HTML email template
			String htmlEmailTemplate = ".... <img src=\"http://www.apache.org/images/feather.gif\"> ....";

			// define you base URL to resolve relative resource locations
			URL url;

			url = new URL("http://www.apache.org");

			// create the email message
			ImageHtmlEmail email = new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("mail.myserver.com");
			email.addTo("jdoe@somewhere.org", "John Doe");
			email.setFrom("me@apache.org", "Me");
			email.setSubject("Test email with inline image");

			// set the Attachment
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath("mypictures/john.jpg");
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("Picture of John");
			attachment.setName("John");

			// add the attachment
			email.attach(attachment);

			// set the HTML message
			email.setHtmlMsg(htmlEmailTemplate);

			// set the alternative message
			email.setTextMsg("Your email client does not support HTML messages");

			// send the email
			email.send();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmailException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
