package org.javaboy.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class HelloController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        return helloService.hello();
    }

    @GetMapping("/hello2")
    public String hello2() {
        HystrixRequestContext cxt = HystrixRequestContext.initializeContext();
        HelloCommand helloCommand = new HelloCommand(
                HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("javaboy"))
                , restTemplate, "javaboy");
        String s = helloCommand.execute();
        System.out.println(s);
        HelloCommand helloCommand2 = new HelloCommand(
                HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("javaboy"))
                , restTemplate, "javaboy");
        Future<String> s1 = helloCommand2.queue();
        String s2 = null;
        try {
            s2 = s1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(s1);
        s = s + "\n</br>" + s2;
        cxt.close();
        return s;
    }

    @GetMapping("/hello3")
    public String hello3() {
        Future<String> future = helloService.hello2();
        String s = null;
        try {
            s = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return s;
    }


    @GetMapping("/hello4")
    public void hello4() throws ExecutionException, InterruptedException {
        HystrixRequestContext cxt = HystrixRequestContext.initializeContext();
        //第一次请求，数据被缓存下来
        helloService.hello3("javaboy");
        //删除数据，同时删除缓存
        helloService.deleteUserByName("javaboy");
        //第二次请求时，虽然参数还是 javaboy ，但是缓存数据已经没了，所以这一次，provider 还是会收到请求
        helloService.hello3("javaboy");
        cxt.close();
    }

    @Autowired
    UserService userService;

    @GetMapping("/hello5")
    public void hello5() throws ExecutionException, InterruptedException {
        HystrixRequestContext cxt = HystrixRequestContext.initializeContext();

        UserCollapseCommand command1 = new UserCollapseCommand(userService, 99);
        UserCollapseCommand command2 = new UserCollapseCommand(userService, 98);
        UserCollapseCommand command3 = new UserCollapseCommand(userService, 97);
        Future<User> q1 = command1.queue();
        Future<User> q2 = command2.queue();
        Future<User> q3 = command3.queue();
        User u1 = q1.get();
        User u2 = q2.get();
        User u3 = q3.get();
        Thread.sleep(2000);
        UserCollapseCommand command4 = new UserCollapseCommand(userService, 96);
        Future<User> q4 = command4.queue();
        User u4 = q4.get();
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
        System.out.println(u4);
        cxt.close();
    }

    @GetMapping("hello6")
    public void hello6() throws ExecutionException, InterruptedException {
        HystrixRequestContext cxt = HystrixRequestContext.initializeContext();

        Future<User> q1 = userService.getUserById(99);
        Future<User> q2 = userService.getUserById(98);
        Future<User> q3 = userService.getUserById(97);
        User u1 = q1.get();
        User u2 = q2.get();
        User u3 = q3.get();
        Thread.sleep(2000);
        Future<User> q4 = userService.getUserById(96);
        User u4 = q4.get();
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
        System.out.println(u4);
        cxt.close();
    }

}
