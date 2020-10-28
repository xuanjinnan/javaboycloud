package org.javaboy.provider;

import module.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @GetMapping("/user/{ids}")
    public List<User> getUserByIds(@PathVariable String ids) {
        System.out.println(ids);
        String[] split = ids.split(",");
        List<User> users = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            User u = new User();
            u.setId(Integer.parseInt(split[i]));
            users.add(u);
        }
        return users;
    }
}
