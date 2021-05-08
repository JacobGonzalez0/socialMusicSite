<template>
  <div class="register">
    <div class="container">

      <div class="col-12">
       <form class="m-4" @submit.prevent="register">
          <input class="form-control" v-model="user.username" type="text" placeholder="Username" />
          <input class="form-control" v-model="user.email" type="text" placeholder="Email@example.com" />
          <input class="form-control" v-model="user.password" type="password" placeholder="Password" />
          <input class="form-control" v-model="user.confirmPassword" type="password" placeholder="Confirm Password" />
          <input type="submit" value="Register" class="btn btn-primary m-2">
        </form>
        
      </div>
      
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'RegisterUser',
  components: {
    
  },
  data() {
        return {
        user: {
            username: "",
            email: "",
            password: "",
            confirmPassword: ""
        },
        message: false
        };
    },
  methods: {

    

    async register(){ 

      if(this.user.password != this.user.confirmPassword){
        this.$toast.error("Passwords do not match!", {
          position: 'bottom'
        })
        throw(new Error("Passwords do not match!"))
      }

      let data = new FormData();
      data.set("username", this.user.username);
      data.set("email", this.user.email);
      data.set("password", this.user.password);

      axios.post("http://localhost:8080/register", data ).then( res =>{

        if(res.data.error){
          this.$toast.error(res.data.error, {
            position: 'bottom'
          })
        }

        if(res.data.message){
          this.$toast.success(res.data.message, {
              position: 'bottom'
            })
          this.$router.push('/login')
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