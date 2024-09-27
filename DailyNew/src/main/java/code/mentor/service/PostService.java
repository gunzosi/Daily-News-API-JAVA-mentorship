package code.mentor.service;

import code.mentor.entities.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(int id);
    Post savePost(Post post);
    Post updatePost(Post post);
    void deletePostById(int id);
}
