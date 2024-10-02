package code.mentor.service;

import code.mentor.models.Category;
import code.mentor.models.Post;
import code.mentor.repository.CategoryRepository;
import code.mentor.repository.PostRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.Instant;
import java.util.List;


@Service
public class RssFeedService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void fetchAndSaveRssFeed(String rssUrl, String categoryName) {
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            // CATEGORY EXIST
            Category category = categoryRepository.findByName(categoryName);
            if (category == null) {
                System.out.println("Category not found, creating new category");
                return;
            }

            List<SyndEntry> entries = feed.getEntries();
            for (SyndEntry entry : entries) {
                Post post = new Post();
                post.setTitle(entry.getTitle());
                post.setContent(entry.getDescription().getValue());
                post.setLink(entry.getLink());
                post.setPubDate(Instant.now());
                post.setCategory(category);

                postRepository.save(post);
            }

            System.out.println("Đã lưu thành công các bài viết từ RSS!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
