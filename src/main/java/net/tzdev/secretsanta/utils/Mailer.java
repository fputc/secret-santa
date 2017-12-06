package net.tzdev.secretsanta.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Mailer {
  private Session session;
  private boolean test;
  private String testAddress;
  private String sender;

  public Mailer() {

    final Properties props = Configurator.getProperties("email.properties");
    sender = props.getProperty("mail.username");

    test = Boolean.parseBoolean(props.getProperty("mail.test.enabled", "false"));
    testAddress = props.getProperty("mail.test.address");

    session = Session.getInstance(props,
            new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, props.getProperty("mail.password"));
              }
            });
  }

  public void send(Mail mail) {
    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(sender));
      if (test) {
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(testAddress));
      } else {
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo()));
      }
      message.setSubject(mail.getSubject());
      message.setText(mail.getBody());

      Transport.send(message);

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

}
