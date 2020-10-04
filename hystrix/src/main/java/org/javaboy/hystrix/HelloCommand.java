package org.javaboy.hystrix;


import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

public class HelloCommand extends HystrixCommand<String> {
    RestTemplate restTemplate;
    String name;

    public HelloCommand(Setter setter, RestTemplate restTemplate, String name) {
        super(setter);
        this.restTemplate = restTemplate;
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
//        int i = 1/0;
        return restTemplate.getForObject("http://provider/hello2?name={1}",String.class,name);
    }

    @Override
    protected String getCacheKey() {
        return name;
    }


    @Override
    protected String getFallback() {
        return "error-extends" + getExecutionException().getMessage();
    }


}
