package org.javaboy.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    HelloService helloService;
    @GetMapping("/hello")
    public String hello(){
        logger.info("hello");
        return "hello spring cloud sleuth!";
    }
    @GetMapping("/hello2")
    public String hello2(){
        logger.info("hello2");
        return restTemplate.getForObject("http://localhost:8080/hello3",String.class);
    }
    @GetMapping("/hello3")
    public String hello3(){
        logger.info("hello3");
        return restTemplate.getForObject("http://localhost:8080/hello4",String.class);
    }
    @GetMapping("/hello4")
    public String hello4(){
        logger.info("hello4");
        return "hello4";
    }
    @GetMapping("/hello5")
    public String hello5(){
        logger.info("hello5");
        String s = helloService.backgroundFun();
        logger.info(s);
        return s;
    }
}
