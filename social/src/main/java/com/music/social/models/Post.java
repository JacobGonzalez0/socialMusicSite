package com.music.social.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @NotEmpty(message = "Post must not be blank")
    private String content;

    @OneToOne
    private Musician musician;

    @OneToOne
    private Image image;

    public Post() {
    }

    public Post(String content, Musician musician) {
        this.content = content;
        this.musician = musician;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Musician getMusician() {
        return this.musician;
    }

    public void setMusician(Musician musician) {
        this.musician = musician;
    }


    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    
}
