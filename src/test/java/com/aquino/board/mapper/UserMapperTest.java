/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.board.mapper;

import com.aquino.board.mapper.UserMapper;
import com.aquino.board.model.User;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.mock.SerializableMode.NONE;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
/**
 *
 * @author b005
 */
@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {
    
    @Autowired
    UserMapper userMapper;
    
    @Test
    public void selectUserByNameTest() {
        User user = userMapper.selectUserByName("alex");
        assertThat(user.getName()).isEqualTo("alex");
        
        
    }
    
    @Test
    public void insertUser() {
        userMapper.insert(new User("john"));
        User user = userMapper.selectUserByName("john");
        assertThat(user.getName()).isEqualTo("john");
    }
    
    @Test
    public void deleteUser() {
        userMapper.insert(new User("billy"));
        userMapper.deleteUserByName("billy");
        User user = userMapper.selectUserByName("billy");
        assertThat(user).isNull();
    }
    
    
    
    
}
