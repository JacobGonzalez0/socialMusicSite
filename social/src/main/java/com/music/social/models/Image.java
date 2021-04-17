package com.music.social.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Image must be somewhere")
    private String url;

    @OneToOne
    private Musician musician;

    @OneToOne
    private User user;

    @OneToOne
    private Song song;

    @OneToOne
    private Post post;

    public Image() {
    }

    public Image(String url, Musician musician) {
        this.url = url;
        this.musician = musician;
    }

    public Image(String url, Post post) {
        this.url = url;
        this.post = post;
    }

    public Image(String url, Song song) {
        this.url = url;
        this.song = song;
    }

    public Image(String url, User user) {
        this.url = url;
        this.user = user;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Musician getMusician() {
        return this.musician;
    }

    public void setMusician(Musician musician) {
        this.musician = musician;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Song getSong() {
        return this.song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    
    
}
