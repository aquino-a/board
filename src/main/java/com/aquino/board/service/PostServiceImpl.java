/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.board.service;

import com.aquino.board.model.Post;
import com.aquino.board.repositories.PostRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 *
 * @author b005
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    public Post findPostById(long id) {
        Optional<Post> postOption = postRepository.findById(id);
        Post post;
        if (postOption.isPresent()) {
            return postOption.get();
        }
        return null;
    }
    
    public long countPosts() {
        return postRepository.count();
    }

}
