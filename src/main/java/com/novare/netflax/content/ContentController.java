package com.novare.netflax.content;

import com.novare.netflax.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ContentController {

    ContentRepository contentRepository;

    @Autowired
    public ContentController(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @PostMapping("/content/create/")
    public ResponseEntity<Content> createContent(@RequestBody Content content) {
        contentRepository.save(content);
        return ResponseEntity.status(HttpStatus.CREATED).body(content);
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

    @PutMapping("/content/update/{id}")
    public ResponseEntity<Content> updateContent(@PathVariable Long id, @RequestBody Content updatedContent) {
        contentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedContent.setId(id);
        Content content = contentRepository.save(updatedContent);
        return ResponseEntity.ok(updatedContent);
    }

    @DeleteMapping("/content/delete/{id}")
    public ResponseEntity<Content> deletePost(@PathVariable Long id) {
        Content content = contentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        contentRepository.delete(content);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
