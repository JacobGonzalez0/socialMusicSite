<template>
  <div class="register">
    <div class="container">

      <div class="col-12">
       <form class="m-4" @submit.prevent="postSong">
          <input class="form-control" v-model="song.title" type="text" placeholder="Title" />
          <input class="form-control" v-model="song.album" type="text" placeholder="Album" />
          <textarea v-model="song.description" class="form-control" rows="10"></textarea>
          <input class="form-control" type="file" name="image" id="image">
          <input class="form-control" type="file" name="song" id="song">
          <input type="submit" value="Post" class="btn btn-primary m-2">
        </form>
        
      </div>
      
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { authHeader } from '../helpers/auth-header';

export default {
  name: 'UploadSong',
  components: {
    
  },
  data() {
        return {
        song: {
            title: "",
            album: "",
            description: "",
            track: 1
        },
        message: false
        };
    },
  methods: {

    

    async postSong(){
      let image = document.getElementById("image");
      let song = document.getElementById("song")

      let data = new FormData();
      data.set("title", this.song.title);
      data.set("album", this.song.album);
      data.set("description", this.song.description);
      data.set("track", this.song.track);
      data.set("image", image.files[0]);
      data.set("song", song.files[0]);
      axios.post("http://localhost:8080/song/create", data , authHeader()).then( res =>{
        
        if(res.data.error){
          this.$toast.error(res.data.error, {
            position: 'bottom'
          })
        }

        if(res.data.message){
          this.$toast.success(res.data.message, {
              position: 'bottom'
            })
          this.$router.push('/')
        }
        
      })
    }
  }
}
</script>

<style scoped>
textarea{
  resize: none;
}
</style>