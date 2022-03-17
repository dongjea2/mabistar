package hello.hellospirng.post.repository;

import hello.hellospirng.post.entity.QPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class QueryRepository {
    @Autowired
    EntityManager em;

    private final QPost post = QPost.post;
}
