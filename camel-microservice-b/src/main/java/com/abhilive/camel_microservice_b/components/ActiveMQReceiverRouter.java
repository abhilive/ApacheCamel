package com.abhilive.camel_microservice_b.components;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQReceiverRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:my-activemq-queue")
                .log("${body}")
                .to("log:received-message-from-active-mq");

    }
}
