package com.basic.example;

import org.apache.camel.builder.RouteBuilder;

public class TransferFileInTargetFolder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        try{
            from( "file:d:/vids").to("file:d:/temp");
            System.out.println("inside route");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
