package code.mentor.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RssRequest {
    private String categoryName;
    private String resourceName;
    private String rssLink;

    public RssRequest() {
    }

    public RssRequest(String categoryName, String resourceName, String rssLink) {
        this.categoryName = categoryName;
        this.resourceName = resourceName;
        this.rssLink = rssLink;
    }

}
