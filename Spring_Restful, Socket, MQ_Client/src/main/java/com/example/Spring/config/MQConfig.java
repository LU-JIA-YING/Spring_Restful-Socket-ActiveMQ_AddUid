package com.example.Spring.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import javax.jms.Topic;

//  JMS ActiveMQ:
//  https://javatute.com/spring-boot/spring-boot-jms-activemq-producer-and-consumer-example/
//  https://www.796t.com/article.php?id=18089
//  執行 apache-activemq-5.17.2\bin\win64\activemq.bat的檔案
//  http://localhost:8161/admin/ 帳密admin
@Configuration
@EnableJms  //開啟jms
public class MQConfig {

    @Value("${mytopic}")
    private String mytopic;
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("request.queue");
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic(mytopic);
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {

        //  建立ConnectionFactory工廠，需要填入名稱、密码、連接地址
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "failover:(tcp://localhost:61616)?initialReconnectDelay=1000&maxReconnectDelay=30000");
        //  initialReconnectDelay：表示第一次嘗試重連之前等待的時間
        //  maxReconnectDelay：預設30000，單位毫秒，表示兩次重連之間的最大時間間隔。
        activeMQConnectionFactory.setBrokerURL(brokerUrl);

        //設定 訊息佇列的重發機制
        RedeliveryPolicy queuePolicy = new RedeliveryPolicy();
        queuePolicy.setInitialRedeliveryDelay(0); // 初始重發延遲時間
        queuePolicy.setRedeliveryDelay(1000);//重發延遲時間
        queuePolicy.setUseExponentialBackOff(false);
        queuePolicy.setMaximumRedeliveries(2);// 最大重传次数

        RedeliveryPolicy topicPolicy = new RedeliveryPolicy();
        topicPolicy.setInitialRedeliveryDelay(0);
        topicPolicy.setRedeliveryDelay(1000);
        topicPolicy.setUseExponentialBackOff(false);
        topicPolicy.setMaximumRedeliveries(3);

        RedeliveryPolicyMap map = activeMQConnectionFactory.getRedeliveryPolicyMap();
        map.put(new ActiveMQQueue("response.queue"),queuePolicy);
        map.put(new ActiveMQTopic("mytopice.topic"),topicPolicy);

        activeMQConnectionFactory.setRedeliveryPolicyMap(map);

        return activeMQConnectionFactory;
    }
    //  ActiveMQ提供failover機制去實現斷線重連的高可用性，可以使得連接斷開之後，不斷的重試連接到一個或多個brokerURL

    //  消息的發送接收
    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(activeMQConnectionFactory());
        template.setPubSubDomain(true);
//        template.setDestinationResolver(destinationResolver());
        template.setDeliveryPersistent(true);
        template.setReceiveTimeout(100000);  //jmsTemplate的接收是阻塞式的接收，默認會一直阻塞等待，直到接收到了消息。也可以設置一個最長的等待時間參數，超過這個時間，接收的方法將得到null的結果。
        return template;
    }
    //JmsTemplate會自動為您建立Connection、Session、Message並進行傳送
}
