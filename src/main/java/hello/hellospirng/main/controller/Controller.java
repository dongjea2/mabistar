package hello.hellospirng.main.controller;

import hello.hellospirng.main.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class Controller {
    @GetMapping("/index")
    public Object test(){
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
}
