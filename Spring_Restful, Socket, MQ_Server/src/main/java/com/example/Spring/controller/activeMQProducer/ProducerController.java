package com.example.Spring.controller.activeMQProducer;

import com.example.Spring.service.TransferService;
import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.TextMessage;

//  queue
@RestController
public class ProducerController {

    @Autowired
    private TransferService transferService;

    @Autowired
    JmsTemplate jmsTemplate;

    @JmsListener(destination = "request.queue")
    public void receive(final Message jsonMessage) throws Exception {
        String messageData = null;
        System.out.println("uid = " + jsonMessage.getStringProperty("uid"));
        if (jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) jsonMessage;
            messageData = textMessage.getText();
            System.out.println("receive request:" + messageData);
            String response = transferService.getResponse(messageData);

            jmsTemplate.send("response.queue", messageCreator -> {
                TextMessage message = messageCreator.createTextMessage();
                message.setText(response);
                message.setStringProperty("uid", jsonMessage.getStringProperty("uid"));
//                message.setStringProperty("uid", "111");
                return message;
            });
            System.out.println("send response data");
            System.out.println("==========================================================");
        }
    }

//    @JmsListener(destination = "request.queue", containerFactory = "queueConnectionFactory")
//    @SendTo("response.queue")
//    public String getRequest(String request) throws Exception {
//        String response = transferService.getResponse(request);
//        return response;
//    }
}
