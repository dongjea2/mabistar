package hello.hellospirng.post.entity;

import hello.hellospirng.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "blocked_post")
    private boolean blockedPost;

    @Column(name = "post_content")
    private String postContent;

    @ManyToOne
    @JoinColumn(name = "post_writer")
    private User postWriter;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "post_files",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<File> imgUrl;

    @OneToOne
    @JoinColumn
    private File thumbnail;

    public Post() {
        imgUrl = new HashSet<>();
    }


    public void addImageFile(File file){
        imgUrl.add(file);
    }

    public void removeImageFile(File file){
        imgUrl.remove(file);
    }
}