package code.mentor.dto;


import code.mentor.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private String title;
    private String content;
    private String link;
    private String pubDate;
    private Category category; // Đảm bảo có trường category

    // Getters và Setters
}
