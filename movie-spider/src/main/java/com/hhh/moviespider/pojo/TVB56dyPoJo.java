package com.hhh.moviespider.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tvb_56dy")
public class TVB56dyPoJo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "movieId")
    private Integer movieId;

    /*标题*/
    @Column(name = "title")
    private String title;

    /*简介*/
    @Column(name = "introduction")
    private String introduction;

    @Column(name = "doubanScore")
    private String doubanScore;

    /*上映时间*/
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "releaseTime")
    private Date releaseTime;

//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime")
    private Date updateTime;

    @Column(name = "cover")
    private String cover;

    @Column(name = "country")
    private String country;

    @Column(name = "type")
    private String type;

    @Column(name = "director")
    private String director;

    @Column(name = "url")
    private String url;

    @Column(name = "kind")
    private Integer kind;

    /**
     * 更新情况
     */
    @Column(name = "updateSituation")
    private String updateSituation;

    @Column(name = "actor")
    private String actor;

}
