package com.hhh.moviespider.pojo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "bd_movie")
public class BDMoviePoJo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*标题*/
    @Column(name = "title")
    private String title;

    @Column(name = "downloadLinks")
    private String downloadLinks;

    /*简介*/
    @Column(name = "introduction")
    private String introduction;

    @Column(name = "doubanScore")
    private String doubanScore;

    /*上映时间*/
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "chinaReleaseTime")
    private Date chinaReleaseTime;

//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime")
    private Date updateTime;

    @Column(name = "cover")
    private String cover;

    @Column(name = "language")
    private String language;

    @Column(name = "country")
    private String country;

    @Column(name = "type")
    private String type;

    @Column(name = "director")
    private String director;

    @Column(name = "url")
    private String url;

}
