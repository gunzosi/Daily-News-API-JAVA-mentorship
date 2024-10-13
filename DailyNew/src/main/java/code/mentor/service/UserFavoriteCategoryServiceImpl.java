package code.mentor.service;

import code.mentor.models.Category;
import code.mentor.models.User;
import code.mentor.models.UserFavoriteCategory;
import code.mentor.models.UserFavoriteCategoryId;
import code.mentor.dto.CategoryId;
import code.mentor.payload.request.FavoriteCategoryRequest;
import code.mentor.repository.CategoryRepository;
import code.mentor.repository.UserFavoriteCategoryRepository;
import code.mentor.repository.UserRepository;
import code.mentor.service.iService.UserFavoriteCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFavoriteCategoryServiceImpl implements UserFavoriteCategoryService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserFavoriteCategoryRepository userFavoriteCategoryRepository;

    @Autowired
    public UserFavoriteCategoryServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository,
                                           UserFavoriteCategoryRepository userFavoriteCategoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.userFavoriteCategoryRepository = userFavoriteCategoryRepository;
    }


    @Override
    public void addFavoriteCategories(Long userId, FavoriteCategoryRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User isn't found with ID: " + userId));

        List<UserFavoriteCategory> favoriteCategories = new ArrayList<>();

        for (CategoryId categoryId : request.getCategories()) {
            Category category = categoryRepository.findById(categoryId.getId())
                    .orElseThrow(() -> new RuntimeException("Category isn't found with ID: " + categoryId.getId()));

            UserFavoriteCategoryId id = new UserFavoriteCategoryId();
            id.setUserId(user.getId());
            id.setCategoryId(category.getId());

            UserFavoriteCategory favoriteCategory = new UserFavoriteCategory();
            favoriteCategory.setId(id);
            favoriteCategory.setUser(user);
            favoriteCategory.setCategory(category);

            favoriteCategories.add(favoriteCategory);
        }

        userFavoriteCategoryRepository.saveAll(favoriteCategories);
    }

    @Override
    public List<UserFavoriteCategory> getFavoriteCategoriesByUserId(Long userId) {
        return userFavoriteCategoryRepository.findByUserId(userId);
    }

}
