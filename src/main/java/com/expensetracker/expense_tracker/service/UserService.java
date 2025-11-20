package com.expensetracker.expense_tracker.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import com.expensetracker.expense_tracker.entity.User;
import com.expensetracker.expense_tracker.repository.UserRepository;
import com.expensetracker.expense_tracker.util.JWTUtil;

@Service
public class UserService {
    
    @Autowired
    UserRepository userrepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTUtil jwtService;

    public List<User> getAllusers() {
        return userrepo.findAll();
    }

    public User signup(User newuser){
        if(userrepo.findByEmail(newuser.getEmail()).isPresent()){
            throw new RuntimeException("User already exists!!");
        }
        newuser.setPassword(encoder.encode(newuser.getPassword()));
        return userrepo.save(newuser);
    }

    public User login(String email,String password){
        Optional<User> u=userrepo.findByEmail(email);
        
        if(u.isPresent()&& u.get().getPassword().equals(password)){
                   

            return u.get();
        }
        else{
            throw new RuntimeException("Invalid email or password!!"); 
        }
    }

    public String getTokenString(User user) {
        Authentication authentication=
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        // System.out.println("Authenticated!!!!");
        if(authentication.isAuthenticated()){

            return jwtService.generateToken(user.getEmail());
        }
        return "Failed";
    }

    public User updatedUserName(User updatedUser, int id) {
        if(!userrepo.existsById(id)){
            throw new RuntimeException("User Not Found!!");
        }
        User temp=userrepo.findById(id).get();
        temp.setName(updatedUser.getName());
        userrepo.save(temp);
        return temp;
    }

    
}
