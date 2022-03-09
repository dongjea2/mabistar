package hello.hellospirng.user.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtFilter extends GenericFilterBean {
    //@Value("${jwt.header}")
    public static final String authHeader = "Authorization";
    private TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    //JWT토큰의 인증정보를 Security Context 에 저장
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        log.info("[doFilter]");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                    //리졸브 사용
        String jwt = resolveToken(httpServletRequest);

        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) ){
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Context에 {} 인증정보를 저장했습니다. {}", authentication.getName() ,httpServletRequest.getRequestURI());
        }else{
            log.info("유효한 JWT 토큰이 없습니다, uri: "+ httpServletRequest.getRequestURI());
        }

        chain.doFilter(servletRequest, servletResponse);

    }

    //Request 헤더에서 토큰정보를 꺼내오기위한 resolveToken
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(authHeader);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            log.info("[resolveToken] success");
            return bearerToken.substring(7);
        }
        log.warn("[resolveToken] failed");
        return null;
    }

}
