package org.javaboy.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloContrller {
    @Autowired
    MyChannel myChannel;
    @GetMapping("/hello")
    public void hello(){
        myChannel.output().send(MessageBuilder.withPayload("hello spring cloud stream!").build());
    }
}
