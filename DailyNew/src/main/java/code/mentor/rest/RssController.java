package code.mentor.rest;

import code.mentor.models.Category;
import code.mentor.models.Resource;
import code.mentor.payload.request.RssRequest;
import code.mentor.service.RssFeedServiceImpl;
import code.mentor.service.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/rss")
public class RssController {

    @Autowired
    private RssFeedServiceImpl rssFeedServiceImpl;

    @Autowired
    private ResourceServiceImpl resourceServiceImpl;

    @PostMapping("/load")
    public ResponseEntity<String> loadRssFeed(@RequestBody RssRequest rssRequest) {
        String rssUrl = rssRequest.getRssLink();
        String categoryName = rssRequest.getCategoryName();
        String resourceName = rssRequest.getResourceName();

        // Lấy category (nếu không tồn tại, trả về lỗi)
        Category category = rssFeedServiceImpl.getCategoryByName(categoryName);
        if (category == null) {
            return ResponseEntity.badRequest().body("Category doesn't exist");
        }

        // Lấy resource (nếu không tồn tại, trả về lỗi)
        Resource resource = resourceServiceImpl.getResourceByName(resourceName);
        if (resource == null) {
            return ResponseEntity.badRequest().body("Resource doesn't exist");
        }

        // Lưu RSS link vào cơ sở dữ liệu
        rssFeedServiceImpl.addRssLink(rssUrl, category, resource);

        return ResponseEntity.ok("RSS feed loaded successfully at " + rssUrl + " for Category: " + categoryName + " and Resource: " + resourceName);
    }
}
