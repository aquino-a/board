/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.board.controller;

import com.aquino.board.model.Member;
import com.aquino.board.repositories.AddressRepository;
import com.aquino.board.repositories.UserRepository;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    private TokenStore tokenStore;
    
    @GetMapping("register")
    public String registerForm() {
        return "register";
    }
    
    @PostMapping("/register")
    public String form(Member member) throws Exception {
        if(userRepository.findByUsername(member.getUsername()) != null)
            throw new Exception("already exists");
        member.setEnabled(true);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setAddress(addressRepository.save(member.getAddress()));
        userRepository.save(member);
        return "redirect:/login";
    }
    
    @GetMapping("/register/check")
    public String check(@RequestParam("username") String username, Model model) {
        Member member = userRepository.findByUsername(username);
        if(member == null)
            model.addAttribute("use", "You may use this username!");
        else model.addAttribute("use", "This username is not available");
        return "check";
    }
    
    @GetMapping("/logout")
    public String logOut(
            HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam(value="redirect_url", required=true)String redirectUrl) {
        HttpSession session= request.getSession(false);
        SecurityContextHolder.clearContext();
        if(session != null) {
            session.invalidate();
        }
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            model.addAttribute("cookie", request.getCookies());
        }
        return "redirect:"+ redirectUrl;
    }
    
//    @ResponseBody
//    @GetMapping("/me") 
//    public ResponseEntity<Member> userInfo(@AuthenticationPrincipal Member member){
////        if(member == null)
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        return ResponseEntity.ok(member);
//    }
    
}
