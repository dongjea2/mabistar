package hello.hellospirng.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private Set<FileDTO> imgUrl;
    private String postContent;
}
