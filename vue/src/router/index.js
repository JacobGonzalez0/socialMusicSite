import Vue from 'vue'
import VueRouter from 'vue-router'
import Feed from '../views/Feed.vue'
import Login from '../views/Login.vue'
import RegisterMusician from '../views/RegisterMusician.vue'
import RegisterUser from '../views/RegisterUser.vue'
import UploadSong from '../views/UploadSong.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Feed',
    component: Feed
  },
  {
    path: '/musician',
    name: 'RegisterMusician',
    component: RegisterMusician
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterUser
  },
  {
    path: '/login',
    name: "Login",
    component: Login
  },
  {
    path: '/song/upload',
    name: "Upload Song",
    component: UploadSong
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
