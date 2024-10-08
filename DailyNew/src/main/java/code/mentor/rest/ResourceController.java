package code.mentor.rest;

import code.mentor.models.Resource;
import code.mentor.service.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Resource>> getAllResources() {
        List<Resource> resources = resourceServiceImpl.getAllResources();
        return ResponseEntity.ok(resources);
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
