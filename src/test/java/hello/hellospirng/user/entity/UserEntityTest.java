package hello.hellospirng.user.entity;

import hello.hellospirng.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class UserEntityTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void userRegexTest(){
        User u = new User();
        //1.
        u.setEmail("dewawd@gmail.com");
        u.setNickName("동재");
        assertEquals(true, u.isUserAllRegexCheckPass());
        //2.
        u.setEmail("dewawd@gmail.fd..fd");
        u.setNickName("동재");
        assertEquals(false, u.isUserAllRegexCheckPass());
        //3.
        u.setEmail("dewawdgmail.com");
        u.setNickName("동재");
        assertEquals(false, u.isUserAllRegexCheckPass());
        //4.
        u.setEmail("dewawd@gmail.com");
        u.setNickName("동재3241");
        assertEquals(false, u.isUserAllRegexCheckPass());
        u.setEmail("dewawd@gmail.com");
        u.setNickName("  동재");
        assertEquals(false, u.isUserAllRegexCheckPass());
    }


    //유저 삭제 테스트
    @Test
    public void removeUserTest(){
        Optional<User> u  = userRepository.findById((long)2002);
        User user = u.get();

        userRepository.delete(user);
    }

    @Test
    public void repoTest(){
        Optional<User> u = userRepository.findOneWithAuthoritiesByEmail("dewawd@naver.com");
        User user = u.get();
        System.out.printf("이름"+user.getNickName());

    }
}
