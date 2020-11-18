package org.javaboy.sleath2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);
    @Async
    public String backgroundFun(){
        logger.info("background");
        return backgroundFun2();
    }
    @Async
    public String backgroundFun2(){
        logger.info("background2");
        return backgroundFun3();
    }
    @Async
    public String backgroundFun3() {
        logger.info("background3");
        return "backgroundFun3";
    }
    @Scheduled(cron = "0/10 * * * * ?")
    public void schedul(){
        backgroundFun();
    }
}
