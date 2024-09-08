package com.abhilive.camel_microservice_a.components;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQSenderRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        //timer
        from("timer:active-mq-timer?period=10000")
                .transform().constant("My message for ActiveMQ")
                .log("${body}")
                .to("activemq:my-activemq-queue");
        //queue
    }
}
