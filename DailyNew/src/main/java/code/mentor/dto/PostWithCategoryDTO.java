package code.mentor.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PostWithCategoryDTO {
    private Integer id;
    private String title;
    private String content;
    private String link;
    private Instant pubDate;
    private Instant updatedAt;

    // Category information
    private Integer categoryId;
    private String categoryName;


}
