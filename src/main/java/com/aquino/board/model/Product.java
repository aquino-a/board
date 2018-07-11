/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.board.model;

/**
 *
 * @author b005
 */
public class Product {
    
    private long code;
    private String name;
    private double price;

    public Product(long code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }
    public Product() {}
    
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
    
}
