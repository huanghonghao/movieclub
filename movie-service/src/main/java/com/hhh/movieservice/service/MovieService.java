package com.hhh.movieservice.service;

import com.hhh.movieservice.entity.BDMovie;
import com.hhh.movieservice.vo.HomePageVo;

import java.util.List;

public interface MovieService {

    List<HomePageVo> getBDMovies(int offset, int limit);

    BDMovie getBDMovieById(int id);

    List<HomePageVo> searchMovie(String title);
}
