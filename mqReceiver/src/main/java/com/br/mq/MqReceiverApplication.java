package com.br.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MqReceiverApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(MqReceiverApplication.class, args);
    }
}
