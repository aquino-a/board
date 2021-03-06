/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.board.repositories;

import com.aquino.board.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author b005
 */
@Service
public class MemberService implements UserDetailsService {
    
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(string);
        if(user == null)
            throw new UsernameNotFoundException(string);
        return user;
    }
    
    public Member findByUsername(String name) {
        return userRepository.findByUsername(name);
    }
    
}
