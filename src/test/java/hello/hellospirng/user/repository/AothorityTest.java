package hello.hellospirng.user.repository;

import hello.hellospirng.user.entity.Authority;
import hello.hellospirng.user.entity.User;
import hello.hellospirng.user.enums.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AothorityTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthorityRepository authorityRepository;

    //Equals and HashCode 제대로 동작하는지 확인
    @Test
    public void aothAddTest(){
        Authority auth = new Authority();
        auth.setAuthorityType(RoleType.ROLE_ADMIN);
        System.out.println(auth);

        Authority auth2 = new Authority();
        auth2.setAuthorityType(RoleType.ROLE_USER);
        System.out.println(auth2);

        Authority auth3 = new Authority();
        auth3.setAuthorityType(RoleType.ROLE_ADMIN);
        System.out.println(auth3);
        auth3.setAuthorityType(RoleType.ROLE_USER);
        System.out.println(auth3);

    }

    //해당 해원의 권한 추가 (일반유저 , 어드민)
    @Test
    public void authorityAddTest(){
        User u = userRepository.findOneWithAuthoritiesByEmail("dewawd@naver.com").get();

        u.addAuthority(RoleType.ROLE_ADMIN);
        u.addAuthority(RoleType.ROLE_USER);
//        u.removeAuthority(RoleType.ROLE_ADMIN);
//        u.removeAuthority(RoleType.ROLE_USER);
        System.out.println(u.getAuthorities());

        userRepository.save(u);
    }
}
