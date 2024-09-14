package com.yangyang.controller;


import com.yangyang.domain.ResponseResult;
import com.yangyang.domain.entity.User;
import com.yangyang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/system/user")
    public ResponseResult addUser(@RequestBody User user) {

        return userService.addUser(user);
    }


    @DeleteMapping("/system/user/{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id) {

        return userService.deleteUser(id);

    }

    //TODO user_role表的update联合主键的修改

    

}
