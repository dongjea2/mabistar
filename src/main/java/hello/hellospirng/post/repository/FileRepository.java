package hello.hellospirng.post.repository;

import hello.hellospirng.post.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
