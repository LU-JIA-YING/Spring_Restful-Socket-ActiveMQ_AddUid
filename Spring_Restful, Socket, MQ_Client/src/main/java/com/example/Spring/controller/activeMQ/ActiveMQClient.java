package com.example.Spring.controller.activeMQ;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

//  queue
@Component
public class ActiveMQClient {

    @Autowired
    private JmsTemplate jmsTemplate;

    private static final Logger logger = Logger.getLogger(ActiveMQClient.class);

    public void send(String msg) throws JMSException {

        String uid = java.util.UUID.randomUUID().toString();
        jmsTemplate.send(new ActiveMQQueue("request.queue"), messageCreator -> {

            TextMessage message = messageCreator.createTextMessage();
            message.setText(msg);
            message.setStringProperty("uid", uid);
            return message;
        });

        System.out.println("send request");
        Message jmsMessage = jmsTemplate.receiveSelected("response.queue", "uid='" + uid + "'");
        if (jmsMessage == null) {
            System.out.println("receive time out ");
        } else {
            TextMessage textMsg = (TextMessage) jmsMessage;
            System.out.println("=============================================");
            System.out.println("uid=" + jmsMessage.getStringProperty("uid"));
            System.out.println("reponsemsg=" + textMsg.getText());
            System.out.println("=============================================");
        }
    }

//    @JmsListener(destination = "response.queue", containerFactory = "queueConnectionFactory")
//    public void consumeMessageQueue(String response) {
//
//        logger.info("Message received from activemq queue---\n" + response);
//        System.out.println("=================================================\n");
////        System.out.println("Message received from activemq queue---\n" + response);
//    }

}
