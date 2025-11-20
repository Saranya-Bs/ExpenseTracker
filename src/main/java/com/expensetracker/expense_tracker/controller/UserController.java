package com.expensetracker.expense_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.expense_tracker.entity.User;
import com.expensetracker.expense_tracker.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    UserService userservice;

    @GetMapping()
    public List<User> getAllusers(){
        return userservice.getAllusers();
    }

    

    @PostMapping("/signup")
    public User signup(@RequestBody User newuser){
        System.out.println("Inside Signup controllllllllll");
        return userservice.signup(newuser);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return userservice.getTokenString(user);
    }

    @PutMapping("/update/{id}")
    public User updatUserName(@RequestBody User updatedUser,@PathVariable int id){
        return userservice.updatedUserName(updatedUser,id);
    }

}
