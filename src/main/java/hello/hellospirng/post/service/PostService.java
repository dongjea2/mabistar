package hello.hellospirng.post.service;

import hello.hellospirng.post.dto.FileDTO;
import hello.hellospirng.post.dto.PostAddDTO;
import hello.hellospirng.post.dto.PostDTO;
import hello.hellospirng.post.entity.File;
import hello.hellospirng.post.entity.Post;
import hello.hellospirng.post.repository.PostRepository;
import hello.hellospirng.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PostService {

    private final S3Uploader s3Uploader;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PostService(S3Uploader s3Uploader, PostRepository postRepository, UserRepository userRepository , ModelMapper modelMapper) {
        this.s3Uploader = s3Uploader;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper =  modelMapper;

    }

    public List<PostDTO> findByAllPost(){
        List<PostDTO> returnPostDTO= new ArrayList<>();

        for ( Post p : postRepository.findAll()){
            PostDTO postDTO= modelMapper.map(p, PostDTO.class);
            Set<FileDTO> files = new HashSet<>();

            for(File f : p.getImgUrl()){
                FileDTO fileDTO = new FileDTO();
                fileDTO.setName(f.getName());
                files.add(fileDTO);
            }
            postDTO.setImgUrl(files);
            returnPostDTO.add(postDTO);
        }

        return returnPostDTO;
    }

    public boolean addPost(PostAddDTO postDTO){
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

        try {
            for( File f : post.getImgUrl()){
                s3Uploader.delete(f.getS3Key());
            }
            postRepository.delete(post);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
