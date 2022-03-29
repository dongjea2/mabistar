package hello.hellospirng.post.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class S3UploaderTest {

    @Autowired
    S3Uploader s3Uploader;

    @Test
    public void s3DeleteTest(){
        String fileName= "dtest.jpg";
        s3Uploader.delete(fileName);
    }
}
