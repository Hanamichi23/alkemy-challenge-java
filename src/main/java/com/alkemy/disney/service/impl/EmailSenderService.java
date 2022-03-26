package com.alkemy.disney.service.impl;

import com.alkemy.disney.service.IEmailSenderService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class EmailSenderService implements IEmailSenderService {

    @Value("${SENDGRID_API_KEY}")
    private String apiKey;

    @Override
    public void sendRegistrationEmail(String toAddress) throws IOException
    {
        Email from = new Email("hana23@gmail.com");
        String subject = "Welcome to Disney!";
        Email to = new Email(toAddress);
        Content content = new Content("text/plain", "Te damos la bienvenida a Disney, donde podrás encontrar a tus personajes favoritos " +
                "y disfrutar de sus series y películas más entrañables :)");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}
