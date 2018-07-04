/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.various.mapper;

import com.aquino.various.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @author b005
 */
@Mapper
public interface UserMapper {
    
    User selectUserByName(String name);
    void insert(User user);
    void deleteUserByName(String name);
    
}
