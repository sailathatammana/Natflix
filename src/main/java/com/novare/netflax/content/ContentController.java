package com.novare.netflax.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

    ContentRepository contentRepository;

    @Autowired
    public ContentController(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @PostMapping("/content/create/")
    public ResponseEntity<Content> createPost(@RequestBody Content content) {
        contentRepository.save(content);
        return ResponseEntity.status(HttpStatus.CREATED).body(content);
    }
}
