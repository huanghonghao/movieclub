package com.hhh.moviespider.pipeline;

import com.hhh.moviespider.dao.TVB56dyEpisodesDao;
import com.hhh.moviespider.model.TVB56dayEpisodes;
import com.hhh.moviespider.pojo.TVB56dyEpisodesPoJo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TVB56dyEpisodesPipeline implements Pipeline {

    @Resource
    private TVB56dyEpisodesDao tvb56dyEpisodesDao;

    @Override
    @Transactional
    public void process(ResultItems resultItems, Task task) {
        TVB56dayEpisodes episodes = resultItems.get("episodes");
        if(episodes != null) {
            TVB56dyEpisodesPoJo tvb56DyEpisodesPoJo = new TVB56dyEpisodesPoJo();
            tvb56DyEpisodesPoJo.setMovieId(episodes.getMovieId());
            tvb56DyEpisodesPoJo.setId(episodes.getId());
            Example<TVB56dyEpisodesPoJo> example = Example.of(tvb56DyEpisodesPoJo);
            List<TVB56dyEpisodesPoJo> list = tvb56dyEpisodesDao.findAll(example);
            if(list.size() == 0) {
                BeanUtils.copyProperties(episodes, tvb56DyEpisodesPoJo);
                tvb56dyEpisodesDao.saveAndFlush(tvb56DyEpisodesPoJo);
            }
        }
    }
}
