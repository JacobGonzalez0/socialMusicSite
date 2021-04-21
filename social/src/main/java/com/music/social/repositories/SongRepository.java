package com.music.social.repositories;

import java.util.List;

import com.music.social.models.Musician;
import com.music.social.models.Song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findAllByArtistOrderByIdDesc(Musician musician);
    List<Song> findAllByOrderByIdAsc();
    List<Song> findAllByAlbum(String album);
    List<Song> findAllByTitle(String title);
    Song getOne(long id);

}
