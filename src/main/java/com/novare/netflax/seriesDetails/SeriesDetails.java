package com.novare.netflax.seriesDetails;

import javax.persistence.*;

@Entity
@Table(name = "details_series")
public class SeriesDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int season_number;
    @Column(nullable = false)
    private int episode_number;
    @Column(nullable = false)
    private String title;
    private String summary;
    private String thumbnail_url;
    private String video_code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getVideo_code() {
        return video_code;
    }

    public void setVideo_code(String video_code) {
        this.video_code = video_code;
    }
}
