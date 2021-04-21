<template>
  <div class="register">
    <div class="container">

      <div class="col-12">
       <form class="m-4" @submit.prevent="post">
          <input class="form-control" v-model="musician.name" type="text" placeholder="Name" />
          <input class="form-control" v-model="musician.tagline" type="text" placeholder="Tagline" />
          <input class="form-control" v-model="musician.website" type="text" placeholder="Website" />
          <input class="form-control" v-model="musician.pronouns" type="text" placeholder="Pronouns" />
          <textarea v-model="musician.bio"  class="form-control" rows="10"></textarea>
          <input class="form-control" type="file" name="image" id="image">
          <input class="form-control" type="file" name="banner" id="banner">
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
  name: 'RegisterMusician',
  components: {
    
  },
  data() {
        return {
        musician: {
            name: "",
            tagline: "",
            website: "",
            pronouns: "",
            bio: ""
        },
        message: false
        };
    },
  methods: {

    

    async post(){
      let image = document.getElementById("image");
      let banner = document.getElementById("banner")

      let data = new FormData();
      data.set("name", this.musician.name);
      data.set("tagline", this.musician.tagline);
      data.set("website", this.musician.website);
      data.set("pronouns", this.musician.pronouns);
      data.set("bio", this.musician.bio);
      data.set("image", image.files[0]);
      data.set("banner", banner.files[0]);
      axios.post("http://localhost:8080/musician/create", data , authHeader()).then( res =>{

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