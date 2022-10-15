package com.novare.netflax.content;

import com.novare.netflax.contentDetails.ContentDetails;
import com.novare.netflax.seriesDetails.SeriesDetails;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private int type_id;
    private int category_id;
    private String summary;
    private String logo_url;
    private String banner_url;
    private String thumbnail_url;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    private List<SeriesDetails> seriesDetails;

    @OneToOne(mappedBy = "content", cascade = CascadeType.ALL)
    private ContentDetails contentDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public List<SeriesDetails> getSeriesDetails() {
        return seriesDetails;
    }

    public void setSeriesDetails(List<SeriesDetails> seriesDetails) {
        this.seriesDetails = seriesDetails;
    }

    public ContentDetails getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(ContentDetails contentDetails) {
        this.contentDetails = contentDetails;
    }
}
