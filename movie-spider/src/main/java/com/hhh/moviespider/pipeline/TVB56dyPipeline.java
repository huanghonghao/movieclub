package com.hhh.moviespider.pipeline;

import com.hhh.moviespider.dao.BDMovieDao;
import com.hhh.moviespider.model.TVB56dy;
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
public class TVB56dyPipeline implements PageModelPipeline<TVB56dy> {

    @Resource
    private BDMovieDao bdMovieDao;

    @Override
    @Transactional
    public void process(TVB56dy tvb56dy, Task task) {
        BDMoviePoJo bdMoviePoJo = new BDMoviePoJo();
        bdMoviePoJo.setUrl(tvb56dy.getUrl());
        Example<BDMoviePoJo> example = Example.of(bdMoviePoJo);
        List<BDMoviePoJo> list = bdMovieDao.findAll(example);
        if(list.size() == 0) {
            BeanUtils.copyProperties(tvb56dy, bdMoviePoJo);
            bdMovieDao.saveAndFlush(bdMoviePoJo);
        } else {
            BDMoviePoJo first = list.get(0);
            if(first.getUpdateTime() != null && tvb56dy.getUpdateTime() != null && first.getUpdateTime().getTime() != tvb56dy.getUpdateTime().getTime()) {
                BeanUtils.copyProperties(tvb56dy, bdMoviePoJo);
                bdMoviePoJo.setId(first.getId());
                bdMovieDao.saveAndFlush(bdMoviePoJo);
            }
        }
    }
}
