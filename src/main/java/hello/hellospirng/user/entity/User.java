package hello.hellospirng.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.regex.Pattern;

@Getter
@Setter
@Entity
public class User {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String nickName;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")}

    )
    private Set<Authority> authorities;


    public boolean isUserAllRegexCheckPass(){
        if(email == null && nickName == null){
            return false;
        }

        //1.check Email
        String emailRegex = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        if( !Pattern.matches(emailRegex,email)){
            return false;
        }

        //2.check nickName
        String nickRexgex= "^[ㄱ-ㅎ|가-힣|a-z|A-Z|]{2,8}$";
        if(!Pattern.matches(nickRexgex,nickName)){
            return false;
        }

        return true;
    }
}
