package com.music.social.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.music.social.utils.ImageUtil;
import com.music.social.utils.SongUtil;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PostController {

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

    /**
     * 
     * @param newPost Accepts the params from the model in formData()
     * @param bindingResult Automatically checks for errors using our model params
     * @return Returns message field if no error, or error field if error.
     * @throws Exception
     */
    @PostMapping("/post/create")
    public String createPost(
        @RequestParam(name = "image") MultipartFile uploadedImage,
        @RequestParam(name = "content") String content,
        HttpServletRequest request
    )throws Exception{


        User user = userServices.getCurrentUser(request);
        Musician musician = musicianRepository.findByUser(user);
        Post post = new Post();
        post.setMusician(musician);
        post.setContent(content);

        postRepository.save(post);
        Image image = ImageUtil.uploadImage(uploadedImage, post);
        post.setImage(image);
        postRepository.save(post);

        JSONObject response = new JSONObject();
        response.put("message", "Created Post!");
        return response.toString();
    }

    @PostMapping("/post/edit/{id}")
    public String editPost(
        @RequestParam(name = "image") MultipartFile uploadedImage,
        @RequestParam(name = "content") String content,
        @PathVariable Long id,
        HttpServletRequest request
    )throws Exception{


        User user = userServices.getCurrentUser(request);
        Musician musician = musicianRepository.findByUser(user);
        Post post = postRepository.getOne(id);
        if(post.getMusician().equals(musician)){
            post.setContent(content);
            postRepository.save(post);
            if(!uploadedImage.isEmpty()){
                Image image = ImageUtil.uploadImage(uploadedImage, post);
                post.setImage(image);
            }
            postRepository.save(post);
            

            JSONObject response = new JSONObject();
            response.put("message", "Edited Post!");
            return response.toString();
        }
        

        JSONObject response = new JSONObject();
        response.put("message", "You do not own the post!");
        return response.toString();
    }

    @PostMapping("/post/remove/{id}")
    public String removePost(
        @PathVariable Long id,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        Musician musician = musicianRepository.findByUser(user);
        Post post = postRepository.getOne(id);
        //check if musician owns the post
        if(post.getMusician().equals(musician) || userServices.userIsRole(user, "admin")){

            List<Like> likes = likeRepository.findAllByPost(post);
            likes.forEach( like ->{
                likeRepository.delete(like);
            });
            Image image = imageRepository.getOne(post.getImage().getId());
            imageRepository.delete(image);
            postRepository.delete(post);
            
            JSONObject response = new JSONObject();
            response.put("message", "Post Deleted");
            return response.toString();

        }

        JSONObject response = new JSONObject();
        response.put("message", "You do not own the post!");
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
                song.setArtist(musician);
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

    @PostMapping("/song/edit/{id}")
    public String editSong(
        @RequestParam(name = "title") String title,
        @RequestParam(name = "album") String album,
        @RequestParam(name = "description") String description,
        @RequestParam(name = "track") Long track,
        @RequestParam(name = "image") MultipartFile uploadedImage,
        @PathVariable Long id,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        Musician musician = musicianRepository.findByUser(user);
        Song song = songRepository.getOne(id);

        if(song.getArtist().equals(musician)){

            try{

                if(!uploadedImage.isEmpty()){

                    Image image = ImageUtil.uploadImage(uploadedImage, song);
                    song.setImage(image);

                }
                
                song.setTitle(title);
                song.setAlbum(album);
                song.setDescription(description);
                song.setTrack(track);
                songRepository.save(song);

                JSONObject response = new JSONObject();
                response.put("message", "Song Updated!");

                return response.toString();

            }catch(Exception e){
                JSONObject response = new JSONObject();
                response.put("error", "Edit failed!");
                return response.toString();
            }
            
        }

        JSONObject response = new JSONObject();
        response.put("error", "You do not own the song!");
        return response.toString();

        
    }

    @PostMapping("/song/remove/{id}")
    public String removeSong(
        @PathVariable Long id,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        Musician musician = musicianRepository.findByUser(user);
        Song song = songRepository.getOne(id);

        if(song == null){
            JSONObject response = new JSONObject();
            response.put("error", "Song does not exist");
            return response.toString();
        }
        //check if musician owns the post
        if(song.getArtist().equals(musician) || userServices.userIsRole(user, "admin")){

            List<Like> likes = likeRepository.findAllBySong(song);
            likes.forEach( like ->{
                likeRepository.delete(like);
            });
            Image image = imageRepository.getOne(song.getImage().getId());
            imageRepository.delete(image);
            songRepository.delete(song);
            
            JSONObject response = new JSONObject();
            response.put("message", "Song Deleted");
            return response.toString();

        }

        JSONObject response = new JSONObject();
        response.put("message", "You do not own the song!");
        return response.toString();
    }

    @PostMapping("/musician/create")
    public String createMusician(
        @RequestParam(name = "image") MultipartFile uploadedImage,
        @RequestParam(name = "banner") MultipartFile uploadedBanner,
        @RequestParam(name = "tagline") String tagline,
        @RequestParam(name = "website") String website,
        @RequestParam(name = "bio") String bio,
        @RequestParam(name = "pronouns") String pronouns,
        @RequestParam(name = "name") String name,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        Musician checkMusician = musicianRepository.findByUser(user);

        if(checkMusician != null){
            JSONObject response = new JSONObject();
            response.put("error", "Musician already exists under current user");
            return response.toString();
        }
        Musician musician = new Musician();
        musician.setBio(bio);
        musician.setName(name);
        musician.setPronouns(pronouns);
        musician.setTagline(tagline);
        musician.setWebsite(website);
        musician.setUser(user);

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

    @PostMapping("/musician/edit/{id}")
    public String editMusician(
        @RequestParam(name = "image") MultipartFile uploadedImage,
        @RequestParam(name = "banner") MultipartFile uploadedBanner,
        @RequestParam(name = "tagline") String tagline,
        @RequestParam(name = "website") String website,
        @RequestParam(name = "bio") String bio,
        @RequestParam(name = "pronouns") String pronouns,
        @RequestParam(name = "name") String name,
        @PathVariable Long id,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        Musician musician = musicianRepository.getOne(id);

        if(userServices.userIsRole(user, "admin")){

            musician.setBio(bio);
            musician.setName(name);
            musician.setPronouns(pronouns);
            musician.setTagline(tagline);
            musician.setWebsite(website);
    
            musicianRepository.save(musician);
            if(!uploadedImage.isEmpty()){

                Image image = ImageUtil.uploadImage(uploadedImage, musician);
                musician.setImage(image);
            }
    
            if(!uploadedBanner.isEmpty()){
                Image banner = ImageUtil.uploadImage(uploadedBanner, musician);
                musician.setBanner(banner);
            }
            musicianRepository.save(musician);
    
            JSONObject response = new JSONObject();
            response.put("message", "Musician Edited!");
            return response.toString();
        }

        JSONObject response = new JSONObject();
        response.put("error", "You are not an admin!");
        return response.toString();
        
    }

    @PostMapping("/musician/edit/")
    public String editMusicianSelf(
        @RequestParam(name = "image") MultipartFile uploadedImage,
        @RequestParam(name = "banner") MultipartFile uploadedBanner,
        @RequestParam(name = "tagline") String tagline,
        @RequestParam(name = "website") String website,
        @RequestParam(name = "bio") String bio,
        @RequestParam(name = "pronouns") String pronouns,
        @RequestParam(name = "name") String name,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        Musician musician = musicianRepository.findByUser(user);

        if(musician == null){
            JSONObject response = new JSONObject();
            response.put("error", "Youre not a musician! :( sorry");
            return response.toString();
        }

        musician.setBio(bio);
        musician.setName(name);
        musician.setPronouns(pronouns);
        musician.setTagline(tagline);
        musician.setWebsite(website);

        musicianRepository.save(musician);
        if(!uploadedImage.isEmpty()){

            Image image = ImageUtil.uploadImage(uploadedImage, musician);
            musician.setImage(image);
        }

        if(!uploadedBanner.isEmpty()){
            Image banner = ImageUtil.uploadImage(uploadedBanner, musician);
            musician.setBanner(banner);
        }
        musicianRepository.save(musician);

        JSONObject response = new JSONObject();
        response.put("message", "Profile Edited!");
        return response.toString();
        
        
    }

    @PostMapping("/musician/remove/{id}")
    public String removeMusician(
        @PathVariable Long id,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        
        //check if admin
        if(userServices.userIsRole(user, "admin")){

            Musician musician = musicianRepository.getOne(id);
            musicianRepository.delete(musician);

            JSONObject response = new JSONObject();
            response.put("message", "Musician Deleted");
            return response.toString();

        }

        JSONObject response = new JSONObject();
        response.put("message", "You are not an admin!");
        return response.toString();
    }
    
    @PostMapping("/user/edit/{id}")
    public String editUser(
        @RequestParam(name = "email") String email,
        @RequestParam(name = "username") String username,
        @PathVariable Long id,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        User userEdit = userRepository.getOne(id);

        if(userServices.userIsRole(user, "admin")){

            userEdit.setEmail(email);
            userEdit.setUsername(username);

            JSONObject response = new JSONObject();
            response.put("message", "User Edited!");
            return response.toString();
        }

        JSONObject response = new JSONObject();
        response.put("error", "You are not an admin!");
        return response.toString();
        
    }

    @PostMapping("/user/delete")
    public String deleteUser(
        @PathVariable Long id,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);
        User userEdit = userRepository.getOne(id);

        if(userServices.userIsRole(user, "admin") || user.equals(userEdit)){

            userRepository.delete(userEdit);

            JSONObject response = new JSONObject();
            response.put("message", "User Deleted!");
            return response.toString();
        }

        JSONObject response = new JSONObject();
        response.put("error", "You are not an admin!");
        return response.toString();
        
    }

    @PostMapping("/user/edit/")
    public String editUserSelf(
        @RequestParam(name = "email") String email,
        @RequestParam(name = "username") String username,
        @RequestParam(name = "password") String password,
        HttpServletRequest request
    )throws Exception{

        User user = userServices.getCurrentUser(request);

        user.setEmail(email);
        user.setUsername(username);

        if(!password.isEmpty()){
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(11);
            String hash = bcrypt.encode(password);
            user.setPassword(password);
        }
        

        JSONObject response = new JSONObject();
        response.put("message", "User Edited!");
        return response.toString();
        
    }



    
}
