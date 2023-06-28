package com.ibm.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.ecommerce.entity.User;
import com.ibm.ecommerce.service.UserService;

import javax.annotation.PostConstruct;

/**
 * 
 * Here the access control policy is generated.
 * 
 *
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    
    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }
    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
    	return userService.registerNewUser(user);
    }

    //@PostMapping({"/registerNewUser"})
    //public User registerNewUser(@RequestBody User user) {
      //  return userService.registerNewUser(user);
    //}

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }
}
