package com.music.social.repositories;

import java.util.List;

import com.music.social.models.Comment;
import com.music.social.models.Like;
import com.music.social.models.Post;
import com.music.social.models.Song;
import com.music.social.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findAllByUser(User user);
    List<Like> findAllByPost(Post post);
    List<Like> findAllBySong(Song song);
    List<Like> findAllByComment(Comment comment);

}
