<template>
  <div>
    <div>{{ name }}</div>
    <div>{{ age }}</div>
  </div>
  <router-view/>
</template>

<script>
import $ from 'jquery';
import {ref} from 'vue';

export default{
  name: "app",
  setup:() => {
    let name = ref("");
    let age = ref(""); 

    $.ajax({
      url:"http://localhost:520/record/getinfo/",
      success: resp => {
        name.value = resp.name;
        age.value = resp.age;
      }

    })

    return{
      name,
      age,
    }
  }
}
</script>

<style>
body{
  background-image: url("@/assets/images/background.jpg");
  background-size: cover;
}
</style>
