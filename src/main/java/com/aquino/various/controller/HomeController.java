/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.various.controller;

import com.aquino.various.model.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author b005
 */
@Controller
public class HomeController {
    
    Map<String, Integer> map;
    
    @Autowired
    private HttpServletRequest request;

    public HomeController() {
        this.map = new HashMap<>();
    }
    
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("objectForm", new User());
        return "attributeForm";
    }
    
    @PostMapping("/form")
    public String form(@ModelAttribute("objectForm") User user,Model model) {
        model.addAttribute("user", user);
        System.out.println(user.getName());
        return "attributeForm";
    }
    
    @GetMapping("/session")
    public String countSession(Model model) {
        String id = RequestContextHolder.currentRequestAttributes().getSessionId();
        if(map.containsKey(id)) {
            map.put(id, map.get(id)+1);
            model.addAttribute("count", map.get(id));
            return "session";
        } else {
            map.put(id, 1);
            model.addAttribute("count", 1);
            return "session";
        }
    }
    @PostMapping("/session")
    public String reset(Model model) {
        String id = RequestContextHolder.currentRequestAttributes().getSessionId();
        if(map.containsKey(id)) {
            map.put(id, 0);
            model.addAttribute("count", 0);
        }
        return "session";
    }
    
    @GetMapping("/cookie")
    public String cookie(HttpServletResponse response,HttpServletRequest request, Model model) {
        
        model.addAttribute("cookie", request.getCookies());
        Cookie cookie = new Cookie("Alex",LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
        cookie.setMaxAge(60 * 24 * 3600);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "cookie";
    }
    
    @PostMapping("/cookie")
    public String cookieDelete(@RequestParam String delete,HttpServletRequest request,HttpServletResponse response, Model model) {
        System.out.println(delete);
        Cookie[] cookie = request.getCookies();
        for (int i = 0; i < cookie.length; i++){
            System.out.println("Name: "+ cookie[i].getName());
            
            if(cookie[i].getName().equals(delete)) {
                 cookie[i].setMaxAge(0);
                 response.addCookie(cookie[i]);
                 System.out.println("deleted");
            }
               
                
        }
        model.addAttribute("cookie", request.getCookies());
        
        return "cookie";
        
    }
    
    @GetMapping("/files")
    public String fileForm() {
        return "files";
    }
    
    
    @PostMapping("/files")
    public String files(@RequestParam List<MultipartFile> files, Model model) {
        String savePath = request.getServletContext().getRealPath("/uploads/");
        ArrayList<String> names = new ArrayList();
        files.forEach((file) -> {
            try {
                file.transferTo(new File(savePath +file.getOriginalFilename()));
                names.add(file.getOriginalFilename());
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        model.addAttribute("files",names);
        return "files";
    }
    
    @ResponseBody
    @GetMapping("/uploads/{fileName}")
    public byte[] findFile(@PathVariable String fileName,HttpServletResponse response) throws FileNotFoundException {
        String path = request.getServletContext().getRealPath("/uploads/" + fileName);
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
               
        
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
           
    
    
    
}
