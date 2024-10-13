package code.mentor.rest;


import code.mentor.dto.UserFavoriteCategoryDTO;
import code.mentor.dto.UserWithFavoriteCategoriesDTO;
import code.mentor.models.User;
import code.mentor.models.UserFavoriteCategory;
import code.mentor.payload.request.FavoriteCategoryRequest;
import code.mentor.payload.response.MessageResponse;
import code.mentor.repository.CategoryRepository;
import code.mentor.repository.UserRepository;
import code.mentor.service.iService.UserFavoriteCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/api/users/fave-categories")
public class UserFavoriteCategoryController {

    private final UserFavoriteCategoryService userFavoriteCategoryService;
    private final UserRepository userRepository;

    @Autowired
    public UserFavoriteCategoryController(UserFavoriteCategoryService userFavoriteCategoryService, UserRepository userRepository) {
        this.userFavoriteCategoryService = userFavoriteCategoryService;
        this.userRepository = userRepository;
    }

    @PostMapping("/{userId}/favorite-categories")
    public ResponseEntity<?> addFavoriteCategories(@PathVariable Long userId, @RequestBody FavoriteCategoryRequest request) {
        userFavoriteCategoryService.addFavoriteCategories(userId, request);
        return ResponseEntity.ok(new MessageResponse("Favorite categories are added successfully!"));
    }

    @GetMapping("/{userId}/getUserFavoriteCategories")
    public ResponseEntity<List<UserFavoriteCategoryDTO>> getUserFavoriteCategories(@PathVariable Long userId) {
        List<UserFavoriteCategory> favoriteCategories = userFavoriteCategoryService.getFavoriteCategoriesByUserId(userId);

        List<UserFavoriteCategoryDTO> response = favoriteCategories.stream()
                .map(userFavCat -> new UserFavoriteCategoryDTO(
                        userFavCat.getCategory().getId(),
                        userFavCat.getCategory().getName()
                )).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllInformation")
    public ResponseEntity<List<UserWithFavoriteCategoriesDTO>> getAllInformation() {
        List<User> users = userRepository.findAll();

        List<UserWithFavoriteCategoriesDTO> response = users.stream().map(user -> {
            List<UserFavoriteCategory> favoriteCategories = userFavoriteCategoryService.getFavoriteCategoriesByUserId(user.getId());

            // Chuyển đổi danh mục yêu thích sang DTO
            List<UserFavoriteCategoryDTO> favCategoryDTOs = favoriteCategories.stream()
                    .map(userFavCat -> new UserFavoriteCategoryDTO(userFavCat.getCategory().getId(), userFavCat.getCategory().getName()))
                    .collect(Collectors.toList());

            // Tạo DTO cho người dùng
            return new UserWithFavoriteCategoriesDTO(user, favCategoryDTOs);
        }).toList();

        return ResponseEntity.ok(response);
    }
}