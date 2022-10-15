package com.novare.netflax.contentCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentCategoryController {
    ContentCategoryRepository contentCategoryRepository;

    @Autowired
    public ContentCategoryController(ContentCategoryRepository contentCategoryRepository) {
        this.contentCategoryRepository = contentCategoryRepository;
    }

    @PostMapping("/content/category")
    public ResponseEntity<ContentCategory> createCategory(@RequestBody ContentCategory contentCategory) {
        contentCategoryRepository.save(contentCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
    }
}
