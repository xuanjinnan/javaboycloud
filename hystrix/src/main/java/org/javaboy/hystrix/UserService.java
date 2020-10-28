package org.javaboy.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import module.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class UserService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCollapser(batchMethod = "getUserByIds", collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds", value = "200")})
    public Future<User> getUserById(Integer id) {
        return null;
    }

    @HystrixCommand
    public List<User> getUserByIds(List<Integer> ids) {
        String idsStr = StringUtils.join(ids, ",");
        System.out.println(idsStr);
        User[] forObject = restTemplate.getForObject("http://provider/user/{1}", User[].class, idsStr);
        return Arrays.asList(forObject);
    }
}
