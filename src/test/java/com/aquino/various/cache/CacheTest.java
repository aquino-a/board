/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.various.cache;

import com.aquino.various.data.LRUCache;
import com.aquino.board.model.Post;
import com.aquino.board.repositories.PostRepository;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author b005
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CacheTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void CacheTest() {
        LRUCache<Post> cache = new LRUCache();
        Post post1 = new Post();
        post1.setText("post 1");
        Post post2 = new Post();
        post2.setText("post 2");
        Post post3 = new Post();
        post3.setText("post 3");
        post1 = cache.add(postRepository.save(post1));
        post2 = cache.add(postRepository.save(post2));
        post3 = cache.add(postRepository.save(post3));

        cache.get(post1);

        List<Post> list = cache.getCache();
        assertThat(list.get(0)).isEqualTo(post1);
        assertThat(list.get(1)).isEqualTo(post3);
        assertThat(list.get(2)).isEqualTo(post2);
        assertThat(list.get(0) != list.get(1));
        assertThat(list.get(2).getId() != list.get(1).getId());
    }

}
