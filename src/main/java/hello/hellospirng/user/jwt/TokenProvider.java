package hello.hellospirng.user.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Slf4j
@Component
public class TokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY="auth";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token-validity-in-seconds}")
    private long tokenValidityInMillseconds;

    private Key key;


    @Override
    public void afterPropertiesSet() {
        log.info("[afterPropertiesSet]");
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication){
        //권한생성
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //만료시간 설정
        long now = (new Date()).getTime();
        Date validity = new Date( now + this.tokenValidityInMillseconds);

        //대칭키로 암호화 하기
        log.info("[createToken]");
//        String  token = Jwts.builder()
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
//        try {
//            return TokenIncripter.encAES(token);
//        }catch (Exception e){
//            return null;
//        }
    }

    public Authentication getAuthentication(String token){
        log.info("[getAuthentication]");
        //1.토큰을 이용해 클레임 만들기
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();


        //2.클레임에서 권한 정보 빼기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        //3. 권한정보로 시큐어 유저객체 만들기
        User principal = new User(claims.getSubject(), "",authorities);
        return new UsernamePasswordAuthenticationToken(principal,token,authorities);
    }

    public boolean validateToken(String token){
        log.info("[validateToken]");
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch ( SecurityException | MalformedJwtException e){
            log.warn("잘못된 서명입니다.");
        }catch (ExpiredJwtException e){
            log.warn("만료된 토큰입니다.");
        }catch (UnsupportedJwtException e){
            //todo:토큰위조 블럭하기
            log.warn("지원되지 않는 토큰입니다.");
        }catch (IllegalArgumentException e){
            log.warn("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
