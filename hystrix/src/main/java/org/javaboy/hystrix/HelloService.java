package org.javaboy.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = ArithmeticException.class)
    public String hello(){
        int i = 1/0;
        return restTemplate.getForObject("http://provider/hello",String.class);
    }
    @HystrixCommand(fallbackMethod = "error2")
    @CacheResult//请求结果会被缓存起来，默认情况下 key 是方法的参数，value 是返回值
    public String hello3(String name){
        return restTemplate.getForObject("http://provider/hello2?name={1}",String.class,name);
    }
    @HystrixCommand
    @CacheRemove(commandKey = "hello3")
    public String deleteUserByName(String name){
        return null;
    }
    public String error2(String name){
        return "It's error2" ;
    }
    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = {ArithmeticException.class,RuntimeException.class})
    public Future<String> hello2(){
//        int i = 1/0;
        return new AsyncResult<String>(){
            @Override
            public String invoke() {
                return restTemplate.getForObject("http://provider/hello",String.class);
            }
        };
    }

    public String error(Throwable throwable){
        System.out.println(throwable);
        return "It's error" +  throwable.getMessage();
    }
}
