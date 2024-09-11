package com.abhilive.camel_microservice_a.components;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.util.concurrent.TimeUnit.SECONDS;
/**
 * SEDA - Staged Event Driven Architecture
 * Refers to an approach to software architecture that decomposes a complex, event driven application
 * into a set of stages connected by queues.
 *
 * It avoids the high overhead associated with thread based concurrency model (i.e. locking, unlocking,
 * and polling for locks), and decouples event and thread scheduling from application logic.
 * */

@Component
public class SedaRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:ping?period=200")
                .process(exchange -> {
                    Message message = new DefaultMessage(exchange);
                    message.setBody(new Date());
                    exchange.setMessage(message);
                })
                .to("seda:weightLifter?multipleConsumers=true");

        from("seda:weightLifter?multipleConsumers=true")
                .to("direct:complexProcess");

        from("direct:complexProcess")
                .log("${body}")
                .process(exchange -> SECONDS.sleep(2))
                .end();
    }
}
