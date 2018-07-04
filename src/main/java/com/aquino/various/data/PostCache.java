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
public class PostCache {

    private Node head;
    private Node tail;
    private Map<Long, Node> map;
    private int size;

    public PostCache(int size) {
        map = new HashMap<>();
        this.size = size;
        head = new Node();
        tail = head.next = new Node();
        tail.previous = head;
    }

    public PostCache() {
        this(20);
    }
    
    public List<Post> getCache() {
        List<Post> list = new ArrayList<>();
        for(Node node = head.next; node != null; node = node.next) {
            list.add(node.data);
        }
        return list;
    }
    
    public void addPost(Post post) {
        Node node = new Node();
        node.data = post;
        putOnTop(node);
        map.put(post.getId(), node);
        if(map.size() >= 20)
            removeLast();
    }
    
    public Post getPost(long id) {
        Node node = remove(map.get(id));
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
        map.remove(node.data.getId());
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
        Post data;

    }

}
