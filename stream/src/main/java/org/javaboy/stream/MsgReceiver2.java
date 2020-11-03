package org.javaboy.stream;

import org.slf4j.Logger;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import static org.slf4j.LoggerFactory.getLogger;

@EnableBinding(MyChannel.class)
public class MsgReceiver2 {
    private static final Logger logger = getLogger(MsgReceiver2.class);

    @StreamListener(MyChannel.INPUT)
    public void receive(Object payload){
        logger.info("receive2:" + payload);
    }
}
