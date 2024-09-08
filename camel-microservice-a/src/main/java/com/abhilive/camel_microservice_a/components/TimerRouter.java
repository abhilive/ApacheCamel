package com.abhilive.camel_microservice_a.components;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class TimerRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    private SimpleLoggingProcessing loggingProcessing;

    @Override
    public void configure() throws Exception {
        /**/
        from("timer:first-time")
                .log("${body}")
                .transform().constant("My constant message")
                .log("${body}")
//                .transform().constant("Time now is:" + LocalDateTime.now())
                .bean(getCurrentTimeBean, "getCurrentTime")
                .log("${body}")
//                .bean(loggingProcessing)
                .process(new SimpleLoggingProcessor())
                .log("${body}")
                .to("log:first-timer");
    }
}

@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "Current time is: "+ LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessing {
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessing.class);
    public void process(String message) {
        logger.info("SimpleLoggingProcessing: {}", message);
    }
}

@Component
class SimpleLoggingProcessor implements Processor {

    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("SimpleLoggingProcessor: {}", exchange.getMessage().getBody());
    }
}
