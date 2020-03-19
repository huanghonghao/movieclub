package com.hhh.moviespider.pipeline;

import com.hhh.moviespider.dao.BDMovieDao;
import com.hhh.moviespider.model.BDMovie;
import com.hhh.moviespider.pojo.BDMoviePoJo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import javax.annotation.Resource;
import java.util.List;

@Component
public class BDMoviePipeline implements PageModelPipeline<BDMovie> {

    @Resource
    private BDMovieDao bdMovieDao;

    @Override
    @Transactional
    public void process(BDMovie bdMovie, Task task) {
        BDMoviePoJo bdMoviePoJo = new BDMoviePoJo();
//        bdMoviePoJo.setUpdateTime(bdMovie.getUpdateTime());
        bdMoviePoJo.setUrl(bdMovie.getUrl());
        Example<BDMoviePoJo> example = Example.of(bdMoviePoJo);
        List<BDMoviePoJo> list = bdMovieDao.findAll(example);
        if(list.size() == 0) {
            BeanUtils.copyProperties(bdMovie, bdMoviePoJo);
            bdMovieDao.saveAndFlush(bdMoviePoJo);
        } else {
            BDMoviePoJo first = list.get(0);
            if(first.getUpdateTime() != null && bdMovie.getUpdateTime() != null && first.getUpdateTime().getTime() != bdMovie.getUpdateTime().getTime()) {
                BeanUtils.copyProperties(bdMovie, bdMoviePoJo);
                bdMoviePoJo.setId(first.getId());
                bdMovieDao.saveAndFlush(bdMoviePoJo);
            }
        }
    }
}
