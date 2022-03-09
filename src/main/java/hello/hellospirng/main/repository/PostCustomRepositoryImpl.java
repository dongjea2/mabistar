package hello.hellospirng.main.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.hellospirng.main.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

import static hello.hellospirng.main.entity.QPost.post;

@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository{

    private JPAQueryFactory jpaQueryFactory;

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