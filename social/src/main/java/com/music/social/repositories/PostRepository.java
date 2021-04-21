package com.music.social.repositories;

import java.util.List;

import com.music.social.models.Musician;
import com.music.social.models.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();
    List<Post> findAllByMusician(Musician m);
    List<Post> findAllByOrderByIdAsc();
    List<Post> findAllByOrderByIdDesc();
    List<Post> findAllByMusicianOrderByIdAsc(Musician m);
    List<Post> findAllByMusicianOrderByIdDesc(Musician m);
    Post getOne(long id);

}