package com.br.mq.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;

@Service
public class MQConnectService {

    private static String getConnectionInfo(String connectionInfo) {

        String parameter;

        try {

            Object obj = new JSONParser ().parse(new FileReader ("src/main/resources/connection_info.json"));


            JSONObject jsonObject = (JSONObject) obj;

            //validating values jsonObject.get(connectionInfo) is Long or String, if it is Long, it will convert to String
            if (jsonObject.get(connectionInfo) instanceof Long valueLong) {
                parameter = valueLong.toString();
            } else {
                parameter = (String) jsonObject.get(connectionInfo);
            }

        } catch (IOException | ParseException e) {
            System.out.println("Erro durante a leitura do arquivo de propriedades");
            throw new RuntimeException(e);
        }

        return parameter;
    }
}
