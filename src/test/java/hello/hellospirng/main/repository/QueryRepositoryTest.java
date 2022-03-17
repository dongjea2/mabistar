package hello.hellospirng.main.repository;

import hello.hellospirng.post.entity.Post;
import hello.hellospirng.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class QueryRepositoryTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PostRepository postCustomRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    public void postSave(){
        Post p = new Post();
        p.setPostContent("안녕하세요");
        postRepository.save(p);
    }

    @Test
    public void postFindAll(){
        List<Post> postList =postRepository.findAll();

        for(Post p : postList){
            logger.info("[Post ID]"+p.getId());
            logger.info("[Post Content]"+p.getPostContent());
        }
    }
}
