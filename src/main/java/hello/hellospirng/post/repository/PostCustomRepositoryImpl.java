package hello.hellospirng.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.hellospirng.post.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

import static hello.hellospirng.post.entity.QPost.post;

@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository{

    private final JPAQueryFactory  jpaQueryFactory;

    public PostCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Post> findByPostContent(String postContent) {
        return jpaQueryFactory.selectFrom(post)
                .where(post.postContent.contains("야호"))
                .fetch();
    }
}