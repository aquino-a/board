/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.board.post;

import com.aquino.board.controller.PostController;
import com.aquino.board.repositories.ImageRepository;
import com.aquino.board.repositories.MemberService;
import com.aquino.board.repositories.PostRepository;
import com.aquino.board.service.PostService;
import org.assertj.core.api.Fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.util.NestedServletException;
/**
 *
 * @author b005
 */
@RunWith(SpringRunner.class)
@EnableSpringDataWebSupport
@WebMvcTest(value = PostController.class, secure = false)
public class NewPostTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    private PostRepository postRepository;
    
    @MockBean
    ImageRepository imageRepository;
    @MockBean
    PostService postService;
    @MockBean
    MemberService memberService;
    
    
//    @Test
//    public void filesTest() throws Exception {
//        
//        MockMultipartFile file2 = new MockMultipartFile("files", "orig", null, "asdasddd".getBytes());
//         MockMultipartFile file1 = new MockMultipartFile("files", "orig1", null, "weqweq".getBytes());
//        mockMvc.perform(MockMvcRequestBuilders.multipart("/files")
//                .file(file1).file(file2)
//                .param("name", "john")).andExpect(status().is(200));4
//        
//    }
    
    @Test(expected = NestedServletException.class)
    public void newPost() throws Exception {
        
        MockMultipartFile file2 = new MockMultipartFile("files", "orig.txt", null, "hey".getBytes());
        MockMultipartFile file1 = new MockMultipartFile("files", "orig1.txt", null, "baby".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/posts/new")
                .file(file1).file(file2)
                .param("text", "Baby, Baby, Baby"))
                .andExpect(status().is(200));
        
    }
    
    @Test
    public void getPosts() throws Exception {
        String posts = mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0&limit=2"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        
        
        String count = mockMvc.perform(MockMvcRequestBuilders.get("/posts/count"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        try {
            Long.parseLong(count);
        } catch (NumberFormatException e) {
            Fail.fail("Didnt get a long");
        }
        
        
        
    }
    
    
}
