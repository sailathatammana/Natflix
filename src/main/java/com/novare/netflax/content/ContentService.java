package com.novare.netflax.content;

import com.novare.netflax.contentDetails.ContentDetails;
import com.novare.netflax.contentDetails.ContentDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class ContentService {

    ContentRepository contentRepository;
    ContentDetailsRepository contentDetailsRepository;


    public ContentService(ContentRepository contentRepository, ContentDetailsRepository contentDetailsRepository){
        this.contentRepository=contentRepository;
        this.contentDetailsRepository = contentDetailsRepository;
    }

    public Content createContent(Content content){
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
}
