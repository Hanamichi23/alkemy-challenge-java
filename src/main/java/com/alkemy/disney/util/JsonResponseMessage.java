package com.alkemy.disney.util;


public class JsonResponseMessage {

    String message;

    public JsonResponseMessage() {
    }

    public JsonResponseMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    @Override
    public String toString() {
        return "JsonResponseMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}