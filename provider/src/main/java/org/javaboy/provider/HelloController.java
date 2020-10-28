package org.javaboy.provider;

//import org.javaboy.commons.User;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import module.User;
import org.javaboy.api.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

@RestController
public class HelloController implements IUserService {
    @Value("${server.port}")
    Integer port;

    @Override
    @RateLimiter(name = "rlA")
    public String hello() {
        String s = "hello   " + port;
        System.out.println(new Date());
        return s;
    }

    @Override
    public String hello2(String name) {
        System.out.println(new Date() + ">>> " + name);
        return "hello" + name;
    }

    @Override
    public User addUser2(@RequestBody User user) {
        return user;
    }

    @Override
    public void deleteUser2(@PathVariable Integer id) {
        System.out.println(id);
    }

    @Override
    public void getUserByName(@RequestHeader("name") String name) throws UnsupportedEncodingException {
        System.out.println(URLDecoder.decode(name, "UTF-8"));
    }


    @PutMapping("/user1")
    public void updateUser1(User user) {
        System.out.println(user);
    }


    @PostMapping("/user1")
    public User addUser1(User user) {
        return user;
    }

    @PutMapping("/user2")
    public void updateUser2(@RequestBody User user) {
        System.out.println(user);
    }

    @DeleteMapping("user1")
    public void deleteUser1(Integer id) {
        System.out.println(id);
    }

}
