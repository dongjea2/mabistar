package hello.hellospirng.post.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    public S3Uploader(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Value("${s3.bucket}")
    private String bucket;  // S3 버킷 이름

    @Value("${s3.dir}")
    private String dir;  // S3 버킷 이름

    public String  upload( MultipartFile file) throws IOException {
        String fileName= UUID.randomUUID()+"-"+file.getOriginalFilename();
        ObjectMetadata metadata= new ObjectMetadata();
         byte[] bytes = IOUtils.toByteArray(file.getInputStream());
         metadata.setContentLength(bytes.length);
         //지정안하면 이미지 다운로드됨
         metadata.setContentType("image/jpeg");

        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        amazonS3Client.putObject(new PutObjectRequest(bucket, dir +fileName, byteStream,metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket,dir+fileName).toString();
    }
}
