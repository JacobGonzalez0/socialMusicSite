package com.music.social.repositories;

import java.util.List;

import com.music.social.models.Musician;
import com.music.social.models.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();
    List<Post> findAllbyMusician(Musician musician);
    Post getOne(long id);

}