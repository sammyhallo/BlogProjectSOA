package com.rsakin.blogapp.controller;

import com.rsakin.blogapp.entity.Role;
import com.rsakin.blogapp.entity.User;
import com.rsakin.blogapp.service.impl.UserService;
import com.rsakin.blogapp.pojo.PojoUserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public String register(@RequestBody PojoUserRegistration userRegistration) {
        if (!userRegistration.getPassword().equals(userRegistration.getPasswordConfirmation()))
            return "Error the two passwords do not match";
        else if (userService.getUser(userRegistration.getUsername()) != null)
            return "Error this username already exists";

        //Checking for non alphanumerical characters in the username.
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        if (pattern.matcher(userRegistration.getUsername()).find())
            return "No special characters are allowed in the username";

        userService.save(
                new User(
                        userRegistration.getUsername(),
                        userRegistration.getPassword(),
                        Arrays.asList(
                                new Role(1L, "USER"),
                                new Role(2L, "ACTUATOR")
                        )
                )
        );
        return "User created";
    }

    @GetMapping(value = "/users")
    public List<User> users() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/logouts")
    public void logout(@RequestParam(value = "access_token") String accessToken) {
    }

//    @GetMapping(value = "/getUsername")
//    public String getUsername() {
//        return SecurityContextHolder.getContext().getAuthentication().getName();
//    }

}

