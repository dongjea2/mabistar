package hello.hellospirng.post.entity;

import hello.hellospirng.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "uploader_id", nullable = false)
    private User uploader_id;




}
