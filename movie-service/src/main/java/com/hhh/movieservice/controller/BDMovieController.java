package com.hhh.movieservice.controller;

import com.hhh.movieservice.entity.BDMovie;
import com.hhh.movieservice.service.MovieService;
import com.hhh.movieservice.vo.HomePageVo;
import com.hhh.movieservice.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/BDMovie")
public class BDMovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/list")
    public R<List<HomePageVo>> getMovieByType(@RequestParam(defaultValue = "0")int offset, @RequestParam(defaultValue = "21")int limit, @RequestParam int kind) {
        return R.data(movieService.getBDMovies(offset, limit, kind));
    }

    @GetMapping("/detail")
    public R<BDMovie> detail(@RequestParam(required = true) int id) {
        return R.data(movieService.getBDMovieById(id));
    }

    @GetMapping("/search")
    public R<List<HomePageVo>> searchMovie(@RequestParam(defaultValue = "")String title) {
        return R.data(movieService.searchMovie(title));
    }

}
