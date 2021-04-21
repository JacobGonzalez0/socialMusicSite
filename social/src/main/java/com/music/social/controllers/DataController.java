package com.music.social.controllers;


import java.util.List;

import com.music.social.models.Image;
import com.music.social.models.Like;
import com.music.social.models.Musician;
import com.music.social.models.Post;
import com.music.social.models.Song;
import com.music.social.models.User;
import com.music.social.repositories.ImageRepository;
import com.music.social.repositories.LikeRepository;
import com.music.social.repositories.MusicianRepository;
import com.music.social.repositories.PostRepository;
import com.music.social.repositories.SongRepository;
import com.music.social.repositories.UserRepository;
import com.music.social.services.UserServices;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    MusicianRepository musicianRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserServices userServices;  

    @GetMapping("/post/all")
    public String getAllPosts(){

        List<Post> posts = postRepository.findAllByOrderByIdDesc();

        JSONArray response = new JSONArray();

        for(int i = 0; i < posts.size();i++){
            JSONObject obj = new JSONObject();
            obj.put("content", posts.get(i).getContent());
            obj.put("id", posts.get(i).getId());
            

            JSONObject image = new JSONObject();
            image.put("id",posts.get(i).getImage().getId());
            image.put("url",posts.get(i).getImage().getUrl());
            
            obj.put("image",image);
            JSONObject musician = new JSONObject();
            musician.put("id", posts.get(i).getMusician().getId());
            musician.put("name", posts.get(i).getMusician().getName());
            musician.put("tagline", posts.get(i).getMusician().getTagline());
            musician.put("website", posts.get(i).getMusician().getWebsite());
            musician.put("bio", posts.get(i).getMusician().getBio());
            musician.put("pronouns", posts.get(i).getMusician().getPronouns());

            JSONObject banner = new JSONObject();
            banner.put("id",posts.get(i).getMusician().getBanner().getId());
            banner.put("url",posts.get(i).getMusician().getBanner().getUrl());
            musician.put("banner",banner);

            JSONObject imageMus = new JSONObject();
            imageMus.put("id", posts.get(i).getMusician().getImage().getId());
            imageMus.put("url", posts.get(i).getMusician().getImage().getUrl());
            musician.put("image",imageMus);

            obj.put("musician",musician);
            response.put(obj);
        }
        
        return response.toString();
    }

    @GetMapping("/post/following")
    public String getMyPosts(){

        List<Post> posts = postRepository.findAllByOrderByIdDesc();

        JSONArray response = new JSONArray();

        for(int i = 0; i < posts.size();i++){

            //TODO: implement following list
            if(posts.get(i).getMusician().getId() == 0){

            }

            JSONObject obj = new JSONObject();
            obj.put("content", posts.get(i).getContent());
            obj.put("id", posts.get(i).getId());
            

            JSONObject image = new JSONObject();
            image.put("id",posts.get(i).getImage().getId());
            image.put("url",posts.get(i).getImage().getUrl());
            
            obj.put("image",image);
            JSONObject musician = new JSONObject();
            musician.put("id", posts.get(i).getMusician().getId());
            musician.put("name", posts.get(i).getMusician().getName());
            musician.put("tagline", posts.get(i).getMusician().getTagline());
            musician.put("website", posts.get(i).getMusician().getWebsite());
            musician.put("bio", posts.get(i).getMusician().getBio());
            musician.put("pronouns", posts.get(i).getMusician().getPronouns());

            JSONObject banner = new JSONObject();
            banner.put("id",posts.get(i).getMusician().getBanner().getId());
            banner.put("url",posts.get(i).getMusician().getBanner().getUrl());
            musician.put("banner",banner);

            JSONObject imageMus = new JSONObject();
            imageMus.put("id", posts.get(i).getMusician().getImage().getId());
            imageMus.put("url", posts.get(i).getMusician().getImage().getUrl());
            musician.put("image",imageMus);

            obj.put("musician",musician);
            response.put(obj);
        }
        
        return response.toString();
    }

    @GetMapping("/post/musician/{id}")
    public String getPostByMusician(
        @PathVariable Long id
    ){
        Musician m = musicianRepository.getOne(id);
        List<Post> posts = postRepository.findAllByMusicianOrderByIdDesc(m);

        JSONArray response = new JSONArray();

        for(int i = 0; i < posts.size();i++){

            JSONObject obj = new JSONObject();
            obj.put("content", posts.get(i).getContent());
            obj.put("id", posts.get(i).getId());
            

            JSONObject image = new JSONObject();
            image.put("id",posts.get(i).getImage().getId());
            image.put("url",posts.get(i).getImage().getUrl());
            
            obj.put("image",image);
            JSONObject musician = new JSONObject();
            musician.put("id", posts.get(i).getMusician().getId());
            musician.put("name", posts.get(i).getMusician().getName());
            musician.put("tagline", posts.get(i).getMusician().getTagline());
            musician.put("website", posts.get(i).getMusician().getWebsite());
            musician.put("bio", posts.get(i).getMusician().getBio());
            musician.put("pronouns", posts.get(i).getMusician().getPronouns());

            JSONObject banner = new JSONObject();
            banner.put("id",posts.get(i).getMusician().getBanner().getId());
            banner.put("url",posts.get(i).getMusician().getBanner().getUrl());
            musician.put("banner",banner);

            JSONObject imageMus = new JSONObject();
            imageMus.put("id", posts.get(i).getMusician().getImage().getId());
            imageMus.put("url", posts.get(i).getMusician().getImage().getUrl());
            musician.put("image",imageMus);

            obj.put("musician",musician);
            response.put(obj);
        }
        
        return response.toString();
    }

    @GetMapping("/post/musician/{id}")
    public String getPost(
        @PathVariable Long id
    ){
        Post post = postRepository.getOne(id);

        JSONObject obj = new JSONObject();

        obj.put("content", post.getContent());
        obj.put("id", post.getId());
        
        JSONObject image = new JSONObject();
        image.put("id", post.getImage().getId());
        image.put("url", post.getImage().getUrl());
        
        obj.put("image",image);
        JSONObject musician = new JSONObject();
        musician.put("id", post.getMusician().getId());
        musician.put("name", post.getMusician().getName());
        musician.put("tagline", post.getMusician().getTagline());
        musician.put("website", post.getMusician().getWebsite());
        musician.put("bio", post.getMusician().getBio());
        musician.put("pronouns", post.getMusician().getPronouns());

        JSONObject banner = new JSONObject();
        banner.put("id",post.getMusician().getBanner().getId());
        banner.put("url",post.getMusician().getBanner().getUrl());
        musician.put("banner",banner);

        JSONObject imageMus = new JSONObject();
        imageMus.put("id", post.getMusician().getImage().getId());
        imageMus.put("url", post.getMusician().getImage().getUrl());
        musician.put("image",imageMus);

        obj.put("musician",musician);

        return obj.toString();
    }

    @GetMapping("/musician/all")
    public String getAllMusicians(){
        List<Musician> m = musicianRepository.findAll();

        JSONArray response = new JSONArray();

        for(int i = 0; i < m.size();i++){
            
            JSONObject musician = new JSONObject();
            musician.put("id", m.get(i).getId());
            musician.put("name", m.get(i).getName());
            musician.put("tagline", m.get(i).getTagline());
            musician.put("website", m.get(i).getWebsite());
            musician.put("bio", m.get(i).getBio());
            musician.put("pronouns", m.get(i).getPronouns());
    
            JSONObject banner = new JSONObject();
            banner.put("id",m.get(i).getBanner().getId());
            banner.put("url",m.get(i).getBanner().getUrl());
            musician.put("banner",banner);
    
            JSONObject imageMus = new JSONObject();
            imageMus.put("id", m.get(i).getImage().getId());
            imageMus.put("url", m.get(i).getImage().getUrl());
            musician.put("image",imageMus);
    
            response.put(musician);
            
        }
        

        return response.toString();
    }

    @GetMapping("/musician/{id}")
    public String getMusicians(
        @PathVariable Long id
    ){
        Musician m = musicianRepository.getOne(id);

        JSONObject musician = new JSONObject();
        musician.put("id", m.getId());
        musician.put("name", m.getName());
        musician.put("tagline", m.getTagline());
        musician.put("website", m.getWebsite());
        musician.put("bio", m.getBio());
        musician.put("pronouns", m.getPronouns());

        JSONObject banner = new JSONObject();
        banner.put("id",m.getBanner().getId());
        banner.put("url",m.getBanner().getUrl());
        musician.put("banner",banner);

        JSONObject imageMus = new JSONObject();
        imageMus.put("id", m.getImage().getId());
        imageMus.put("url", m.getImage().getUrl());
        musician.put("image",imageMus);
    
        return musician.toString();
    }

    
}
