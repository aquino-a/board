/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.various.repositories;

import com.aquino.various.model.Member;
import com.aquino.various.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author b005
 */
public interface PostRepository extends PagingAndSortingRepository<Post, Long>{
    
    Post findPostByText(String text);
    Page<Post> findAllByOrderByPostDateDesc(Pageable page);
    Page<Post> findPostByMemberOrderByPostDateDesc(Member member, Pageable page);
}
