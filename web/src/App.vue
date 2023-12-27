<template>
  <NavBar/>
  <router-view/>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
import "bootstrap/dist/js/bootstrap.bundle.min"
import '../node_modules/element-plus/dist/index.css'

export default{
  components:{
    NavBar
  },
  setup(){
  }
}
</script>

<style>
body{
  background-image: url("@/assets/images/background.jpg");
  background-size: cover;
}
</style>