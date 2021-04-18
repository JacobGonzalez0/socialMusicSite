package com.music.social.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "musicians")
public class Musician {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Name must not be blank")
    private String name;

    private String tagline;
    private String website;
    private String bio;

    @NotEmpty(message = "You must specify pronouns")
    private String pronouns;

    @OneToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    @OneToOne(cascade = CascadeType.ALL)
    private Image banner;


    public Musician() {
    }

    public Musician(String name, String tagline, String website, String bio, String pronouns, User user, Image image, Image banner) {
        this.name = name;
        this.tagline = tagline;
        this.website = website;
        this.bio = bio;
        this.pronouns = pronouns;
        this.user = user;
        this.image = image;
        this.banner = banner;
    }

    public Musician(String name, String tagline, String website, String bio, String pronouns, User user) {
        this.name = name;
        this.tagline = tagline;
        this.website = website;
        this.bio = bio;
        this.pronouns = pronouns;
        this.user = user;
    }

    public Musician(String name, String pronouns, User user) {
        this.name = name;
        this.pronouns = pronouns;
        this.user = user;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return this.tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPronouns() {
        return this.pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getBanner() {
        return this.banner;
    }

    public void setBanner(Image banner) {
        this.banner = banner;
    }


}
