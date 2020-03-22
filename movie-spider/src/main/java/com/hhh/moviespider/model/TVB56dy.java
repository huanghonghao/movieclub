package com.hhh.moviespider.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.Formatter;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import javax.persistence.Column;
import java.util.Date;

@Data
@Slf4j
@HelpUrl(value = "http://www.56dy.com/ju/list/----10-*", sourceRegion = "//ul[@class='stui-page']//a[@href]")
@TargetUrl(value = "http://www.56dy.com/ju/\\d+", sourceRegion = "//div[@class='stui-pannel_bd']/ul[@class='stui-vodlist']")
public class TVB56dy implements AfterExtractor {

    @ExtractBy("//div[@class='stui-content__detail']/h1[@class='title']/text()")
    private String title;

    /*简介*/
    @ExtractBy("//div[@class='stui-content__detail']//span[@class='detail-content']/text()")
    private String introduction;

    /**
     * 评分
     */
    @ExtractBy("//div[@class='stui-content__detail']//p[5]/text()")
    private String doubanScore;

    /*年份*/
    @Formatter("yyyy")
    @ExtractBy("//div[@class='stui-content__detail']//p[3]/text()/regex('\\d+')")
    private Date releaseTime;

    /**
     * 更新时间
     */
    @Formatter("yyyy-MM-dd HH:mm:ss")
    @ExtractBy("//div[@class='stui-content__detail']//allText()/regex('\\d+-\\d+-\\d+\\s+\\d+:\\d+:\\d+')")
    private Date updateTime;

    /**
     * 封面
     */
    @ExtractBy("//div[@class='stui-content__thumb']//img/@data-original")
    private String cover;

    @ExtractBy("//div[@class='stui-content__detail']//p[3]//a/text()")
    private String country;

    @ExtractBy("//div[@class='stui-content__detail']//p[3]/text()")
    private String type;

    @ExtractBy("//div[@class='stui-content__detail']//p[2]/text()")
    private String director;

    private String url;

    /**
     * 分类
     */
    private int kind;

    /**
     * 更新情况
     */
    @ExtractBy("//div[@class='stui-content__thumb']//span[@class='pic-text']/text()")
    private String updateSituation;

    /**
     * 主演
     */
    @ExtractBy("//div[@class='stui-content__detail']//p[@class='data']/text()")
    private String actor;

    private int movieId;

    @Override
    public void afterProcess(Page page) {
        if(this.doubanScore != null) this.setDoubanScore(this.doubanScore.trim());
        if(this.type != null) this.setType(this.type.trim().split("")[0]);
        if(this.director != null) this.setDirector(this.director.trim());
        if(this.actor != null) this.setActor(this.actor.trim());
        // 当前页面的url
        String url = page.getUrl().get();
        this.setUrl(url);
        this.setKind(4);
        this.setMovieId(Integer.parseInt(url.substring(url.lastIndexOf("/") +1)));
        System.out.println(this.toString());
    }
}
