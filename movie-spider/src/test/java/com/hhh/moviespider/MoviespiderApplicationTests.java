package com.hhh.moviespider;

import com.hhh.moviespider.model.BDMovie;
import com.hhh.moviespider.pipeline.BDMoviePipeline;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

@SpringBootTest
class MoviespiderApplicationTests {

    @Autowired
    private BDMoviePipeline bdMoviePipeline;

    @Test
    void bdSpider() {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
                new Proxy("47.104.234.32",3128)));
        OOSpider.create(Site.me().setSleepTime(1000).setTimeOut(10000).setCycleRetryTimes(5)
                , bdMoviePipeline, BDMovie.class)
//                .setDownloader(httpClientDownloader)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(30000)))
                .addUrl("https://www.bd-film.cc/movies/index.htm")
                .thread(6)
                .run();
    }

}
