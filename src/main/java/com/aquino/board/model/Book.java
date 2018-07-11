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
public class Book extends Product {
    
    private int pages;
    private String author;

    public Book(long code, String name, double price) {
        super(code, name, price);
    }
    
    public Book(long code, String name, double price, int pages, String author) {
        super(code, name, price);
        this.pages = pages;
        this.author = author;
    }

    
    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    
    
}
