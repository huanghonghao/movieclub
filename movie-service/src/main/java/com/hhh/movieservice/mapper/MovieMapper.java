package com.hhh.movieservice.mapper;

import com.hhh.movieservice.entity.BDMovie;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface MovieMapper {

    List<BDMovie> getBDMovies();

    BDMovie getOneById(int id);

    List<BDMovie> getByTitle(String title);
}
