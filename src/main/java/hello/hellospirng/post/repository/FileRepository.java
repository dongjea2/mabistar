package hello.hellospirng.post.repository;

import hello.hellospirng.post.entity.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {
}
