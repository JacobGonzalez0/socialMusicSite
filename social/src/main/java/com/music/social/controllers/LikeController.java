package com.music.social.controllers;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.music.social.models.Like;
import com.music.social.models.Post;
import com.music.social.models.Song;
import com.music.social.models.User;
import com.music.social.repositories.LikeRepository;
import com.music.social.repositories.MusicianRepository;
import com.music.social.repositories.PostRepository;
import com.music.social.repositories.SongRepository;
import com.music.social.services.UserServices;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LikeController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    MusicianRepository musicianRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserServices userServices;  

    @PostMapping("/like/post/add/{id}")
    public String addLikePost(
        @PathVariable Long id,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        Post post = postRepository.getOne(id);
        List<Like> checkLike = likeRepository.findAllByPost(post);

        for(int i = 0; i < checkLike.size();i++){
            if(checkLike.get(i).getUser().equals(user)){
                JSONObject response = new JSONObject();
                response.put("message", "Post already Liked!");
                return response.toString();
            }
        }

        Like like = new Like(user,post);
        likeRepository.save(like);

        JSONObject response = new JSONObject();
        response.put("message", "Liked Post!");
        return response.toString();
    }

    @PostMapping("/like/post/remove/{id}")
    public String removeLikePost(
        @PathVariable Long id,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        Post post = postRepository.getOne(id);
        List<Like> checkLike = likeRepository.findAllByPost(post);

        for(int i = 0; i < checkLike.size();i++){
            if(checkLike.get(i).getUser().equals(user)){
                likeRepository.delete(checkLike.get(i));

                JSONObject response = new JSONObject();
                response.put("message", "Like removed!");
                return response.toString();
            }
        }

        JSONObject response = new JSONObject();
        response.put("message", "Like doesnt exist!");
        return response.toString();
    }
    // Post liking


    @PostMapping("/like/song/add/{id}")
    public String addLikeSong(
        @PathVariable Long id,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        Song song = songRepository.getOne(id);
        List<Like> checkLike = likeRepository.findAllBySong(song);

        for(int i = 0; i < checkLike.size();i++){
            if(checkLike.get(i).getUser().equals(user)){
                JSONObject response = new JSONObject();
                response.put("message", "Song already Liked!");
                return response.toString();
            }
        }

        Like like = new Like(user,song);
        likeRepository.save(like);

        JSONObject response = new JSONObject();
        response.put("message", "Liked Song!");
        return response.toString();
    }

    @PostMapping("/like/song/remove/{id}")
    public String removeLikeSong(
        @PathVariable Long id,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        Song song = songRepository.getOne(id);
        List<Like> checkLike = likeRepository.findAllBySong(song);

        for(int i = 0; i < checkLike.size();i++){
            if(checkLike.get(i).getUser().equals(user)){
                likeRepository.delete(checkLike.get(i));

                JSONObject response = new JSONObject();
                response.put("message", "Like removed!");
                return response.toString();
            }
        }

        JSONObject response = new JSONObject();
        response.put("message", "Like doesnt exist!");
        return response.toString();
    }
    //Song likes

    
}
