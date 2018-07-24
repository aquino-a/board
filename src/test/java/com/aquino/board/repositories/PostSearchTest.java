/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.board.repositories;

import com.aquino.board.model.Post;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author b005
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class PostSearchTest {
    
    @Autowired
    PostRepository postRepository;
    
    @Test
    public void search() {
        
        
        Post post = new Post();
        post.setText("");
        post = postRepository.save(post);
        Assertions.assertThat(post.getId() == postRepository.findById(post.getId()).get().getId()).isTrue();
        
        
        Post reply = new Post();
        reply.setParentPost(reply);
        reply.setText("hahaha");
        reply = postRepository.save(reply);
        
        List<Post> replies = new ArrayList<>();
        replies.add(reply);
        post.setReplies(replies);
        postRepository.save(post);
        
        
        post = new Post();
        post.setText("ha");
        postRepository.save(post);
        
        post = new Post();
        post.setText("haha");
        postRepository.save(post);
        
        Assertions.assertThat(postRepository.findAllByText("ha", null).getTotalElements() == 3).isTrue();
        
    }
    
    
}
