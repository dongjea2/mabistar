package hello.hellospirng.user.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
public class SecurityUtil {
    public static Optional<String> getCurrentUserEmail(){
        //SecurityContext에서 Authentication 객체를 이용해 이메일을 얻어옴
        //authentication 이 SC에 저장되는 시점은 doFilter에 리퀘스트 들어올떄 저장됨
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            log.info("SecurityContext 에 인증정보가 없습니다.");
            return Optional.empty();
        }

        String username = null;
        if(authentication.getPrincipal() instanceof UserDetails ){
            UserDetails securityUser = (UserDetails) authentication.getPrincipal();
            username = securityUser.getUsername();
        }
        if(authentication.getPrincipal() instanceof String){
            username = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(username);
    }
}
