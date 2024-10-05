package code.mentor.service;

import code.mentor.models.Category;
import code.mentor.models.Post;
import code.mentor.repository.CategoryRepository;
import code.mentor.repository.PostRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class RssFeedService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public RssFeedService(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Scheduled(fixedRate = 120000)  // Mỗi 2 phút (120000 ms)
    public void scheduledRssFeedUpdate() {
        // Ví dụ với URL và categoryName cố định, có thể lấy từ config
        String rssUrl = "https://vnexpress.net/rss/khoa-hoc.rss";
        String categoryName = "Technology";

        // Gọi hàm fetchAndSaveRssFeed để xử lý
        fetchAndSaveRssFeed(rssUrl, categoryName);
    }

    public void fetchAndSaveRssFeed(String rssUrl, String categoryName) {
        try {
            // Lấy feed từ RSS URL
            SyndFeed feed = getFeedFromUrl(rssUrl);

            // Tìm category, nếu không có thì dừng lại
            Category category = categoryRepository.findByName(categoryName);
            if (category == null) {
                System.out.println("Category không tồn tại, vui lòng tạo category: " + categoryName);
                return;
            }

            // Xử lý danh sách bài viết từ RSS feed
            processFeedEntries(feed.getEntries(), category);

            System.out.println("Đã lưu thành công các bài viết từ RSS tại link: " + rssUrl);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi lưu bài viết từ RSS tại link: " + rssUrl);
        }
    }

    // Phương thức lấy RSS feed từ URL
    private SyndFeed getFeedFromUrl(String rssUrl) {
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            return input.build(new XmlReader(url));
        } catch (FeedException | IOException e) {
            throw new RuntimeException("Lỗi khi đọc RSS feed từ URL: " + rssUrl, e);
        }
    }

    // Phương thức xử lý các bài viết trong RSS feed
    private void processFeedEntries(List<SyndEntry> entries, Category category) {
        for (SyndEntry entry : entries) {
            Optional<Post> existingPost = postRepository.findByLink(entry.getLink());

            if (existingPost.isPresent()) {
                // Cập nhật bài viết nếu đã tồn tại
                updateExistingPost(existingPost.get(), entry);
            } else {
                // Tạo mới bài viết nếu chưa tồn tại
                createNewPost(entry, category);
            }
        }
    }

    // Phương thức cập nhật bài viết đã tồn tại
    private void updateExistingPost(Post post, SyndEntry entry) {
        post.setTitle(entry.getTitle());
        post.setContent(entry.getDescription().getValue());
        post.setUpdatedAt(Instant.now());
        postRepository.save(post);
        System.out.println("Post updated: " + entry.getTitle());
    }

    // Phương thức tạo bài viết mới
    private void createNewPost(SyndEntry entry, Category category) {
        Post post = new Post();
        post.setTitle(entry.getTitle());
        post.setContent(entry.getDescription().getValue());
        post.setLink(entry.getLink());
        post.setPubDate(Instant.now());
        post.setCategory(category);
        postRepository.save(post);
        System.out.println("Post inserted: " + entry.getTitle());
    }
}
