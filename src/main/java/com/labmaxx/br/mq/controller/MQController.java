package com.labmaxx.br.mq.controller;

import com.ibm.mq.MQException;
import com.labmaxx.br.mq.service.MQConnectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MQController {

    private final MQConnectService mqConnectService;

    public MQController(MQConnectService mqConnectService) {
        this.mqConnectService = mqConnectService;
    }


    //create method to connect to MQ using MQConnectService class
    @GetMapping("/connect")
    public void connect() throws MQException {
        MQConnectService.connect();
    }

}
