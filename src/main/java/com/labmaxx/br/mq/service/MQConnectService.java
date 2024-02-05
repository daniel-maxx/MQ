package com.labmaxx.br.mq.service;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.labmaxx.br.mq.model.MessageDTO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.FileReader;
import java.io.IOException;


@Service
public class MQConnectService {

    static JMSContext context = null;
    static Destination destination = null;
    static JMSProducer producer = null;
    static JMSConsumer consumer = null;


    private static String getConnectionInfo(String connectionInfo) {

        String parameter;

        try {

            Object obj = new JSONParser().parse(new FileReader("src/main/resources/connection_info.json"));
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

    private static String getAcessInfo(String accessInfo) {

        String parameter;

        try {

            Object obj = new JSONParser ().parse (new FileReader ("src/main/resources/administratorApiKey.json"));
            JSONObject jsonObject = (JSONObject) obj;

            //validating values jsonObject.get(connectionInfo) is Long or String, if it is Long, it will convert to String
            if (jsonObject.get(accessInfo) instanceof Long valueLong) {
                parameter = valueLong.toString();
            } else {
                parameter = (String) jsonObject.get(accessInfo);
            }

        } catch (IOException | ParseException e) {
            System.out.println ("Erro durante a leitura do arquivo de propriedades");
            throw new RuntimeException (e);
        }

        return parameter;
    }

    public static MessageDTO connect() {

        //create MessageDTO object to store the message that will be sent and received
        MessageDTO messageDTO = new MessageDTO();

        try {

            // create a connection factory
            JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
            JmsConnectionFactory cf = ff.createConnectionFactory();

            //Set the properties
            cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, getConnectionInfo("hostname"));
            cf.setIntProperty(WMQConstants.WMQ_PORT, Integer.parseInt(getConnectionInfo("listenerPort")));
            cf.setStringProperty(WMQConstants.WMQ_CHANNEL, getConnectionInfo("adminChannelName"));
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, getConnectionInfo("queueManagerName"));
            cf.setStringProperty(WMQConstants.WMQ_SSL_CIPHER_SUITE, "*TLS12");
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
            cf.setStringProperty(WMQConstants.USERID, getAcessInfo("mqUsername"));
            cf.setStringProperty(WMQConstants.PASSWORD, getAcessInfo("apiKey"));


            //Create JMS objects
            context = cf.createContext();
            destination = context.createQueue("queue:///" + "DEV.QUEUE.1");

            long uniqueNumber = System.currentTimeMillis() % 1000;
            TextMessage message = context.createTextMessage("Teste de conexao" + uniqueNumber);

            producer = context.createProducer();
            producer.send(destination, message);
            messageDTO.setMessageTextSend(message.getText());

            consumer = context.createConsumer(destination); // autoclosable
            String receivedMessage = consumer.receiveBody(String.class, 15000); // in ms or 15 seconds

            messageDTO.setMessageTextReceived(receivedMessage);

        } catch (JMSException jmsex) {
            System.out.println(jmsex.getErrorCode());
        } finally {
            if (context != null) {
                context.close();
            }
        }

        return messageDTO;
    }
}

