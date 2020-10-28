package org.javaboy.consulconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
public class HelloController {
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello() {
        ServiceInstance instance = loadBalancerClient.choose("consul-provider");
        URI uri = instance.getUri();
        return restTemplate.getForObject(uri.toString() + "/hello", String.class);
    }
}
