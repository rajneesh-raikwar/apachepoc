package com.basic.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;


public class SimpleRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:C:/inputFolder?noop=true").doTry().process(new MyProcessor()).to("file:C:/outputFolder")
                .doCatch(CamelCustomException.class).process(new Processor() {

            public void process(Exchange exchange) throws Exception {
                System.out.println("handling ex");
            }
        }).log("Received body ");

        from("file:C:/inbox?noop=true").process(new MyProcessor()).to("file:C:/outbox");
    }

}
