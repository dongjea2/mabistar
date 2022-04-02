package hello.hellospirng.post.controller;

import hello.hellospirng.post.dto.PostAddDTO;
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

    @PostMapping("/mainpage")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Object post(){
        return postService.findByAllPost();
    }

    @PostMapping("/post")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> upload(@ModelAttribute PostAddDTO post) {
        if(postService.addPost(post)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/post")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    //ToDo: Warning:(48, 72) Cannot resolve path variable 'post' in request mapping 해결하기
    public ResponseEntity<String> deletePost( @PathVariable PostAddDTO post){
        if (postService.deletePost(post.getId())){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
