package hello.hellospirng.main.repository;

import hello.hellospirng.main.entity.QPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class QueryRepository {
    @Autowired
    EntityManager em;

    private QPost post = QPost.post;
}
