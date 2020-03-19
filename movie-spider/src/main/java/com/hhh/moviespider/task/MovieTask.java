package com.hhh.moviespider.task;

import com.hhh.moviespider.model.BDMovie;
import com.hhh.moviespider.pipeline.BDMoviePipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Site;
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
}
