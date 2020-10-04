package org.javaboy.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import org.junit.Test;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class resilience4jTest {
    @Test
    public void test1(){
        // 获取一个 CircuitBreakerRegistry 实例,可以 ofDefaults 来获取 CircuitBreakerRegistr 实例，也可以自定义
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();

        CircuitBreakerConfig config =  CircuitBreakerConfig.custom()
                //故障率阈值百分比，超过这个阈值，断路器就会打开
                .failureRateThreshold(50)
                //断路器保持打开的时间，在到达设置的时间后，断路器会进入 half open 状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                // 当断路器处于 half open 状态，唤醒缓冲区的大小
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry r1 = CircuitBreakerRegistry.of(config);
        CircuitBreaker cb1 = r1.circuitBreaker("javaboy");
        CircuitBreaker cb2 = r1.circuitBreaker("javaboy2", config);
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb1, () -> "Hello resilience4j");
        Try<String> result = Try.of(supplier)
                .map(v -> v + "hello world");
        System.out.println(result.isSuccess());
        System.out.println(result.get());
    }
    @Test
    public void test2(){
        CircuitBreakerConfig config =  CircuitBreakerConfig.custom()
                //故障率阈值百分比，超过这个阈值，断路器就会打开
                .failureRateThreshold(50)
                //断路器保持打开的时间，在到达设置的时间后，断路器会进入 half open 状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                // 当断路器处于 half open 状态，唤醒缓冲区的大小
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry r1 = CircuitBreakerRegistry.of(config);
        CircuitBreaker cb1 = r1.circuitBreaker("javaboy");
        System.out.println(cb1.getState());
        cb1.onError(0, TimeUnit.SECONDS,new RuntimeException());
        System.out.println(cb1.getState());
        cb1.onError(0, TimeUnit.SECONDS,new RuntimeException());
        System.out.println(cb1.getState());
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb1, () -> "Hello resilience4j");
        Try<String> result = Try.of(supplier)
                .map(v -> v + "hello world");
        System.out.println(result.isSuccess());
        System.out.println(result.get());
    }
    @Test
    public void test3(){
        RateLimiterConfig config = RateLimiterConfig.custom().limitRefreshPeriod(Duration.ofMillis(1000))
                .limitForPeriod(2)
                .timeoutDuration(Duration.ofMillis(1000))
                .build();
        RateLimiter rateLimiter = RateLimiter.of("javaboy", config);

        CheckedRunnable checkedRunnable = RateLimiter.decorateCheckedRunnable(rateLimiter, () -> {
            System.out.println(new Date());
        });
        Try.run(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .onFailure(t-> System.out.println(t.getMessage()));
    }
    @Test
    public void test4(){
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(2)
                .waitDuration(Duration.ofMillis(500))
                .retryExceptions(RuntimeException.class)
                .build();
        Retry retry = Retry.of("javaboy", config);
        Retry.decorateRunnable(retry,new Runnable(){
            int count = 0;
            @Override
            public void run() {
                System.out.println(count);
                if(count++ < 3){
                    throw new RuntimeException();
                }
                System.out.println("retry");
            }
        }).run();
    }
}
