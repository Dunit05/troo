// By: Tommy
// Sprint: 3

package com.troo.controllers.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import java.io.IOException;

import io.github.cdimascio.dotenv.Dotenv;

// Email class that extends the SendGrid Email class
public class Email extends com.sendgrid.helpers.mail.objects.Email {
    // Opens a new dotenv file to get the SendGrid API key
    Dotenv dotenv = Dotenv.load();

    // Creates a new SendGrid object with the API key
    SendGrid sg = new SendGrid(dotenv.get("SENDGRID_API_KEY"));

    // Constructor
    public Email() {
        super();
    }

    // Overloading the constructor
    public Email(String string) {
        super(string);
    }

    // Send email method with no attachment
    public void sendEmail(String email, String emailSubject, String message) {
        // Create a new email object
        Email to = new Email(email);
        Email from = new Email(dotenv.get("FROM_EMAIL"));
        String subject = emailSubject;
        Content content = new Content("text/plain", message);

        // Create a new mail object, and request object
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();

        // Try to send the email
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            // For debugging purposes
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Send email method with attachment, takes in a path to the file
    public void sendEmailWithAttachment(String email, String path) {
        // Create a new email object
        Email to = new Email(email);
        Email from = new Email(dotenv.get("FROM_EMAIL"));
        String subject = "You Logged In";
        Content content = new Content("text/plain",
                "You logged in to Troo, and your encypted password is: ");

        // Create a new mail object, and request object, and attachment object, and byte
        // array
        String base64EncodedImageBytes = "";
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        byte[] bytes;

        // Try to read the file, and encode it to base64
        try {
            bytes = Files.readAllBytes(Paths.get(path));
            base64EncodedImageBytes = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Create a new attachment object, and set the content, type, filename
        Attachments attachment = new Attachments();
        attachment.setContent(base64EncodedImageBytes);
        attachment.setType("image/png");
        attachment.setFilename("icon.png");
        attachment.setDisposition("attachment");
        attachment.setContentId("troo");
        mail.addAttachments(attachment);

        // Try to send the email
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            // For debugging purposes
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
