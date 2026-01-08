package se.jensen.marcel.springboot1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.marcel.springboot1.model.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
}
