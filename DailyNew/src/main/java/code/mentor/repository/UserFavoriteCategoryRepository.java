package code.mentor.repository;

import code.mentor.models.UserFavoriteCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFavoriteCategoryRepository extends JpaRepository<UserFavoriteCategory, Long> {
    List<UserFavoriteCategory> findByUserId(Long userId);
}
