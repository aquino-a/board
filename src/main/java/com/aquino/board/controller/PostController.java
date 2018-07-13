/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.board.controller;

import com.aquino.board.model.Image;
import com.aquino.board.model.Member;
import com.aquino.board.model.Post;
import com.aquino.board.repositories.ImageRepository;
import com.aquino.board.repositories.MemberService;
import com.aquino.board.repositories.PostRepository;
import com.aquino.board.service.PostService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author b005
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired ImageRepository imageRepository;
    @Autowired PostRepository postRepository;
    @Autowired PostService postService;
    @Autowired MemberService memberService;

    @Value("${user.save-location-path}")
    private String saveLocationPath;
    
    @GetMapping()
    public ResponseEntity<Page<Post>> getPosts(Pageable page) {
        return ResponseEntity.ok(
                postRepository.findAllByParentPostIsNullOrderByLastAccessDateDescPostDateDesc(page));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(postService.countPosts());
    }
    
    @GetMapping("/user/{username}")
    public ResponseEntity<Page<Post>> getUserPosts(
            @PathVariable String username, Pageable page) {
        Member member = memberService.findByUsername(username);
        return ResponseEntity.ok(
                postRepository
                        .findPostByMemberAndParentPostIsNullOrderByLastAccessDateDescPostDateDesc(member, page));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(
            @PathVariable long id) {
        Optional<Post> postOption = postRepository.findById(id);
        if(postOption.isPresent())
            return ResponseEntity.ok(postOption.get());
        else return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deletePost(
            @PathVariable long id,@AuthenticationPrincipal Member member) {
        Optional<Post> postOption = postRepository.findById(id);
        Post post;
        if(postOption.isPresent()) {
            post = postOption.get();
            postService.deletePost(post);
             return ResponseEntity.ok(id);
        }
        return ResponseEntity.status( HttpStatus.NOT_FOUND).body(id);
    }
    
    @PostMapping("/new")
    public ResponseEntity<Post> newPost(
            @AuthenticationPrincipal Member member,
            Post postInput, @RequestParam List<MultipartFile> files,
            @RequestParam(required = false, defaultValue = "-1") long parentId) {
        Post post = postRepository.save(postInput);
        post.setMember(member);
        post.setPostDate(LocalDateTime.now());

        List<Image> images = new ArrayList<>();
        String path = makePath(member.getUsername());
        File directory = (new File(path));
        if (!directory.exists()) {
            directory.mkdirs();
        }
        files.forEach((file) -> {
            try {
                file.transferTo(new File(path + file.getOriginalFilename()));
                Image image = new Image(file.getOriginalFilename());
                image.setPost(post);
                images.add(imageRepository.save(image));
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        post.setImages(images);

        if (parentId != -1) {
            Optional<Post> parentOption = postRepository.findById(parentId);
            Post parent = parentOption.get();
            if (parent != null) {
                List<Post> replies = parent.getReplies();
                if (replies == null) {
                    replies = new ArrayList<>();
                }
                replies.add(post);
                parent.setLastAccessDate(LocalDateTime.now());
                postRepository.save(parent);
            }
            post.setParentPost(post);
        }
        postRepository.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
    
    @GetMapping("{username}/images/{fileName}")
    public ResponseEntity<byte[]> findFile(@PathVariable String fileName,
            @PathVariable String username) throws FileNotFoundException {
        String path = makePath(username) + fileName;
        try {
            return ResponseEntity.ok(Files.readAllBytes(Paths.get(path)));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/me") 
    public ResponseEntity<Member> userInfo(@AuthenticationPrincipal Member member){
//        if(member == null)
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(member);
    }

    private String makePath(String username) {
        StringBuilder sb = new StringBuilder(saveLocationPath);
        sb.append("\\");
        sb.append(username);
        sb.append("\\images\\");
        return sb.toString();
    }

}
