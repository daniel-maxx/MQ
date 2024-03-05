package com.br.mq.model;

//create a class to represent the message that will be sent and receive to the queue

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDTO {

    //create a class to represent the message that will be sent and receive to the queue
    private String messageTextSend;
    private String messageTextReceived;
    private String queueName;
    private String queueManager;
    private String channel;
    private String host;
    private String port;
    private String user;
    private String password;

}
