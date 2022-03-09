package hello.hellospirng.user.service;

import hello.hellospirng.user.entity.User;
import hello.hellospirng.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    User user;

    @Override
    @Transactional
    //1. 로그인 시에 DB에서 유저정보와 권한을 가져옴
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
                //find DB
        log.info("[loadUserByUsername]  / Find User Email : "+userEmail);
        return userRepository
                    .findOneWithAuthoritiesByEmail(userEmail)
                    .map(user -> createUser(userEmail, user))
                    .orElseThrow(() -> new UsernameNotFoundException(userEmail + "-> is not found from DB"));
    }

    //2. 로그인 한 유저가 활성상태면 닉네임, 비밀번호, 권한정보를 리턴
    private org.springframework.security.core.userdetails.User createUser(String userEmail, User user){
        //TODO: 활성 컬럼 추가해서 활성된 유저 아니면 익셉션 보내기
        log.info("[createUser]");

        List<GrantedAuthority> grantedAuthorities =
                    user
                        .getAuthorities().stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                        .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.
                    User(user.getEmail(),user.getPassword(),grantedAuthorities);
    }

}
