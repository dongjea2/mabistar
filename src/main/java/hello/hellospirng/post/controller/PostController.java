package hello.hellospirng.post.controller;

import hello.hellospirng.post.dto.PostDTO;
import hello.hellospirng.post.entity.Post;
import hello.hellospirng.post.service.S3Uploader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class PostController {
    private final S3Uploader s3Uploader;

    public PostController(S3Uploader s3Uploader) {
        this.s3Uploader = s3Uploader;
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
    public Object post(@RequestBody Post post){
        log.info(post.getPostContent());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/images")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String upload(@ModelAttribute PostDTO post) throws  IOException {
        log.info(post.getPostContent());
        return s3Uploader.upload(post.getPostImages());
    }


}
