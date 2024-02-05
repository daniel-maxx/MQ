package com.labmaxx.br.mq.controller;

import com.ibm.mq.MQException;
import com.labmaxx.br.mq.model.MessageDTO;
import com.labmaxx.br.mq.service.MQConnectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MQController {

    private final MQConnectService mqConnectService;

    public MQController(MQConnectService mqConnectService) {
        this.mqConnectService = mqConnectService;
    }


    //create method to connect to MQ using MQConnectService class
    @GetMapping("/connect")
    public String connect(Model model) throws MQException {
        MessageDTO messageDTO = MQConnectService.connect();

        //set the messageDTO object to the model
        model.addAttribute("messageDTO", messageDTO);

        return "Sucess";

    }

}
