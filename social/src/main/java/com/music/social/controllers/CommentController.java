package com.music.social.controllers;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.music.social.models.Comment;
import com.music.social.models.Musician;
import com.music.social.models.Post;
import com.music.social.models.Song;
import com.music.social.models.User;
import com.music.social.repositories.CommentRepository;
import com.music.social.repositories.ImageRepository;
import com.music.social.repositories.LikeRepository;
import com.music.social.repositories.MusicianRepository;
import com.music.social.repositories.PostRepository;
import com.music.social.repositories.SongRepository;
import com.music.social.services.UserServices;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CommentController {

    @Autowired
    PostRepository postRepository;

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

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/comment/post/add/{id}")
    public String commentPost(
        @PathVariable Long id,
        @RequestParam(name = "content") String content,
        HttpServletRequest request
    )throws Exception{

        if(content.isEmpty()){
            JSONObject response = new JSONObject();
            response.put("error", "comment Empty!");
            return response.toString();
        }

        Post post = postRepository.getOne(id);

        if(post == null){
            JSONObject response = new JSONObject();
            response.put("error", "Post does not exist!");
            return response.toString();
        }

        User user = userServices.getCurrentUser(request);
        Musician musician = musicianRepository.findByUser(user); 
           
        Comment comment = new Comment(content, post);

        if(musician == null){
            comment.setUser(user);
        }else{
            comment.setMusician(musician);
        }
        commentRepository.save(comment);

        JSONObject response = new JSONObject();
        response.put("message", "Comment Posted!");
        return response.toString();
    }


    @PostMapping("/comment/delete/{id}")
    public String commentDeletePost(
        @PathVariable Long id,
        @RequestParam(name = "content") String content,
        HttpServletRequest request
    )throws Exception{

        if(content.isEmpty()){
            JSONObject response = new JSONObject();
            response.put("error", "comment Empty!");
            return response.toString();
        }

        Comment comment = commentRepository.getOne(id);
        if(comment == null){
            JSONObject response = new JSONObject();
            response.put("error", "comment doesnt exist!");
            return response.toString();
        }

        User user = userServices.getCurrentUser(request);
        Musician musician = musicianRepository.findByUser(user); 
        
        if(musician == null){
            if(userServices.userIsRole(user, "admin")){

                commentRepository.delete(comment);
                JSONObject response = new JSONObject();
                response.put("message", "Comment Deleted!");
                return response.toString();

            }
        }else{
            if(musician.equals(comment.getMusician())){

                commentRepository.delete(comment);
                JSONObject response = new JSONObject();
                response.put("message", "Comment Deleted!");
                return response.toString();

            }
        }
        
        JSONObject response = new JSONObject();
        response.put("error", "You do not have the permissions!");
        return response.toString();
        
    }

    @PostMapping("/comment/song/add/{id}")
    public String commentSong(
        @PathVariable Long id,
        @RequestParam(name = "content") String content,
        HttpServletRequest request
    )throws Exception{

        if(content.isEmpty()){
            JSONObject response = new JSONObject();
            response.put("error", "comment Empty!");
            return response.toString();
        }

        Song song = songRepository.getOne(id);

        if(song == null){
            JSONObject response = new JSONObject();
            response.put("error", "Song does not exist!");
            return response.toString();
        }
        
        User user = userServices.getCurrentUser(request);
        Musician musician = musicianRepository.findByUser(user); 

        Comment comment = new Comment(content, song);

        if(musician == null){
            comment.setUser(user);
        }else{
            comment.setMusician(musician);
        }
        commentRepository.save(comment);

        JSONObject response = new JSONObject();
        response.put("message", "Comment Posted!");
        return response.toString();
    }

    
}
