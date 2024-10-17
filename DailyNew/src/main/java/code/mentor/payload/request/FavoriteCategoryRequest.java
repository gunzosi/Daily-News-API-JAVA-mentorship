package code.mentor.payload.request;

import code.mentor.dto.CategoryIdDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FavoriteCategoryRequest {
    private List<CategoryIdDTO> categories;
}

