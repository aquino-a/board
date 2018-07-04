/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.various.data;

import com.aquino.various.model.Post;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author b005
 */
public class LRUCache<E> {
    
     private Node head;
    private Node tail;
    private Map<E, Node> map;
    private int size;

    public LRUCache(int size) {
        map = new HashMap<>();
        this.size = size;
        head = new Node();
        tail = head.next = new Node();
        tail.previous = head;
    }

    public LRUCache() {
        this(20);
    }
    
    public List<E> getCache() {
        List<E> list = new ArrayList<>();
        for(Node node = head.next; node != null; node = node.next) {
            list.add(node.data);
        }
        return list;
    }
    
    public E add(E data) {
        Node node = new Node();
        node.data = data;
        putOnTop(node);
        map.put(data, node);
        if(map.size() >= 20)
            removeLast();
        return data;
    }
    
    public E get(E data) {
        Node node = remove(map.get(data));
        putOnTop(node);
        return node.data;
    }

    private void putOnTop(Node node) {
        node.previous = head;
        node.next = head.next;
        head.next = node;
        node.next.previous = node;
    }
    
    private void removeLast() {
        Node node = tail.previous;
        remove(node);
        map.remove(node.data);
    }

    private Node remove(Node node) {
        node.previous.next = node.next;
        node.next.previous = node.previous;
       
        return node;
    }

    private class Node {

        Node previous;
        Node next;

        long key;
        E data;

    }

    
}
