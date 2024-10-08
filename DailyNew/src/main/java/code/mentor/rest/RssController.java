package code.mentor.rest;

import code.mentor.dto.RssRequestDTO;
import code.mentor.models.Category;
import code.mentor.models.Resource;
import code.mentor.payload.request.RssRequest;
import code.mentor.service.RssFeedService;
import code.mentor.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/rss")
public class RssController {

    @Autowired
    private RssFeedService rssFeedService;

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/load")
    public ResponseEntity<String> loadRssFeed(@RequestBody RssRequest rssRequest) {
        String rssUrl = rssRequest.getRssLink();
        String categoryName = rssRequest.getCategoryName();
        String resourceName = rssRequest.getResourceName();

        // Lấy category (nếu không tồn tại, trả về lỗi)
        Category category = rssFeedService.getCategoryByName(categoryName);
        if (category == null) {
            return ResponseEntity.badRequest().body("Category doesn't exist");
        }

        // Lấy resource (nếu không tồn tại, trả về lỗi)
        Resource resource = resourceService.getResourceByName(resourceName);
        if (resource == null) {
            return ResponseEntity.badRequest().body("Resource doesn't exist");
        }

        // Lưu RSS link vào cơ sở dữ liệu
        rssFeedService.addRssLink(rssUrl, category, resource);

        return ResponseEntity.ok("RSS feed loaded successfully at " + rssUrl + " for Category: " + categoryName + " and Resource: " + resourceName);
    }
}
