package com.music.social.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = "User must be included in like")
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Post post;

    @OneToOne(cascade = CascadeType.ALL)
    private Song song;

    @OneToOne(cascade = CascadeType.ALL)
    private Comment comment;

    public Like() {
    }

    public Like(User user,  Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public Like(User user,Song song) {
        this.user = user;
        this.song = song;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Song getSong() {
        return this.song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Comment getComment() {
        return this.comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
    
}
