package org.javaboy.openfeign;

import org.javaboy.api.IUserService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "provider", fallback = HelloServiceFallback.class)
public interface HelloService extends IUserService {


}
