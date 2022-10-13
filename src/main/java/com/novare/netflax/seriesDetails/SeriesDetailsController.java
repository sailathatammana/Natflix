package com.novare.netflax.seriesDetails;

import org.springframework.beans.factory.annotation.Autowired;

public class SeriesDetailsController {
    SeriesDetailsRepository seriesDetailsRepository;

    @Autowired
    public SeriesDetailsController(SeriesDetailsRepository seriesDetailsRepository) {
        this.seriesDetailsRepository = seriesDetailsRepository;
    }
}
