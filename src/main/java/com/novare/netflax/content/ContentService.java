package com.novare.netflax.content;

import com.novare.netflax.contentDetails.ContentDetails;
import com.novare.netflax.contentDetails.ContentDetailsRepository;
import com.novare.netflax.uploadServices.IStorageService;
import com.novare.netflax.utils.FileUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Service
public class ContentService {

    ContentRepository contentRepository;
    ContentDetailsRepository contentDetailsRepository;
    private final IStorageService iStorageService;


    public ContentService(ContentRepository contentRepository, ContentDetailsRepository contentDetailsRepository, IStorageService iStorageService) {
        this.contentRepository = contentRepository;
        this.contentDetailsRepository = contentDetailsRepository;
        this.iStorageService = iStorageService;
    }

    public Content createContent(Content content) {
        if (content.getType_id() == 0) {
            content.setType_id(1);
        }
        if (content.getCategory_id() == 0) {
            content.setCategory_id(1);
        }
        if (content.getLogo_url() == null) {
            content.setLogo_url("");
        }
        if (content.getBanner_url() == null) {
            content.setBanner_url("");
        }
        if (content.getThumbnail_url() == null) {
            content.setThumbnail_url("");
        }
        if (content.getType_id() != 1) {
            ContentDetails details = new ContentDetails();
            details.setVideo_code("");
            content.setContentDetails(details);
            details.setContent(content);
            contentRepository.save(content);
            contentDetailsRepository.save(details);
        }
        contentRepository.save(content);
        return content;
    }

    public String image(String imageUrl) {
        String imageDataBytes = FileUtil.getImageFromBase64(imageUrl);
        byte[] decodedBytes = Base64.decodeBase64(imageDataBytes);
        String image = iStorageService.storeBase64(decodedBytes);
        String imgUrl = MvcUriComponentsBuilder.fromMethodName(ContentController.class, "getFile", image, null).build().toString();
        return imgUrl;
    }
}
