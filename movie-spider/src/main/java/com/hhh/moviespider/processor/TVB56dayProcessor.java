package com.hhh.moviespider.processor;

import com.hhh.moviespider.model.TVB56dayEpisodes;
import com.hhh.moviespider.utils.UrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.PageMapper;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.List;
import java.util.Map;

@Component
public class TVB56dayProcessor implements PageProcessor {

    private static final String LIST_URL = "http://www.56dy.com/ju/list/----10-.*";

    private static final String PLAY_URL = "http://www.56dy.com/ju/\\d+/play.*";

    private static final String MOVIE_URL = "http://www.56dy.com/ju/\\d+$";

    private static final String TARGET_URL = "*";

    private Site site = Site.me().setCycleRetryTimes(5).setSleepTime(2000).setTimeOut(10000).setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");;

    private PageMapper<TVB56dayEpisodes> tvb56dayEpisodesPageMapper = new PageMapper<>(TVB56dayEpisodes.class);

    @Override
    public void process(Page page) {
        if(page.getUrl().regex(LIST_URL).match()) { // 是列表页
            page.addTargetRequests(page.getHtml().xpath("//ul[@class='stui-page']//a[@href]").links().regex(LIST_URL).all());
            page.addTargetRequests(page.getHtml().xpath("//div[@class='stui-pannel_bd']/ul[@class='stui-vodlist']").links().regex(MOVIE_URL).all());
        } else if(page.getUrl().regex(MOVIE_URL).match()) {
            page.addTargetRequests(page.getHtml().xpath("//div[@id='pycon']//div[@class='stui-pannel_bd']/ul[@class='stui-content__playlist']").links().regex(PLAY_URL).all());
        } else if(page.getUrl().regex(PLAY_URL).match()) {
            String playPage = page.getHtml().xpath("//div[@class='stui-player']/div[@class='stui-player__video']/iframe/@src").get();
            if(StringUtils.isNotBlank(playPage)) {
                String url = page.getHtml().xpath("//h1[@class='title']/a/@href").get();
                String name = page.getHtml().xpath("//h1[@class='title']/a/text()").regex("(?<=第)\\d+(?=集)").get();
                page.addTargetRequest(playPage + "?movieId=" + url.substring(url.lastIndexOf("/") +1) + "&name=" + name);
            }
        } else {
            TVB56dayEpisodes tvb56dayEpisodes = tvb56dayEpisodesPageMapper.get(page);
            if(tvb56dayEpisodes == null) {
                page.setSkip(true);
            } else {
                String selfUrl = page.getUrl().get();
                Map<String, String> paramMap = UrlUtil.parse(selfUrl).params;
                tvb56dayEpisodes.setMovieId(Long.parseLong(paramMap.get("movieId")));
                tvb56dayEpisodes.setName(paramMap.get("name"));
                try {
                    ScriptEngine scriptEngine = tvb56dayEpisodes.getScriptEngine();
                    scriptEngine.eval(page.getHtml().xpath("//script/text()/regex('var main.*')").get());
                    tvb56dayEpisodes.setPlayLink(UrlUtils.getHost(page.getUrl().get()) + scriptEngine.get("main"));
                    scriptEngine.eval(page.getHtml().xpath("//script/text()/regex('var videoid.*')").get());
                    tvb56dayEpisodes.setId((String)scriptEngine.get("videoid"));
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                page.putField("episodes", tvb56dayEpisodes);
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.out.println(UrlUtil.parse("https://tudou.diediao-kuyun.com/20200219/11763_f186f72c/index.m3u8?sign=d8385a7731a42bb3193f202cc20b7113&movieId=3543").params.get("movieId"));
    }
}
