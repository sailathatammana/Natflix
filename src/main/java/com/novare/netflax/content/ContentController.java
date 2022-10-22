package com.novare.netflax.content;

import com.novare.netflax.ResourceNotFoundException;
import com.novare.netflax.uploadServices.IStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class ContentController {
    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

    ContentRepository contentRepository;
    ContentService contentService;
    private final IStorageService iStorageService;

    @Autowired
    public ContentController(ContentRepository contentRepository, ContentService contentService, IStorageService iStorageService) {
        this.contentRepository = contentRepository;
        this.contentService = contentService;
        this.iStorageService = iStorageService;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename, HttpServletRequest request) {
        Resource file = iStorageService.loadAsResource(filename);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }

    @PostMapping(value = "/content/create/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Content> createContent(@RequestBody Content content) {
        if (content.getThumbnail_url() != null) {
            String thumbnail = contentService.image(content.getThumbnail_url());
            content.setThumbnail_url(thumbnail);
        }
        if (content.getBanner_url() != null) {
            String banner = contentService.image(content.getBanner_url());
            content.setBanner_url(banner);
        }
        if (content.getLogo_url() != null) {
            String logo = contentService.image(content.getLogo_url());
            content.setLogo_url(logo);
        }
        Content createData = contentService.createContent(content);
        return ResponseEntity.status(HttpStatus.CREATED).body(createData);
    }

    @GetMapping("/content")
    public ResponseEntity<List<Content>> listAllContents() {
        List<Content> contents = contentRepository.findAll();
        return ResponseEntity.ok(contents);
    }

    @GetMapping(value = "/content", params = {"type_id"})
    public ResponseEntity<List<Content>> getContentByType(@RequestParam int type_id) {
        List<Content> contents = contentRepository.findAll().stream()
                .filter((item) -> item.getType_id() == type_id)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contents);
    }

    @PutMapping(value = "/content/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Content> updateContent(@PathVariable Long id, @RequestBody Content updatedContent) {
        contentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedContent.setId(id);
        if (!(Objects.equals(updatedContent.getThumbnail_url(), "")||(updatedContent.getThumbnail_url().contains("http://localhost:8080/files")))) {
            String thumbnail = contentService.image(updatedContent.getThumbnail_url());
            updatedContent.setThumbnail_url(thumbnail);
        }
        if (!(Objects.equals(updatedContent.getBanner_url(), "")||(updatedContent.getBanner_url().contains("http://localhost:8080/files")))) {
            String banner = contentService.image(updatedContent.getBanner_url());
            updatedContent.setBanner_url(banner);
        }
        if (!(Objects.equals(updatedContent.getLogo_url(), "")||(updatedContent.getLogo_url().contains("http://localhost:8080/files")))) {
            String logo = contentService.image(updatedContent.getLogo_url());
            updatedContent.setLogo_url(logo);
        }
        contentRepository.save(updatedContent);
        return ResponseEntity.ok(updatedContent);
    }

    @DeleteMapping("/content/delete/{id}")
    public ResponseEntity<Content> deleteContent(@PathVariable Long id) {
        Content content = contentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        contentRepository.delete(content);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
