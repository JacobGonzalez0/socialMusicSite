package com.music.social.controllers;

import javax.validation.Valid;

import com.music.social.models.Musician;
import com.music.social.models.Post;
import com.music.social.models.User;
import com.music.social.repositories.MusicianRepository;
import com.music.social.repositories.PostRepository;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    MusicianRepository musicianRepository;


    /**
     * 
     * @param newPost Accepts the params from the model in formData()
     * @param bindingResult Automatically checks for errors using our model params
     * @return Returns message field if no error, or error field if error.
     * @throws JSONException
     */
    @PostMapping("/post/create")
    public String createPost(
        @Valid Post newPost,
        BindingResult bindingResult
    )throws JSONException{

        if(bindingResult.hasErrors()){
            //create the response, get the first error and return it 
            JSONObject response = new JSONObject();
            response.put("error", bindingResult.getAllErrors().get(0).toString());
            return response.toString();
        }

        //TODO accept image

        // TODO get user, get the musician assoicated with them, and save to new post
        // User user = null;
        // Musician musician = musicianRepository.findByUser(user);
        // newPost.setMusician(musician);

        postRepository.save(newPost);

        JSONObject response = new JSONObject();
        response.put("message", "Created Post!");

        return response.toString();
    }



    
}
