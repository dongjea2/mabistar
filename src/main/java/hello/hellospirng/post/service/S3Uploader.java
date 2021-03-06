package hello.hellospirng.post.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public List<String> upload(MultipartFile[] files) throws IOException {
        List<String> returnFilesName = new ArrayList<>();

        for(MultipartFile file : files) {
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            metadata.setContentLength(bytes.length);
            metadata.setContentType("image/png"); //지정안하면 이미지 클릭시 자동으로 다운로드됨

            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            amazonS3Client.putObject(new PutObjectRequest(bucket, dir + fileName, byteStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            returnFilesName.add(amazonS3Client.getUrl(bucket,dir+fileName)+"");
        }
        return returnFilesName;
    }

    public boolean delete(String key){
        try {
            DeleteObjectRequest deleteObj = new DeleteObjectRequest( bucket , key);
            amazonS3Client.deleteObject(deleteObj);
            log.info(String.format("[%s] 삭제 요청 완료", key));
            return true;

        }catch (Exception e ){
            log.warn(String.format("[%s] 삭제 요청 실패", key));
            return false;
        }
    }
}
