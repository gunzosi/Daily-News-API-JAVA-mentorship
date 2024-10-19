package code.mentor.repository;

import code.mentor.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findByLink(String link);
    List<Post> findByTitleContaining(String name);
    Page<Post> findAll(Pageable pageable);
    List<Post> findByCategoryIdIn(List<Integer> categoryIds);
}

