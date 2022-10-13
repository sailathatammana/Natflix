package com.novare.netflax.contentDetails;

import javax.persistence.*;

@Entity
@Table(name = "details_content")
public class ContentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String video_code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideo_code() {
        return video_code;
    }

    public void setVideo_code(String video_code) {
        this.video_code = video_code;
    }
}
