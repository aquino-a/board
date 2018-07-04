/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.various.service;

import com.aquino.various.model.Post;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author b005
 */
public interface PostService {

    @PreAuthorize(value = "hasAuthority('ADMIN')"
            + "or authentication.principal.equals(#post.member) ")
    void deletePost(Post post);

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    void deletePostById(long id);
    
    Post findPostById(long id);
    
    long countPosts();
}
