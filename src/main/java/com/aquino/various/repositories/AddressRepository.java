/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.various.repositories;

import com.aquino.various.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author b005
 */
@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
    
    
}
