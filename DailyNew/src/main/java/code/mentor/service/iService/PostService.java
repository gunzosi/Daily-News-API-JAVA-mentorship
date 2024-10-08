package code.mentor.service.iService;

import code.mentor.models.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    Post getPostById(int id);

    Post savePost(Post post);

    Post updatePost(Post post);

    void deletePostById(int id);
}
