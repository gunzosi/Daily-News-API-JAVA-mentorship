package code.mentor.dto;

import code.mentor.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserWithFavoriteCategoriesDTO {
    private Long id;
    private String username;
    private String email;
    private List<UserFavoriteCategoryDTO> favCategories;

    public UserWithFavoriteCategoriesDTO(User user, List<UserFavoriteCategoryDTO> favCategories) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.favCategories = favCategories;
    }
}
