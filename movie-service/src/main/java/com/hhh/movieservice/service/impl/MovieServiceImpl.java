package com.hhh.movieservice.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hhh.movieservice.entity.BDMovie;
import com.hhh.movieservice.mapper.MovieMapper;
import com.hhh.movieservice.service.MovieService;
import com.hhh.movieservice.utils.BeanCopyUtils;
import com.hhh.movieservice.vo.HomePageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Resource
    private MovieMapper movieMapper;

    @Override
    public List<HomePageVo> getBDMovies(int offset, int limit, int kind) {
        PageHelper.offsetPage(offset, limit, false);
        return BeanCopyUtils.copyListProperties(movieMapper.getBDMovies(kind), HomePageVo::new);
    }

    @Override
    public BDMovie getBDMovieById(int id) {
        return movieMapper.getOneById(id);
    }

    @Override
    public List<HomePageVo> searchMovie(String title) {
        return BeanCopyUtils.copyListProperties(movieMapper.getByTitle(title), HomePageVo::new);
    }
}
