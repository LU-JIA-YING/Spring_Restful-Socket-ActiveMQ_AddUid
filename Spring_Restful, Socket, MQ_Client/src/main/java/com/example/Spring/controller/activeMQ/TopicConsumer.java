package com.example.Spring.controller.activeMQ;

import org.apache.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {

    private static final Logger logger = Logger.getLogger(TopicConsumer.class);

    @JmsListener(destination = "${mytopic}", containerFactory = "topicConnectionFactory")
    public void readActiveTopic(String msg) {

        logger.info("Message received from activemq topic\n" + msg);
        System.out.println("Topic1 : " + msg);
    }

    @JmsListener(destination = "${mytopic}", containerFactory = "topicConnectionFactory")
    public void readActiveTopic2(String msg) {

        logger.info("Message received from activemq topic\n" + msg);
        System.out.println("Topic2 : " + msg);
    }
}
