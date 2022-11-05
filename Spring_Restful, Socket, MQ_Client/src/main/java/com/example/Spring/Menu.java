package com.example.Spring;

import com.example.Spring.controller.activeMQ.ActiveMQClient;
import com.example.Spring.controller.restful.RestClient;
import com.example.Spring.controller.socket.SocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Component
public class Menu implements CommandLineRunner {

//    @Autowired
//    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue requestQueue;
    @Autowired
    ActiveMQClient activeMQClient;

    @Override
    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("輸入 MQ or Socket or Restful: ");
            String sendDataType = scanner.nextLine();
            String requestBody = "";
            switch (sendDataType.toLowerCase()) {
                case "mq": {
                    System.out.println("ActiveMQ request : ");
                    requestBody = scanner.nextLine();   //nextLine() or .next:https://blog.51cto.com/u_14917507/3836527
                    activeMQClient.send(requestBody);
//                    jmsMessagingTemplate.convertAndSend(requestQueue,requestBody);
                    break;
                }
                case "socket": {
                    System.out.println("Socket request : ");
                    requestBody = scanner.nextLine();
                    SocketClient.sendRequest(requestBody);
                    break;
                }
                case "restful": {
                    System.out.println("Restful request : ");
                    requestBody = scanner.nextLine();
                    RestClient.sendRestFulMessage(requestBody);
                    break;
                }
                case "exit": {
                    System.out.println("結束輸入");
                    System.exit(0);
                    break;
                }
                default:{
                    System.out.println("請輸入正確資料");
                }
            }

            TimeUnit.SECONDS.sleep(1);
        }
    }
}
