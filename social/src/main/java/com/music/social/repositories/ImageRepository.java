package com.music.social.repositories;

import java.util.List;

import com.music.social.models.Comment;
import com.music.social.models.Image;
import com.music.social.models.Post;
import com.music.social.models.Song;
import com.music.social.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByUser(User user);
    Image findByUser(User user);
    Image findByPost(Post post);
    Image findBySong(Song song);
    Image getOne(long id);

}