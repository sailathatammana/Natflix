package com.novare.netflax.contentType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentTypeController {

    ContentTypeRepository contentTypeRepository;

    @Autowired
    public ContentTypeController(ContentTypeRepository contentTypeRepository) {
        this.contentTypeRepository = contentTypeRepository;
    }

    @PostMapping("/content/type")
    public ResponseEntity<ContentType> createPost(@RequestBody ContentType contentType) {
        contentTypeRepository.save(contentType);
        return ResponseEntity.status(HttpStatus.CREATED).body(contentType);
    }
}
