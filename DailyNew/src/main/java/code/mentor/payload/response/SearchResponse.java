package code.mentor.payload.response;

import code.mentor.models.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchResponse {
    private List<Post> posts;
    private int totalCount;

    // Constructor, getters and setters


    public SearchResponse(List<Post> posts, int totalCount) {
        this.posts = posts;
        this.totalCount = totalCount;
    }
}
