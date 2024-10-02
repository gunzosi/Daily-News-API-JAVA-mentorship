package code.mentor.rest;


import code.mentor.service.RssFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/rss")
public class RssController {

    @Autowired
    private RssFeedService rssFeedService;

    @PostMapping("/load")
    public ResponseEntity<String> loadRssFeed(@RequestParam String rssUrl, @RequestParam String categoryName) {
        rssFeedService.fetchAndSaveRssFeed(rssUrl, categoryName);
        return ResponseEntity.ok("RSS feed has been loaded and add to DATABASED");
    }
}
