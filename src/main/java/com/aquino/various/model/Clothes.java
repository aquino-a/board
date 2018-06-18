/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.various.model;

/**
 *
 * @author b005
 */
public class Clothes extends Product {
    
    private Size size;
    private Color color;

    public Clothes(long code, String name, double price) {
        super(code, name, price);
    }
    public Clothes(long code, String name, double price, Size size, Color color) {
        super(code, name, price);
        this.size = size;
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public enum Color{
        RED,BLUE,BLACK,YELLOW,GREEN
    }
    
    public enum Size {
        S,M,L
    }
    
    
    
}
