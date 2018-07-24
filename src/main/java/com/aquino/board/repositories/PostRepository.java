/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.board.repositories;

import com.aquino.board.model.Member;
import com.aquino.board.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author b005
 */
public interface PostRepository extends PagingAndSortingRepository<Post, Long>{
    
    Post findPostByText(String text);
    Page<Post> findAllByParentPostIsNullOrderByLastAccessDateDesc(Pageable page);
    Page<Post> findPostByMemberAndParentPostIsNullOrderByLastAccessDateDesc(Member member, Pageable page);
    
    @Query("select p from Post p left join p.replies q where p.parentPost is null and( lower(p.text) like lower(concat('%', ?1,'%')) or lower(q.text) like lower(concat('%', ?1,'%')) )")
    Page<Post> findAllByText(String text, Pageable page);
}
