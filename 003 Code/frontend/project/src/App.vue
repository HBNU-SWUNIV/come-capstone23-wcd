<template>

  <LeftScreen/>
  <NavScreen/>
  <MainScreen/>
  <div id="main_screen" class="d-flex flex-column justify-content-center">
    <router-view/>
  </div>
  <RightScreen/>

</template>

<script>
import LeftScreen from '@/components/LeftScreen'
import NavScreen from '@/components/NavScreen'
import MainScreen from '@/components/MainScreen'
import RightScreen from '@/components/RightScreen'

export default {
  name: 'App',
  components: {
    LeftScreen,
    NavScreen,
    MainScreen,
    RightScreen,
  }
}
</script>

<style>
#app{
  position: relative;
  width: 1920px;
  height: 1080px;
  background-color: #383838;
}

#main_screen{
  position: absolute;
  width: 1500px;
  height: 1000px;
  left: 120px;
  top: 80px;
  background: #232324;
  border-radius: 15px 0px 0px 0px;
}
</style>

<!-- json-server --watch db.json -->