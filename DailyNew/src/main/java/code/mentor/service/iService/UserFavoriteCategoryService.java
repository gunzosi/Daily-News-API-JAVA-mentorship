package code.mentor.service.iService;

import code.mentor.models.UserFavoriteCategory;
import code.mentor.payload.request.FavoriteCategoryRequest;

import java.util.List;

public interface UserFavoriteCategoryService {
    void addFavoriteCategories(Long userId, FavoriteCategoryRequest request);
    List<UserFavoriteCategory> getFavoriteCategoriesByUserId(Long userId);

}
