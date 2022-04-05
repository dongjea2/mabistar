package hello.hellospirng.post.entity;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "post_files",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<File> imgUrl;

    public Post() {
        imgUrl = new HashSet<>();
    }

    @Column(name = "post_content")
    private String postContent;

    public void addImageFile(File file){
        imgUrl.add(file);
    }

    public void removeImageFile(File file){
        imgUrl.remove(file);
    }
}