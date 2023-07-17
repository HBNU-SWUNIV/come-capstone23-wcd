<template>
  <div class="home">
    <img id="home_img" src="../assets/home.png" alt="Logo" />
  </div>
</template>

<script>
import MainScreen from '@/components/MainScreen.vue'

export default {
  name: 'PageHome',
  components: {
    MainScreen,
  }
}
</script>

<style>
#home_img {
  margin-left:20px;
  width:1450px;
  height:950px;
}
</style>