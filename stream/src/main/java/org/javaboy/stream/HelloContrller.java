package org.javaboy.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HelloContrller {
    private static final Logger logger = LoggerFactory.getLogger(HelloContrller.class);

    @Autowired
    MyChannel myChannel;
    @GetMapping("/hello")
    public void hello(){
        logger.info("send time: " + new Date());
        myChannel.output().send(MessageBuilder.withPayload("hello spring cloud" +
                " stream!").setHeader("x-delay",3000).build());
    }
}
