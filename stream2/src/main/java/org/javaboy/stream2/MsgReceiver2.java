package org.javaboy.stream2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.Date;

@EnableBinding(MyChannel.class)
public class MsgReceiver2 {
    private static final Logger logger = LoggerFactory.getLogger(MyChannel.class);

    @StreamListener(MyChannel.INPUT)
    public void receive(Object payload){
        logger.info("received2:" + payload + ":" + new Date());
    }
}
