package code.mentor.service;

import code.mentor.models.Category;
import code.mentor.models.Post;
import code.mentor.models.Resource;
import code.mentor.models.RssLink;
import code.mentor.repository.CategoryRepository;
import code.mentor.repository.PostRepository;
import code.mentor.repository.RssLinkRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class RssFeedServiceImpl {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final RssLinkRepository rssLinkRepository;

    @Autowired
    public RssFeedServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, RssLinkRepository rssLinkRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.rssLinkRepository = rssLinkRepository;
    }

    // @Scheduled(fixedRate = 600000)  // Every 10 minutes = 600000 ms
    public void scheduledRssFeedUpdate() {
        List<RssLink> rssLinks = rssLinkRepository.findAll();
        System.out.println("\n\n*** ------------------------------------------------------------------------------- Start fetching RSS feeds...");

        // Iterate over each RSS link and fetch posts
        rssLinks.forEach(rssLink -> {
            fetchAndSaveRssFeed(rssLink.getUrl(), rssLink.getCategory().getName());
            System.out.printf("SAVED ALL posts from \"RSS-Link\": \"%s\" in CATEGORY: \"%s\" of RESOURCE: \"%s\"%n",
                    rssLink.getUrl(), rssLink.getCategory().getName(), rssLink.getResource().getName());
        });

        System.out.println("*** ------------------------------------------------------------------------------- End fetching RSS feeds...\n\n");
    }

    public void fetchAndSaveRssFeed(String rssUrl, String categoryName) {
        try {
            // Fetch the feed from the RSS URL
            SyndFeed feed = getFeedFromUrl(rssUrl);

            // Find category; if not found, terminate
            Category category = categoryRepository.findByName(categoryName);
            if (category == null) {
                System.out.println("⁉️ Category does not exist. Please create the category: " + categoryName);
                return;
            }

            // Process the list of posts from the RSS feed
            processFeedEntries(feed.getEntries(), category);

            System.out.println(">>>>> Successfully saved posts from RSS link: " + rssUrl);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" ❌ Error while saving posts from RSS link: " + rssUrl);
        }
    }

    // Method to fetch RSS feed from URL
    private SyndFeed getFeedFromUrl(String rssUrl) {
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            return input.build(new XmlReader(url));
        } catch (FeedException | IOException e) {
            throw new RuntimeException(" @@@ -------- Error reading RSS feed from URL: " + rssUrl, e);
        }
    }

    // Method to process the posts in the RSS feed
    private void processFeedEntries(List<SyndEntry> entries, Category category) {
        for (SyndEntry entry : entries) {
            Optional<Post> existingPost = postRepository.findByLink(entry.getLink());

            if (existingPost.isPresent()) {
                // Update the post if it already exists
                updateExistingPost(existingPost.get(), entry);
            } else {
                // Create a new post if it doesn't exist
                createNewPost(entry, category);
            }
        }
    }

    // Method to update an existing post
    private void updateExistingPost(Post post, SyndEntry entry) {
        post.setTitle(entry.getTitle());
        post.setContent(entry.getDescription().getValue());
        post.setUpdatedAt(Instant.now());
        postRepository.save(post);
        System.out.println("4. --- Post updated: " + entry.getTitle());
    }

    // Method to create a new post
    private void createNewPost(SyndEntry entry, Category category) {
        Post post = new Post();
        post.setTitle(entry.getTitle());
        post.setContent(entry.getDescription().getValue());
        post.setLink(entry.getLink());
        post.setPubDate(Instant.now());
        post.setCategory(category);
        postRepository.save(post);
        System.out.println("5. --- New post inserted: " + entry.getTitle());
    }

    public void addRssLink(String rssUrl, Category category, Resource resource) {
        RssLink rssLink = new RssLink();
        rssLink.setUrl(rssUrl);
        rssLink.setCategory(category);
        rssLink.setResource(resource);
        rssLinkRepository.save(rssLink);
    }

    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }
}
