package org.javaboy.sleuth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    Log log = LogFactory.getLog(HelloService.class);
    @Async
    public String backgroundFun(){
        log.info("backgroundFun");
        return backgroundFun2();
    }
    @Async
    public String backgroundFun2(){
        log.info("backgroundFun2");
        return "backgroundFun2";
    }
    /*@Scheduled(cron = "* 0/10 * * * ?")
    public void schel(){
        log.info("start");
        backgroundFun();
        log.info("end");
    }*/
}
