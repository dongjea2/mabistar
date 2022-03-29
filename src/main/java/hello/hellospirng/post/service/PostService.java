package hello.hellospirng.post.service;

import hello.hellospirng.post.dto.PostDTO;
import hello.hellospirng.post.entity.File;
import hello.hellospirng.post.entity.Post;
import hello.hellospirng.post.repository.PostRepository;
import hello.hellospirng.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PostService {

    private final S3Uploader s3Uploader;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(S3Uploader s3Uploader, PostRepository postRepository, UserRepository userRepository) {
        this.s3Uploader = s3Uploader;
        this.postRepository = postRepository;
        this.userRepository = userRepository;

    }

    public boolean addPost(PostDTO postDTO){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails ud = (UserDetails)principal;
        String userEmail = ud.getUsername();
        log.info(ud.getUsername());

        try {
            List<String> files = s3Uploader.upload(postDTO.getPostImages());

            Post p = new Post();
            p.setPostContent(postDTO.getPostContent());

            for(String file : files){
                File f = new File();
                f.setUploader_id(userRepository.findByEmail(userEmail).get());
                f.setCreatedAt(new Date());
                f.setName(file);
                f.setType("image");

                p.addImageFile(f);
            }
            postRepository.save(p);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean deletePost(long id){
        Optional<Post> opt = postRepository.findById(id);
        if(opt.isEmpty()){return false;}
        Post post = opt.get();

        for( File url : post.getImgUrl()){
            s3Uploader.delete(url.getName());
        }

        postRepository.delete(post);
        return true;
    }
}
