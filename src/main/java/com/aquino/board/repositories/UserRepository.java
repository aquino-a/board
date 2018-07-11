/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.board.repositories;

import com.aquino.board.model.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author b005
 */
@Repository
public interface UserRepository extends CrudRepository<Member,Long> {
    
    Member findByUsername(String username);
    
}
