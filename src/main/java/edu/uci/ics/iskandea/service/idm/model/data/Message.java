package edu.uci.ics.iskandea.service.idm.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message
{
    @JsonProperty("message")
    public String message;

    public Message(String message)
    {
        this.message = message;
    }
}
