package com.hhh.moviespider.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tvb_56dy_episodes")
public class TVB56dyEpisodesPoJo {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "movieId")
    private Long movieId;

    @Column(name = "name")
    private String name;

    @Column(name = "playLink")
    private String playLink;

}
