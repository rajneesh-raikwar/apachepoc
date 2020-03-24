package com.basic.example;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelStarter {
    public static void main(String[] args) throws Exception {
        System.out.println("CamelStarter");
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new TransferFileInTargetFolder());
        context.addRoutes(new SimpleRouteBuilder());
        context.start();
        Thread.sleep(30000);
        context.stop();
    }
}
