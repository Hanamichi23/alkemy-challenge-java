package com.alkemy.disney.service;

import java.io.IOException;

public interface IEmailSenderService {

    void sendRegistrationEmail(String toAddress) throws IOException;
}
