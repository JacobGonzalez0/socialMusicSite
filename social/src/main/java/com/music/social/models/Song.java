package com.music.social.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Song must have a name")
    private String title;

    private String album;

    @Column(columnDefinition="TEXT")
    private String description;

    private long track;

    @NotEmpty(message = "Song must specify length")
    private String length;

    @OneToOne
    private Musician artist;

    @OneToOne
    private Image image;

    public Song() {
    }

    public Song(String title, String length, Musician artist) {
        this.title = title;
        this.length = length;
        this.artist = artist;
    }

    public Song(String title, String album, String description, long track, String length, Musician artist) {
        this.title = title;
        this.album = album;
        this.description = description;
        this.track = track;
        this.length = length;
        this.artist = artist;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return this.album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTrack() {
        return this.track;
    }

    public void setTrack(long track) {
        this.track = track;
    }

    public String getLength() {
        return this.length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Musician getArtist() {
        return this.artist;
    }

    public void setArtist(Musician artist) {
        this.artist = artist;
    }

    
}
