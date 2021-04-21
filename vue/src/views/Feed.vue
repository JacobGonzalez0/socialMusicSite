<template>
  <div class="home">
    <div class="container">
      <div class="col-12">
        <form class="m-4" @submit.prevent="sendPost">
          <textarea class="form-control" v-model="post.content" rows="4"></textarea>
          <input type="file" name="image" id="image">
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
  name: 'Feed',
  components: {
    
  },
  data() {
    return {
      post: {
        content: ""
      }
    }
  },
  methods: {
    async sendPost(){
        let image = document.getElementById("image");
        let data = new FormData();
        data.set("image",image.files[0]);
        data.set("content", this.post.content);
        
        axios.post("http://localhost:8080/post/create", data, authHeader()).then( res =>{
          
          if(res.data.error){
            this.$toast.error(res.data.error, {
              position: 'bottom'
            })
          }

          if(res.data.message){
            this.$toast.success(res.data.message, {
                position: 'bottom'
              })
          }

        }).catch( err =>{
          console.log(err)
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