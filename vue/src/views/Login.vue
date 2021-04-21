<template>
    <div class="container">
      
      <div class="row">
        <div class="col-lg-6 offset-lg-3 col-sm-10 offset-sm-1">
          <form
            class="text-center border border-primary p-5"
            style="margin-top:70px;height:auto;padding-top:100px !important;"
            @submit.prevent="loginUser"
          >
            <input
              type="text"
              id="username"
              class="form-control mb-5"
              placeholder="username"
              v-model="login.username"
            />
            <!-- Password -->
            <input
              type="password"
              id="password"
              class="form-control mb-5"
              placeholder="Password"
              v-model="login.password"
            />
            <p>
              Dont have an account? Click
              <router-link to="/register"> here </router-link> to sign up
            </p>
            <!-- Sign in button -->
            <center>
              <button class="btn btn-primary btn-block w-75 my-4" type="submit">
                Sign in
              </button>
            </center>
          </form>
        </div>
      </div>
  </div>
    
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
        login: {
            username: "",
            password: ""
        },
        message: false
        };
    },
    methods: {
        async loginUser() {

            let data = new FormData()
            data.set("username", this.login.username)
            data.set("password", this.login.password)

            axios.post("http://127.0.0.1:8080/login", data ).then( response =>{
                
                if(response.status == 200){
                    let token = response.data.token;
                    if(token){
                        localStorage.setItem("user", token);
                        this.$toast.success('Logged in!', {
                            position: 'bottom'
                        })
                        this.$router.push('/') 
                    }else{
                        this.$toast.error('Something went wrong storing credientials', {
                            position: 'bottom'
                        })
                       
                    }
                }
            }).catch( ()=>{
                this.$toast.error('Credientials Failed', {
                    position: 'bottom'
                })
            })
        
        }
    }

}
</script>