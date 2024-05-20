package com.example.camelmicroservices.router.a.processor;


import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;


//@Component
public class GreetingRouter extends RouteBuilder{

    @Autowired
    CurrentTime currentTime;

    @Autowired
    SimpleLoggingProcessingComponent loggingComponent;



    @Override
    public void configure() throws Exception {
       from("timer:Timer1")
               .transform().constant("This is a constant Message")
               .log("${body}")
                //.transform().constant("Time is: "+ LocalTime.now())
                .bean("currentTime")
               .log("${body}")
               .bean(loggingComponent)
               .process(new SimpleLoggingProcessor())
                .to("log:Timer1");

       /* from("timer: Timer2")
                .bean(currentTime, "getCurrentTime")
                .to("log:Timer1");*/
    }
}

@Component
class CurrentTime{
    public String getCurrentTime() {
        return "Time is :" +LocalTime.now();
    }
}

@Component
class SimpleLoggingProcessingComponent{

    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    public void process(String message) {
        logger.info("SimpleLoggingProcessingComponent ${}", message);
    }
}
