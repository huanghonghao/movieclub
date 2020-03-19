package com.hhh.moviespider.model;

import com.hhh.moviespider.engine.JavaScriptEngine;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.Formatter;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
@Slf4j
@HelpUrl(value = "https://www.bd-film.cc/movies/index_\\d{1,2}.htm", sourceRegion = "//div[@id='pages']/ul")
@TargetUrl(value = "https://www.bd-film.cc/\\D{2}/\\d+.htm", sourceRegion = "//ul[@id='content_list']")
public class BDMovie implements AfterExtractor {

    private ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");

    @ExtractBy("//div[@id='dfg-video-details']//h3/text()")
    private String title;

    @ExtractBy(type=ExtractBy.Type.Regex, value = "var urls = .*(?=,.*scoreData =)")
    private String downloadLinks;

    @ExtractBy("//*[@id=\"plot\"]/div/div/allText()")
    private String introduction;

    @ExtractBy("//*[@id='dfg-video-details']/div/dl/dd/div[2]/a[@class='btn btn-success btn-lg db']/text()")
    private String doubanScore;

    @Formatter("yyyy-MM-dd")
    @ExtractBy("//*[@id=\"dfg-video-details\"]/div/dl/dd/allText()/regex('\\d+-\\d+-\\d+')")
    private Date chinaReleaseTime;

    @Formatter("yyyy-MM-dd HH:mm")
    @ExtractBy("//div[@class='dfg-video-title']/allText()/regex('\\d+-\\d+-\\d+\\s+\\d+:\\d+')")
    private Date updateTime;

    @ExtractBy("//*[@id='dfg-video-details']//a[@class='videopic']/img/@src")
    private String cover;

    private String director;

    @ExtractBy("//div[@id='dfg-video-details']/allText()") //  /regex('(?<=语言[：:]).*语')
    private String detail;

    private String language;

    private String country;

    private String type;

    private String url;

    @Override
    public void afterProcess(Page page) {
        try {
            // 下载连接后续处理
            scriptEngine.eval(this.getDownloadLinks());
            String enCodeUrls = new StringBuffer((String)scriptEngine.get("urls")).reverse().toString();
            final Base64.Decoder decoder = Base64.getDecoder();
            final byte[] textByte = enCodeUrls.getBytes("UTF-8");
            String decodeUrls = new String(decoder.decode(textByte));
            String[] urlArr = decodeUrls.replace("<p>","").replace("</p>","").split("###");
            List<String> urlList = Arrays.stream(urlArr).map(v -> '"' + v.trim() + '"').collect(Collectors.toList());
            this.setDownloadLinks(urlList.toString());

            // 处理语言字段
            this.setLanguage(matchByDetail("(?<=语言[：:]).*语"));
            // 处理国家字段
            this.setCountry(matchByDetail("(?<=地区[：:]).*(?=语言)"));
            // 电影类型
            this.setType(matchByDetail("(?<=类型[：:]).*(?=制片国家)"));
            // 导演
            this.setDirector(matchByDetail("(?<=导演[：:]).*(?=编剧)"));

            // 当前页面的url
            this.setUrl(page.getUrl().get());
        } catch (Exception e) {
            log.error(this.toString(), e);
        }
    }

    /**
     * 找到就返回, 找不到返回null, 默认返回第一组
     * @param pattern
     * @return
     */
    private String matchByDetail(String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(this.getDetail());
        if(matcher.find()) {
            return matcher.group().trim();
        }
        return null;
    }
}
