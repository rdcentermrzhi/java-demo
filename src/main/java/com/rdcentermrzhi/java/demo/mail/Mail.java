package com.rdcentermrzhi.java.demo.mail;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.simplejavamail.util.ConfigLoader;

public class Mail {

	public static void main(String[] args) {
		/*
		 * ConfigLoader.loadProperties("simplejavamail.properties"); // optional default
		 * ConfigLoader.loadProperties("overrides.properties"); // optional extra
		 * 
		 * Email email = EmailBuilder.startingBlank() .to("lollypop",
		 * "lolly.pop@somemail.com") .to("C. Cane", "candycane@candyshop.org")
		 * .ccWithFixedName("C. Bo group", "chocobo1@candyshop.org",
		 * "chocobo2@candyshop.org") .withRecipientsUsingFixedName("Tasting Group", BCC,
		 * "taster1@cgroup.org;taster2@cgroup.org;tester &lt;taster3@cgroup.org&gt;")
		 * .bcc("Mr Sweetnose &lt;snose@candyshop.org&gt;") .withReplyTo("lollypop",
		 * "lolly.pop@othermail.com") .withSubject("hey")
		 * .withHTMLText("&lt;img src=&#39;cid:wink1&#39;&gt;&lt;b&gt;We should meet up!&lt;/b&gt;&lt;img src=&#39;cid:wink2&#39;&gt;"
		 * ) .withPlainText("Please view this email in a modern email client!")
		 * .withEmbeddedImage("wink1", imageByteArray, "image/png")
		 * .withEmbeddedImage("wink2", imageDatesource) .withAttachment("invitation",
		 * pdfByteArray, "application/pdf") .withAttachment("dresscode", odfDatasource)
		 * .withHeader("X-Priority", 5) .withReturnReceiptTo()
		 * .withDispositionNotificationTo("notify-read-emails@candyshop.com")
		 * .withBounceTo("tech@candyshop.com") .signWithDomainKey(privateKeyData,
		 * "somemail.com", "selector") .buildEmail();
		 * 
		 * Mailer mailer = MailerBuilder .withSMTPServer("smtp.host.com", 587,
		 * "user@host.com", "password")
		 * .withTransportStrategy(TransportStrategy.SMTP_TLS)
		 * .withProxy("socksproxy.host.com", 1080, "proxy user", "proxy password")
		 * .withSessionTimeout(10 * 1000) .clearEmailAddressCriteria() // turns off
		 * email validation .withProperty("mail.smtp.sendpartial", true)
		 * .withDebugLogging(true) .buildMailer();
		 * 
		 * mailer.sendMail(email);
		 */

		Email email = EmailBuilder.startingBlank().from("Michel Baker", "2675204902@qq.com")
				.to("Mick", "xxxxxx@aaa.com")
				.withSubject("My Bakery is finally open!")
				.withPlainText("121212333").buildEmail();

		MailerBuilder.withSMTPServer("smtp.xxx.com", 25, "xxx@bw30.com", "password")
		.withTransportStrategy(TransportStrategy.SMTP).buildMailer().sendMail(email);
	}
}
