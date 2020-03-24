package com.basic.example;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class EIPMessageFilter {

    public static void main(String[] args) throws Exception {

        final CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                from("file:src/data?noop=true").to("direct:incomingMessage");

// content-based router
                from("direct:incomingMessage")
                        .choice()
                        .when(header("CamelFileName").endsWith(".xml"))
                        .to("direct:xmlMessage")
                        .otherwise()
                        .to("direct:badMessage");

                from("direct:xmlMessage")
// applying Filter on messages
                        .filter(xpath("/person/city = 'London'"))
                        .process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                System.out.println("Received Filtered XML Message: "
                                        + exchange.getIn().getHeader("CamelFileName"));
                            }
                        });

                from("direct:badMessage")
                        .process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                System.out.println("Received Bad Message: "
                                        + exchange.getIn().getHeader("CamelFileName"));
                            }
                        });
            }
        });

// start the CamelContext to initiate Route
        context.start();
        Thread.sleep(300000);
// stop the CamelContext
        context.stop();
    }
}
