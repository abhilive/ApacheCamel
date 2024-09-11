package com.abhilive.camel_microservice_a.components;

import com.abhilive.camel_microservice_a.dtos.TransactionDto;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Component
public class WireTap extends RouteBuilder {

    static final String SENDER = "sender";
    static final String RECEIVER = "receiver";
    static final String AUDIT = "audit";

    static final String ACTIVEMQ_QUEUE_SENDER = "activemq:sender-transaction-queue";

    static final String ACTIVEMQ_QUEUE_RECEIVER = "activemq:receiver-transaction-queue";
    static final String ACTIVEMQ_QUEUE_AUDIT = "activemq:audit-transaction-queue";

    static final String TIMER_ACTIVEMQ = "timer:active-mq-timer?period=10000";

    static final String AUDIT_TRANSACTION_ROUTE = "direct:audit-transaction";

    static final String ACTIVEMQ_URI = "";

    @Override
    public void configure() throws Exception {
        fromF(ACTIVEMQ_QUEUE_SENDER, SENDER, SENDER)
                .unmarshal().json(JsonLibrary.Jackson, TransactionDto.class)
                .wireTap(AUDIT_TRANSACTION_ROUTE)
                .process(this::enrichTransactionDto)
                .marshal().json(JsonLibrary.Jackson, TransactionDto.class)
                .toF(ACTIVEMQ_QUEUE_RECEIVER, RECEIVER, RECEIVER)
                .log("Money Transferred: ${body}");

        from(AUDIT_TRANSACTION_ROUTE)
                .process(this::enrichTransactionDto)
                .marshal().json(JsonLibrary.Jackson, TransactionDto.class)
                .toF(ACTIVEMQ_QUEUE_AUDIT, AUDIT, AUDIT);
    }

    private void enrichTransactionDto(Exchange exchange) {
        TransactionDto transactionDto = exchange.getMessage().getBody(TransactionDto.class);
        transactionDto.setTransactionDate(new Date());

        Message message = new DefaultMessage(exchange);
        message.setBody(transactionDto);

        exchange.setMessage(message);
    }
}
