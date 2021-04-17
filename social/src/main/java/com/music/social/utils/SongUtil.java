package com.music.social.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.time.YearMonth;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.music.social.models.Musician;
import com.music.social.models.Song;
import com.music.social.repositories.SongRepository;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SongUtil {
    
    private static SongRepository songDao;
    private static String UPLOAD_PATH;

    @Autowired
    private SongRepository dao0;

    @Value("${song-upload-path}")
    private String uploadPath;
  
    @PostConstruct     
    private void initStaticDao () {
        songDao = this.dao0;
        UPLOAD_PATH = this.uploadPath;
    } 

    public static Song uploadSong(MultipartFile uploadedSong, Musician artist) throws Exception{

        String mime = uploadedSong.getContentType();
        String ext = FilenameUtils.getExtension(uploadedSong.getOriginalFilename());
        String dir = getYearAndMonthUrlFragment();
        String filename = RandomStringUtils.random(7, true, true) + "." + ext;
        Path path = Paths.get(UPLOAD_PATH + dir);

        //check if image is actually image;
        if( mime == "audio/aac"||
            mime == "audio/mpeg"||
            mime == "audio/ogg"||
            mime == "audio/webm"  ){

                //get the audio files length 
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(uploadedSong.getInputStream());
                AudioFormat format = audioInputStream.getFormat();
                long frames = audioInputStream.getFrameLength();
                Double durationInSeconds = (frames+0.0) / format.getFrameRate();  
                String length = durationInSeconds.toString();

                if(Files.notExists(path)){
                    try{
                        Files.createDirectories(path);
                    }catch(IOException e){
                        e.printStackTrace();
                        return null;
                    }
                }
                
                try {
                    uploadedSong.transferTo(Paths.get(UPLOAD_PATH + dir + filename));
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
        
                Song song = new Song(
                    UPLOAD_PATH + dir + filename,
                    length,
                    artist
                );
                
                //save and return the image to be attached to the user
                songDao.save(song);

                return song;
        }else{
            throw new Exception("Image type not valid");
        }

    }

    private static String getYearAndMonthUrlFragment() {
        StringBuilder sb = new StringBuilder();
        sb.append(Year.now().getValue());
        sb.append("/");
        sb.append(YearMonth.now().getMonthValue());
        sb.append("/");
    
        return sb.toString();
	}

    public static String randomString() {
        //the string is 7 characters long
        byte[] array = new byte[7]; 
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
    
        return generatedString;
    }
}
