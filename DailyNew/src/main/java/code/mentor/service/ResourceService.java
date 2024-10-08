package code.mentor.service;

import code.mentor.models.Resource;
import code.mentor.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    // Tạo mới hoặc lấy Resource nếu đã tồn tại
    public Resource getOrCreateResource(String resourceName) {
        Resource resource = resourceRepository.findByName(resourceName);
        if (resource == null) {
            resource = new Resource();
            resource.setName(resourceName);
            resourceRepository.save(resource);
        }
        return resource;
    }

    // Lấy tất cả các resource
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    // Lưu resource
    public Resource saveResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    // Xóa resource theo ID
    public void deleteResourceById(int resourceId) {
        resourceRepository.deleteById(resourceId);
    }

    // Update resource
    public Resource updateResource(int resourceId, Resource resource) {
        Resource existingResource = resourceRepository.findById(resourceId).orElse(null);
        if (existingResource != null) {
            existingResource.setName(resource.getName());
            return resourceRepository.save(existingResource);
        }
        return null;
    }

    public Resource getResourceByName(String resourceName) {
        return resourceRepository.findByName(resourceName);
    }
}