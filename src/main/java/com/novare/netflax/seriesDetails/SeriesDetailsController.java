package com.novare.netflax.seriesDetails;

import com.novare.netflax.ResourceNotFoundException;
import com.novare.netflax.content.Content;
import com.novare.netflax.content.ContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class SeriesDetailsController {
    private static final Logger logger = LoggerFactory.getLogger(SeriesDetailsController.class);
    ContentRepository contentRepository;
    SeriesDetailsRepository seriesDetailsRepository;
    SeriesDetailsService seriesDetailsService;

    @Autowired
    public SeriesDetailsController(SeriesDetailsRepository seriesDetailsRepository, ContentRepository contentRepository, SeriesDetailsService seriesDetailsService) {
        this.contentRepository = contentRepository;
        this.seriesDetailsRepository = seriesDetailsRepository;
        this.seriesDetailsService = seriesDetailsService;
    }

    @PostMapping("details-series/create/{contentId}")
    public ResponseEntity<SeriesDetails> createSeries(@PathVariable Long contentId, @RequestBody SeriesDetails seriesDetails) {
        Content content = contentRepository.findById(contentId).orElseThrow(ResourceNotFoundException::new);
        if (seriesDetails.getThumbnail_url() != null) {
            String thumbnail = seriesDetailsService.image(seriesDetails.getThumbnail_url());
            seriesDetails.setThumbnail_url(thumbnail);
        }
        if (seriesDetails.getThumbnail_url() == null) {
            seriesDetails.setThumbnail_url("");
        }
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
        if (!(Objects.equals(updatedSeriesDetails.getThumbnail_url(), "") || (updatedSeriesDetails.getThumbnail_url().contains("http://localhost:8080/files")))) {
            String thumbnail = seriesDetailsService.image(updatedSeriesDetails.getThumbnail_url());
            updatedSeriesDetails.setThumbnail_url(thumbnail);
        }
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
