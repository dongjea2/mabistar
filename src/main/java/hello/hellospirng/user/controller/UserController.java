package hello.hellospirng.user.controller;

import hello.hellospirng.user.dto.LoginDTO;
import hello.hellospirng.user.dto.UserDTO;
import hello.hellospirng.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signUp")
    public Object signUp(
            @Valid @RequestBody UserDTO user){
        return ResponseEntity.ok(userService.signUp(user));
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginDTO user){
        if(userService.signIn(user)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Object getMyInfo(){
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().orElse(null));
    }

    @GetMapping("/user/{userNo}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Object getUserInfo(@PathVariable String userNo){
        return ResponseEntity.ok(userService.getUserWithAuthorities(userNo).orElse(null));
    }
}
