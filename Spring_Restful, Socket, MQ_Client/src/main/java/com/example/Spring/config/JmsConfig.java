package com.example.Spring.config;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.util.backoff.FixedBackOff;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfig {

    //  https://dev.to/adzubla/mixing-pub-sub-and-point-to-point-in-spring-boot-36og
    //  ConnectionFactory 自動配置Queue or Topic
    @Bean
    public JmsListenerContainerFactory<?> queueConnectionFactory(ConnectionFactory connectionFactory,
                                                                 DefaultJmsListenerContainerFactoryConfigurer configure) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configure.configure(factory, connectionFactory);
        factory.setBackOff(new FixedBackOff(5000, 5));  //  activeMQ配置監聽器重連服務器(5000=>5秒,5=>5次)
        factory.setPubSubDomain(false);
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> topicConnectionFactory(ConnectionFactory connectionFactory,
                                                                 DefaultJmsListenerContainerFactoryConfigurer configure) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configure.configure(factory, connectionFactory);
        factory.setBackOff(new FixedBackOff(5000, 5));
        factory.setPubSubDomain(true);
        return factory;
    }

//    @Bean
//    public DynamicDestinationResolver destinationResolver() {
//        return new DynamicDestinationResolver() {
//            @Override
//            public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain) throws JMSException, JMSException {
//                if (destinationName.endsWith(".topic")) {
//                    pubSubDomain = true;
//                }
//                return super.resolveDestinationName(session, destinationName, pubSubDomain);
//            }
//        };
//    }
}
