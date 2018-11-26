// File Name RegistrationEmailServlet.java
package com.mvc.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.mail.*;
import javax.mail.internet.*;
//import javax.activation.*;

public class recoverPassEmailServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("retrocinemabooking@gmail.com", "cinemapassword");
                    }
                }
        );

        // Recipient's email ID needs to be mentioned.
        String to = (String)request.getAttribute("email");

        // Assuming you are sending email from localhost
        //String host = "localhost";

        // Get system properties
        //Properties properties = System.getProperties();

        // Setup mail server
        //properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        //Session session = Session.getDefaultInstance(properties);

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress("retrocinemabooking@gmail.com"));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject("Registration Confirmation");

            // Send the actual HTML message, as big as you like
            message.setContent("<h1>Recover Password</h1>", "text/html" );

            // Send message
            Transport.send(message);
            String title = "Send Email";
            String res = "Sent message successfully....";
            String docType =
                    "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

            out.println(docType +
                    "<html>\n" +
                    "<head><title>" + title + "</title></head>\n" +
                    "<body bgcolor = \"#f0f0f0\">\n" +
                    "<h1 align = \"center\">" + title + "</h1>\n" +
                    "<p align = \"center\">" + res + "</p>\n" +
                    "</body> " +
                    "</html>"
            );

            out.print("Sent email");
            RequestDispatcher rd=request.getRequestDispatcher("forgotPassword.jsp");
            rd.include(request,response);

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
} 