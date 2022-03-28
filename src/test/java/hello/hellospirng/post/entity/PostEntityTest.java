package hello.hellospirng.post.entity;

import hello.hellospirng.post.repository.FileRepository;
import hello.hellospirng.post.repository.PostRepository;
import hello.hellospirng.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class PostEntityTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    FileRepository fileRepository;

    //1. 포스트 넣어지는지
    @Test
    public void postAddTest(){

        File f = new File();
        f.setName("안녕");
        f.setCreatedAt(new Date());
        f.setType("jpg");
        f.setUploader_id(userRepository.getById(1l));

        Post p = new Post();
        p.setPostContent("안녕");
        System.out.println(f.getName());
        p.addImageFile(f);

        postRepository.save(p);
    }


    //2.포스트 삭제해도 , 파일은 남아 있어야함
    @Test
    public void postRemoveTest(){
        Post p =  postRepository.findById(4l).get();
        postRepository.delete(p);
    }

    //3.게시물은 연결된 상태에서, ㅇ사진을 삭제하면 실패해야함
    @Test
    public void fileRemoveTest(){
        File f = fileRepository.findById(2l).get();

        fileRepository.delete(f);
    }
}
