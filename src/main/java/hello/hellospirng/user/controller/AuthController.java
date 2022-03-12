package hello.hellospirng.user.controller;

import hello.hellospirng.user.dto.LoginDTO;
import hello.hellospirng.user.dto.TokenDTO;
import hello.hellospirng.user.jwt.JwtFilter;
import hello.hellospirng.user.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class AuthController {
    final TokenProvider tokenProvider;
    final AuthenticationManagerBuilder managerBuilder;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder managerBuilder) {
        this.tokenProvider = tokenProvider;
        this.managerBuilder = managerBuilder;
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> auth(@Valid @RequestBody LoginDTO loginDTO){
        log.info("Request (/auth) Post");
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword());

        Authentication authentication = managerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders =new HttpHeaders();
        httpHeaders.add(JwtFilter.authHeader, "Bearer "+ jwt);
        log.info("토큰 발행, 해더 추가 성공");
        return new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK);
    }
}
