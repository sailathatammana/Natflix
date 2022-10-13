package com.novare.netflax.contentDetails;

import org.springframework.beans.factory.annotation.Autowired;

public class ContentDetailsController {
    ContentDetailsRepository contentDetailsRepository;

    @Autowired
    public ContentDetailsController(ContentDetailsRepository contentDetailsRepository) {
        this.contentDetailsRepository = contentDetailsRepository;
    }
}
