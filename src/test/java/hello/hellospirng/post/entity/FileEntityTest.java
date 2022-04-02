package hello.hellospirng.post.entity;

import hello.hellospirng.post.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileEntityTest {

    @Autowired
    private FileRepository fileRepository;

    @Test
    public void fileParsingTest(){

        File f =fileRepository.findById(4l).get();
        System.out.println(f.getS3Key());


    }
}
