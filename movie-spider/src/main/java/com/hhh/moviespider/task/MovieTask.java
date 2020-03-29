package com.hhh.moviespider.task;

import com.hhh.moviespider.model.BDMovie;
import com.hhh.moviespider.model.TVB56dy;
import com.hhh.moviespider.pipeline.BDMoviePipeline;
import com.hhh.moviespider.pipeline.TVB56dyEpisodesPipeline;
import com.hhh.moviespider.pipeline.TVB56dyPipeline;
import com.hhh.moviespider.processor.TVB56dayProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

@Component
public class MovieTask {

    @Autowired
    private BDMoviePipeline bdMoviePipeline;

    @Autowired
    private TVB56dyPipeline tvb56dyPipeline;

    @Autowired
    private TVB56dayProcessor tvb56dayProcessor;

    @Autowired
    private TVB56dyEpisodesPipeline tvb56DyEpisodesPipeline;

    @Scheduled(cron = "0 0 0 * * *")
    public void bdMovieSpider() {
//        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
//                new Proxy("47.104.234.32",3128)));
        OOSpider.create(Site.me().setSleepTime(2000).setTimeOut(10000).setCycleRetryTimes(5)
                , bdMoviePipeline, BDMovie.class)
//                .setDownloader(httpClientDownloader)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(30000)))
                .addUrl("https://www.bd-film.cc/movies/index.htm")
                .thread(5)
                .run();
    }

    @Scheduled(cron = "0 16 21,23 * * *")
    public void tvb56dySpider() {
        OOSpider.create(Site.me().setSleepTime(1000).setTimeOut(10000).setCycleRetryTimes(5)
                , tvb56dyPipeline, TVB56dy.class)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(30000)))
                .addUrl("http://www.56dy.com/ju/list/----10-")
                .thread(6)
                .run();
    }

    @Scheduled(cron = "0 15 21,23 * * *")
    public void tvb56dyEPSpider() {
        Spider.create(tvb56dayProcessor)
                .addPipeline(tvb56DyEpisodesPipeline)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(30000)))
                .addUrl("http://www.56dy.com/ju/list/----10-")
                .thread(30).run();
    }
}
