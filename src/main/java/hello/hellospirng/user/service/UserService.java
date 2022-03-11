package hello.hellospirng.user.service;

import hello.hellospirng.user.dto.LoginDTO;
import hello.hellospirng.user.dto.UserDTO;
import hello.hellospirng.user.entity.Authority;
import hello.hellospirng.user.entity.User;
import hello.hellospirng.user.repository.UserRepository;
import hello.hellospirng.user.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;


    public Optional<User> getMyUserWithAuthorities(){
        return SecurityUtil.getCurrentUserEmail().flatMap(userRepository::findOneWithAuthoritiesByEmail);
    }

    public Optional<User> getUserWithAuthorities(String userNo){
        Optional<User> u = userRepository.findById(Long.parseLong(userNo));

        if(u.isEmpty()){
            return Optional.empty();
        }
        return userRepository.findOneWithAuthoritiesByEmail(u.get().getEmail());
    }

    public boolean signIn(final LoginDTO loginDto){
        try {
            User loginUser = modelMapper.map(loginDto, User.class );

            //1.Email check
            Optional<User> userFromDB = userRepository.findByEmail(loginUser.getEmail());
            if(userFromDB.isEmpty()){
                throw new Exception();
            }

            //2.PWD check
            if(!passwordEncoder.matches(loginUser.getPassword(), userFromDB.get().getPassword())){
                throw new Exception();
            }

            return true;
        }catch (Exception e){
            return false;
        }

    }


    @Transactional
    public boolean signUp(final UserDTO userDTO){
        try {
            User createdUser = modelMapper.map(userDTO, User.class);


            if(!createdUser.isUserAllRegexCheckPass()){
                log.error("SignUp Failed! RegexCheck ERROR");
                throw new Exception();
            }

            Optional<User> findUserFromDB = userRepository.findByEmail(createdUser.getEmail());
            if(findUserFromDB.isPresent()){
                log.error("SignUp Failed! Email Already Used");
                throw new Exception();
            }

            //1. add Default Authority
            Authority defaultAuth = new Authority();
            defaultAuth.setAuthorityName("ROLE_USER");
            createdUser.setAuthorities(Collections.singleton(defaultAuth));


            //2. password encoding
            String encodePassword = passwordEncoder.encode(createdUser.getPassword());
            createdUser.setPassword(encodePassword);
            log.info("암호화 성공");
            userRepository.save(createdUser);
            log.info(createdUser.getEmail()+"가입 성공");
            return true;

        }catch (Exception e){
            log.warn("가입실패");
            return false;
        }
    }

}
