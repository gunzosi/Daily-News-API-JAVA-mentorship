package code.mentor.rest;

import code.mentor.dto.PostDTO;
import code.mentor.dto.PostWithCategoryDTO;
import code.mentor.entities.Post;
import code.mentor.entities.Category;
import code.mentor.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/v1/api/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Lấy tất cả các post
//    @GetMapping
//    public ResponseEntity<List<Post>> getAllPosts() {
//        // List All Posts with ID of Category
//        List<Post> posts = postService.getAllPosts();
//        return ResponseEntity.ok(posts);
//    }

    @GetMapping
    public ResponseEntity<List<PostWithCategoryDTO>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostWithCategoryDTO> postWithCategoryDTOs = posts.stream().map(post -> {
            PostWithCategoryDTO dto = new PostWithCategoryDTO();
            dto.setId(post.getId());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setLink(post.getLink());
            dto.setPubDate(post.getPubDate());
            dto.setUpdatedAt(post.getUpdatedAt());
            Category category = post.getCategory();
            if (category != null) {
                dto.setCategoryId(category.getId());
                dto.setCategoryName(category.getName());
            }
            return dto;
        }).toList();
        return ResponseEntity.ok(postWithCategoryDTOs);
    }

    // Lấy thông tin một post cụ thể cùng với category mà nó thuộc về
    @GetMapping("/{postId}")
    public ResponseEntity<PostWithCategoryDTO> getPostById(@PathVariable int postId) {
        Post post = postService.getPostById(postId);
        Category category = post.getCategory();

        PostWithCategoryDTO postWithCategoryDTO = new PostWithCategoryDTO();
        postWithCategoryDTO.setId(post.getId());
        postWithCategoryDTO.setTitle(post.getTitle());
        postWithCategoryDTO.setContent(post.getContent());
        postWithCategoryDTO.setLink(post.getLink());
        postWithCategoryDTO.setPubDate(post.getPubDate());
        postWithCategoryDTO.setUpdatedAt(post.getUpdatedAt());

        if (category != null) {
            postWithCategoryDTO.setCategoryId(category.getId());
            postWithCategoryDTO.setCategoryName(category.getName());
        }

        return ResponseEntity.ok(postWithCategoryDTO);
    }

    // Thêm mới post
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setLink(postDTO.getLink());
        post.setPubDate(Instant.parse(postDTO.getPubDate())); // Chuyển đổi pubDate từ String sang Instant

        // Kiểm tra Category và thiết lập cho Post
        if (postDTO.getCategory() != null) {
            Category category = new Category();
            category.setId(postDTO.getCategory().getId()); // Chỉ cần ID để ánh xạ tới Category
            post.setCategory(category);
        }

        Post createdPost = postService.savePost(post);
        return ResponseEntity.ok(createdPost);
    }


    // Cập nhật post
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable int postId, @RequestBody Post post) {
        post.setId(postId);
        Post updatedPost = postService.updatePost(post);
        return ResponseEntity.ok(updatedPost);
    }

    // Xóa post
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId) {
        postService.deletePostById(postId);
        return ResponseEntity.ok("Delete POST Successfully");
    }
}
