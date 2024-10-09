package code.mentor.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ResourceResponse {
    private Integer id;
    private String name;
    private Set<Integer> rssLinkIds;

    public ResourceResponse(Integer id, String name, Set<Integer> rssLinkIds) {
        this.id = id;
        this.name = name;
        this.rssLinkIds = rssLinkIds;
    }

    public ResourceResponse() {
    }
}
