package org.javaboy.stream2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    MyChannel myChannel;
    @GetMapping("/hello")
    public void hello(){
        MessageChannel messageChannel = myChannel.output();
        MessageBuilder<String> builder = MessageBuilder.withPayload("hello spring cloud stream!").setHeader("x-delay",3000);
        Message<String> message = builder.build();
        boolean send = messageChannel.send(message);
        logger.info("" + send + new Date());

    }
}
