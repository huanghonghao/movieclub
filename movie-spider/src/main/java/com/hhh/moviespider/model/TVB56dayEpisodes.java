package com.hhh.moviespider.model;

import lombok.Data;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.HasKey;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import javax.persistence.Column;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@Data
//@HelpUrl(value = {"http://www.56dy.com/ju/list/----10-*", "http://www.56dy.com/ju/\\d+/*"},
//        sourceRegion = "//ul[@class='stui-page'] | //div[@id='pycon']//div[@class='stui-pannel_bd']/ul[@class='stui-content__playlist']")
//@TargetUrl(value = "*", sourceRegion = "//div[@class='stui-player']/div[@class='stui-player__video']/iframe/@src")
public class TVB56dayEpisodes {

    private ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");

    private String id;

    private Long movieId;

//    @ExtractBy("//title/text()/regex('\\d+$')")
    private String name;

    private String playLink;
}