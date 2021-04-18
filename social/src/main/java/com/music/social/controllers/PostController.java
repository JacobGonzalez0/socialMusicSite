package com.music.social.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.music.social.models.Image;
import com.music.social.models.Musician;
import com.music.social.models.Post;
import com.music.social.models.Song;
import com.music.social.models.User;
import com.music.social.repositories.MusicianRepository;
import com.music.social.repositories.PostRepository;
import com.music.social.repositories.SongRepository;
import com.music.social.services.UserServices;
import com.music.social.utils.ImageUtil;
import com.music.social.utils.SongUtil;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    MusicianRepository musicianRepository;

    @Autowired
    UserServices userServices;  

    /**
     * 
     * @param newPost Accepts the params from the model in formData()
     * @param bindingResult Automatically checks for errors using our model params
     * @return Returns message field if no error, or error field if error.
     * @throws Exception
     */
    @PostMapping("/post/create")
    public String createPost(
        @Valid Post newPost,
        @RequestParam(name = "image") MultipartFile uploadedImage,
        BindingResult bindingResult,
        HttpServletRequest request
    )throws Exception{

        if(bindingResult.hasErrors()){
            //create the response, get the first error and return it 
            JSONObject response = new JSONObject();
            response.put("error", bindingResult.getAllErrors().get(0).toString());
            return response.toString();
        }

        User user = userServices.getCurrentUser(request);
        Musician musician = musicianRepository.findByUser(user);
        newPost.setMusician(musician);

        postRepository.save(newPost);
        Image image = ImageUtil.uploadImage(uploadedImage, newPost);
        newPost.setImage(image);
        postRepository.save(newPost);

        JSONObject response = new JSONObject();
        response.put("message", "Created Post!");

        return response.toString();
    }

    /**
     * 
     * @param title
     * @param album
     * @param description
     * @param track
     * @param uploadedSong
     * @param uploadedImage
     * @return
     * @throws Exception 
     */
    @PostMapping("/song/create")
    public String createSong(
        @RequestParam(name = "title") String title,
        @RequestParam(name = "album") String album,
        @RequestParam(name = "description") String description,
        @RequestParam(name = "track") Long track,
        @RequestParam(name = "song") MultipartFile uploadedSong,
        @RequestParam(name = "image") MultipartFile uploadedImage,
        HttpServletRequest request
    )throws Exception{

        if(uploadedSong.isEmpty()){
            JSONObject response = new JSONObject();
            response.put("error", "No song uploaded, please attach file");
            return response.toString();
        }

        User user = userServices.getCurrentUser(request);
        Musician musician = musicianRepository.findByUser(user);

        try{

            Song song = SongUtil.uploadSong(uploadedSong, musician);

            try{

                Image image = ImageUtil.uploadImage(uploadedImage, song);
                song.setImage(image);
                song.setTitle(title);
                song.setAlbum(album);
                song.setDescription(description);
                song.setTrack(track);
                songRepository.save(song);

                JSONObject response = new JSONObject();
                response.put("message", "Song Uploaded!");

                return response.toString();

            }catch(Exception e){
                JSONObject response = new JSONObject();
                response.put("error", "Image upload failed!");
                return response.toString();
            }



        }catch(Exception e){
            JSONObject response = new JSONObject();
            response.put("error", "Song upload failed!");
            return response.toString();
        }
        
    }

    @PostMapping("/musician/create")
    public String createMusician(
        @RequestParam(name = "image") MultipartFile uploadedImage,
        @RequestParam(name = "banner") MultipartFile uploadedBanner,
        Musician musician,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        Musician checkMusician = musicianRepository.findByUser(user);
        if(checkMusician != null){
            JSONObject response = new JSONObject();
            response.put("error", "Musician already exists under current user");
            return response.toString();
        }

        musicianRepository.save(musician);
        Image image = ImageUtil.uploadImage(uploadedImage, musician);
        Image banner = ImageUtil.uploadImage(uploadedBanner, musician);
        musician.setImage(image);
        musician.setBanner(banner);
        musicianRepository.save(musician);

        JSONObject response = new JSONObject();
        response.put("message", "Musician registered!");

        return response.toString();
    }



    
}
