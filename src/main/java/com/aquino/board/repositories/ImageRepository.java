/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.board.repositories;

import com.aquino.board.model.Image;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author b005
 */
public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {
    
}
