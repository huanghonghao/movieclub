package com.hhh.movieservice.mapper;

import com.hhh.movieservice.entity.BDMovie;
import com.hhh.movieservice.entity.TVB56dyEpisodes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface MovieMapper {

    List<BDMovie> getBDMovies(int kind);

    BDMovie getOneById(int id);

    List<BDMovie> getByTitle(String title);

    List<BDMovie> getTVBMovies();

    List<TVB56dyEpisodes> getEpisodes(int movieId);
}
