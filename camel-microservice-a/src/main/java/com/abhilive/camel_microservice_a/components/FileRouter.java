package com.abhilive.camel_microservice_a.components;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class FileRouter extends RouteBuilder {
    public static final String FROM_DIR = "D:/Projects/Repos/ApacheCamel/camel-microservice-a/files/input";
    public static final String TO_DIR = "D:/Projects/Repos/ApacheCamel/camel-microservice-a/files/output";
    public static final String APPEND = "&fileExist=Append";

    @Override
    public void configure() throws Exception {
        from("file://"+FROM_DIR)
                .log("${body}")
                .to("file://"+TO_DIR);
    }
}
