package com.mbans.sandbox.springbootsandbox.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

/**
 * Created by lumarmacy1 on 14/11/2018.
 */
@Configuration
@EnableJms
public class ActiceMqConfig {

    private static final Logger log = LoggerFactory.getLogger(ActiceMqConfig.class);

    @Value("${topic.name}")
    private String topicName;

    /**
     * Need to set 'setPubDomain' to true
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);

        //Set the shared subsription to true
        //Active MQ not compatible
        factory.setSubscriptionShared(true);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {

        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        //jmsTemplate.setDefaultDestination(destinationTopic());;
        jmsTemplate.setDefaultDestinationName(topicName);
        jmsTemplate.setConnectionFactory(connectionFactory);

        //jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());

        //Need to set to true as the default JmsTemplate is set to false and relates to queue not Topic connection
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }

    @JmsListener(destination = "${topic.name}", containerFactory = "myFactory", subscription = "sub1")
    public void listener(String message) {
        log.info("sub1 - Received <" + message + ">");
    }

    @JmsListener(destination = "${topic.name}", containerFactory = "myFactory", subscription = "sub1")
    public void listener2(String message) {
        log.info("sub2 - Received <" + message + ">");
    }
}
