/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.various.controller;

import com.aquino.various.model.Member;
import com.aquino.various.repositories.AddressRepository;
import com.aquino.various.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author b005
 */
@Controller
@RequestMapping("users")
public class MemberController {
    
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    
    
    @GetMapping("register")
    public String registerForm(Model model) {
        model.addAttribute("member", new Member());
        return "register";
    }
    
    @PostMapping("/register")
    public String form(@ModelAttribute("member") Member member) throws Exception {
        if(userRepository.findByUsername(member.getUsername()) != null)
            throw new Exception("already exists");
        member.setEnabled(true);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setAddress(addressRepository.save(member.getAddress()));
        userRepository.save(member);
        return "login";
    }
    
    @GetMapping("/register/check")
    public String check(@RequestParam("username") String username, Model model) {
        Member member = userRepository.findByUsername(username);
        if(member == null)
            model.addAttribute("use", "You may use this username!");
        else model.addAttribute("use", "This username is not available");
        return "check";
    }
    
    @ResponseBody
    @GetMapping("/me") 
    public String userInfo(@AuthenticationPrincipal Member member){
        
        
        return String.format("Id: %d%nUsername: %s%nPassword: %s%n"
                + "Age: %s%nEmail:%s%nPost Code:%s%nAddress:%s",member.getId(),member.getUsername()
                ,member.getPassword(),member.getAge(),member.getEmail()
                ,member.getAddress().getPostCode(),member.getAddress().getFullAddress());
    }
    
}
