package com.novare.netflax.seriesDetails;

import com.novare.netflax.content.ContentController;
import com.novare.netflax.uploadServices.IStorageService;
import com.novare.netflax.utils.FileUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Service
public class SeriesDetailsService {
    private final IStorageService iStorageService;

    public SeriesDetailsService(IStorageService iStorageService) {
        this.iStorageService = iStorageService;
    }

    public String image(String imageUrl) {
        String imageDataBytes = FileUtil.getImageFromBase64(imageUrl);
        byte[] decodedBytes = Base64.decodeBase64(imageDataBytes);
        String image = iStorageService.storeBase64(decodedBytes);
        String imgUrl = MvcUriComponentsBuilder.fromMethodName(ContentController.class, "getFile", image, null).build().toString();
        return imgUrl;
    }
}
