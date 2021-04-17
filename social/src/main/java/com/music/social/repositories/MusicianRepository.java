package com.music.social.repositories;

import java.util.List;

import com.music.social.models.Musician;
import com.music.social.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Long> {

    Musician findByUser(User user);
    Musician getOne(long id);
    List<Musician> findAll();

}
