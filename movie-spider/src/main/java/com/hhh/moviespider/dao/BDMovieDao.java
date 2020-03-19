package com.hhh.moviespider.dao;

import com.hhh.moviespider.pojo.BDMoviePoJo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BDMovieDao extends JpaRepository<BDMoviePoJo, Integer> {
}
