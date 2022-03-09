package hello.hellospirng.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginDTO {
    @NotNull
    private String email;

    @NotNull
    private String password;
}
