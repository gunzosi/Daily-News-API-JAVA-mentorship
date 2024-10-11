package code.mentor.service.iService;

import code.mentor.dto.SearchCriteria;
import code.mentor.models.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getAllPosts();

    Post getPostById(int id);

    Post savePost(Post post);

    Post updatePost(Post post);

    void deletePostById(int id);

    Optional<List<Post>> searchPostsByBody(SearchCriteria criteria);

    List<Post> searchFuzzyByTitle(String keyword);
}
