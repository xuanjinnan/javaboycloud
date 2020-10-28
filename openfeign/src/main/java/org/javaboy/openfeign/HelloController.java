package org.javaboy.openfeign;

import module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String hello() throws UnsupportedEncodingException {
        String hello = helloService.hello();
        System.out.println(hello);
        String s = helloService.hello2("江南一点雨");
        System.out.println(s);
        User user = new User();
        user.setId(1);
        user.setUsername("javaboy");
        user.setPassword("123");
        User user1 = helloService.addUser2(user);
        System.out.println(user1);
        helloService.deleteUser2(1);
        helloService.getUserByName(URLEncoder.encode("江南一点雨", "UTF-8"));
        return hello;

    }
}
