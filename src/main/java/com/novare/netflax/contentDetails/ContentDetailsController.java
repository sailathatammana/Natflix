package com.novare.netflax.contentDetails;

import com.novare.netflax.ResourceNotFoundException;
import com.novare.netflax.content.Content;
import com.novare.netflax.content.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContentDetailsController {
    ContentRepository contentRepository;
    ContentDetailsRepository contentDetailsRepository;

    @Autowired
    public ContentDetailsController(ContentDetailsRepository contentDetailsRepository, ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
        this.contentDetailsRepository = contentDetailsRepository;
    }

    @PostMapping("details-other/create/{contentId}")
    public ResponseEntity<ContentDetails> createContentDetails(@PathVariable Long contentId, @RequestBody ContentDetails details) {
        Content content = contentRepository.findById(contentId).orElseThrow(ResourceNotFoundException::new);
        details.setContent(content);
        contentDetailsRepository.save(details);
        return ResponseEntity.status(HttpStatus.CREATED).body(details);
    }

    @GetMapping("details-other/{contentId}")
    public ResponseEntity<ContentDetails> listDetailsOnContent(@PathVariable Long contentId) {
        Content content = contentRepository.findById(contentId).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(content.getContentDetails());
    }

    @PutMapping("details-other/update/{id}")
    public ResponseEntity<ContentDetails> updateDetails(@PathVariable Long id, @RequestBody ContentDetails updatedDetails) {
        ContentDetails details = contentDetailsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedDetails.setId(id);
        updatedDetails.setContent(details.getContent());
        contentDetailsRepository.save(updatedDetails);
        return ResponseEntity.ok(updatedDetails);
    }
}
