package com.novare.netflax.seriesDetails;

import com.novare.netflax.ResourceNotFoundException;
import com.novare.netflax.content.Content;
import com.novare.netflax.content.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SeriesDetailsController {
    ContentRepository contentRepository;
    SeriesDetailsRepository seriesDetailsRepository;

    @Autowired
    public SeriesDetailsController(SeriesDetailsRepository seriesDetailsRepository, ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
        this.seriesDetailsRepository = seriesDetailsRepository;
    }

    @PostMapping("details-series/create/{contentId}")
    public ResponseEntity<SeriesDetails> createSeries(@PathVariable Long contentId, @RequestBody SeriesDetails seriesDetails) {
        Content content = contentRepository.findById(contentId).orElseThrow(ResourceNotFoundException::new);
        seriesDetails.setContent(content);
        seriesDetailsRepository.save(seriesDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(seriesDetails);
    }

    @GetMapping("details-series/{contentId}")
    public ResponseEntity<List<SeriesDetails>> listAllSeriesOnContent(@PathVariable Long contentId) {
        Content content = contentRepository.findById(contentId).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(content.getSeriesDetails());
    }

    @PutMapping("details-series/update/{id}")
    public ResponseEntity<SeriesDetails> updateSeriesDetails(@PathVariable Long id, @RequestBody SeriesDetails updatedSeriesDetails) {
        SeriesDetails seriesDetails = seriesDetailsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedSeriesDetails.setId(id);
        updatedSeriesDetails.setContent(seriesDetails.getContent());
        seriesDetailsRepository.save(updatedSeriesDetails);
        return ResponseEntity.ok(updatedSeriesDetails);
    }

    @DeleteMapping("details-series/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeriesDetails(@PathVariable Long id) {
        SeriesDetails seriesDetails = seriesDetailsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        seriesDetailsRepository.delete(seriesDetails);
    }
}
