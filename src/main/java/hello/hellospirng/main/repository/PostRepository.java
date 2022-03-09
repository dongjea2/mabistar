package hello.hellospirng.main.repository;

import hello.hellospirng.main.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPostContent(String postContent);
}
interface PostCustomRepository {
    List<Post> findByPostContent(String postContent);
}
