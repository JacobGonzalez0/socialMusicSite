<template>
  <div class="home">
    <div class="container ">

      <div class="row justify-content-center">

        <div class="col-12 col-md-8 col-lg-6">

          <div class="col-12">
            <form class="my-4" @submit.prevent="sendPost">
              <textarea class="form-control" v-model="post.content" rows="4"></textarea>
              <input type="file" name="image" id="image">
              <input type="submit" value="Post" class="btn btn-primary m-2">
            </form>
          </div>

          <div class="col-12 " v-for="(post, index) in posts" :key="index">
            
            <div class="col-12 border p-2 my-2">

              <div class="d-flex justify-content-between">
                <div class="d-flex">
                  <img class="post-profile-image" :src="$hostname + post.musician.image.url" alt="">
                  <div class="px-2">
                      {{post.musician.name}}
                  </div>
                </div>
                
                <div>
                  <button class="btn btn-danger" @click="deletePost(post.id)" >Delete</button>
                </div>
              </div>

              <div class="d-flex py-2">
                <img class="w-100" :src="$hostname + post.image.url" alt="">
              </div>

              <div class="d-flex py-2">
                <div class="align-items-start">
                  {{post.content}}
                </div>
              </div>

            </div>
          
          </div>

        </div>

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
      },
      posts: []
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
            this.getPosts()
          }

        }).catch( err =>{
          console.log(err)
          this.$toast.error(err, {
            position: 'bottom'
          })
        })
      },
    async getPosts(){
      axios.get("http://localhost:8080/post/all", authHeader()).then(res =>{
        this.posts = res.data
      }).catch( err =>{
        this.$toast.error(err, {
          position: 'bottom'
        })
      })
    },
    async deletePost(id){
      axios.delete("http://localhost:8080/post/remove/" + id , authHeader()).then(res =>{

        if(res.data.error){
          this.$toast.error(res.data.error, {
            position: 'bottom'
          })
        }

        if(res.data.message){
          this.$toast.success(res.data.message, {
              position: 'bottom'
            })
          this.getPosts()
        }

      }).catch( err =>{
        this.$toast.error(err, {
          position: 'bottom'
        })
      })
    }
      
  },
  beforeMount: function(){
    //redirect if user is not logged in
    if(!localStorage.getItem("user")){
      this.$router.push('/login');
    }
  },
  mounted: function(){
    
    this.getPosts()
  }
}
</script>

<style scoped>
textarea{
  resize: none;
}
.post-profile-image{
  width:4rem
}
</style>