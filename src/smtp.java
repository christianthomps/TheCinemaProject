/**
 * 1. Download JavaMail API
 * 2. Add the mail.jar , and all the jars in "lib" to project
 * 4. import java.util.Properties
 * import javax.mail.*;
 */

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/*
 * This class connects to GMail and allows the transmission of messages within the GMail server via the use
 * of SMTP (i.e. Simple Mail Transfer Protocol.
 *
 * The documentation for the API can be found here: https://javaee.github.io/javamail/docs/api/.
 *
 * To proceed, these .jar files need to be downloaded: javax.mail, dsn, imap, mailapi, pop3, smtp.
 *
 * To ensure mail transmission,  you need to allow your GMail to be accessed via "less secure apps". Go here:
 * https://myaccount.google.com/lesssecureapps
 *
 * @author: Mary Brown
 */

public class smtp {

    public static void main(String[] args) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");                                                        //insert name of host/mail server here, format: "smtp.[server].com" (i.e. smtp.gmail.com)
        props.put("mail.smtp.auth", "true");                                                                    //attempts to authenticate user using the AUTH command
        props.put("mail.smtp.port", "[portNumber]");                                                            //insert port number of the mail server for the protocol. (i.e. 587 for TLS, 465 for SSL)
        props.put("mail.smtp.starttls.enable", "true");                                                        //allows the use of TLS for SMTP connections


        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("[yourEmail]", "[yourPassword]");                        //insert your email without domain name, followed by password
                    }
                }
        );

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("[yourEmail]"));                                                //insert your email here with domain name (i.e. from@gmail.com)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("[theirEmail]"));            //insert recipient's email here with domain name (i.e. to@gmail.com)
            message.setSubject("[subjectOfEmail]");                                                                //insert subject of here
            message.setText("[emailContent]");                                                                   //insert body of email here
            //Transport.send(message, "[yourEmail]", "[yourPassword]");                                            //insert your email here with domain name, followed by password

            //let user know email was successfully sent
            System.out.println("message sent");

        } catch (Exception e) {

            //if unsuccessful email transmission, explain error
            e.printStackTrace();
        }
    }
}

