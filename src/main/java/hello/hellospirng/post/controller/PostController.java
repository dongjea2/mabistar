package hello.hellospirng.post.controller;

import hello.hellospirng.post.dto.PostDTO;
import hello.hellospirng.post.entity.Post;
import hello.hellospirng.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService= postService;
    }

    @RequestMapping("/test")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Object st(){
        Map<String, String> retrunMap = new HashMap<>();
        retrunMap.put("hi","hello");
        return retrunMap;
    }

    @PostMapping("/post")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Object post(@RequestBody Post post ){
        log.info(post.getPostContent());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/images")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> upload(@ModelAttribute PostDTO post) {
        if(postService.addPost(post)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
