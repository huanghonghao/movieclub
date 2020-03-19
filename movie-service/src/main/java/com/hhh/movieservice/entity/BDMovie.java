package com.hhh.movieservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BDMovie {

    private Long id;

    /*标题*/
    private String title;

    private String downloadLinks;

    /*简介*/
    private String introduction;

    private String doubanScore;

    /*上映时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date chinaReleaseTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    private String url;

    private String cover;

    private String language;

    private String country;

    private String type;

    private String director;

}
