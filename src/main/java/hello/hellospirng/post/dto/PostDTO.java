package hello.hellospirng.post.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class PostDTO {
    private MultipartFile[] postImages;
    private String postContent;
}
