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
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByUser(User user);
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllBySong(Song song);
    Comment getOne(long id);

}
