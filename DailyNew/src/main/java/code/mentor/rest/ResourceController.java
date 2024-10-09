package code.mentor.rest;

import code.mentor.models.Resource;
import code.mentor.models.RssLink;
import code.mentor.payload.response.ResourceResponse;
import code.mentor.service.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api/resource")
public class ResourceController {

    private final ResourceServiceImpl resourceServiceImpl;

    @Autowired
    public ResourceController(ResourceServiceImpl resourceServiceImpl) {
        this.resourceServiceImpl = resourceServiceImpl;
    }

    // Lấy tất cả các resource
    @GetMapping
    public ResponseEntity<List<ResourceResponse>> getAllResources() {
        List<Resource> resources = resourceServiceImpl.getAllResources();
        List<ResourceResponse> resourceResponses = resources.stream()
                .map(this::toResourceResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resourceResponses);
    }

    // Method to convert Resource to ResourceResponse
    private ResourceResponse toResourceResponse(Resource resource) {
        Set<Integer> rssLinkIds = resource.getRssLinks().stream()
                .map(RssLink::getId)
                .collect(Collectors.toSet());
        return new ResourceResponse(resource.getId(), resource.getName(), rssLinkIds);
    }

    // Thêm mới resource
    @PostMapping
    public ResponseEntity<Resource> createResource(@RequestBody Resource resource) {
        Resource createdResource = resourceServiceImpl.saveResource(resource);
        return ResponseEntity.ok(createdResource);
    }

    // Xóa resource
    @DeleteMapping("/{resourceId}")
    public ResponseEntity<Void> deleteResource(@PathVariable int resourceId) {
        resourceServiceImpl.deleteResourceById(resourceId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{resourceId}")
    public ResponseEntity<Resource> updateResource(@PathVariable int resourceId, @RequestBody Resource resource) {
        Resource updatedResource = resourceServiceImpl.updateResource(resourceId, resource);
        return ResponseEntity.ok(updatedResource);
    }
}
