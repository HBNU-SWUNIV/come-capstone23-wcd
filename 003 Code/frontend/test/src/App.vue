<template>
  <div id="full_screen">

    <div id="left_screen">
      <LeftScreen/>
    </div>

    <div id="center_screen">
      <NavScreen/>
      <MainScreen/>
    </div>

    <div id="right_screen">
      <RightScreen/>
    </div>

  </div>
</template>

<script>
import LeftScreen from '@/components/LeftScreen.vue'
import NavScreen from '@/components/NavScreen.vue'
import MainScreen from '@/components/MainScreen.vue'
import RightScreen from '@/components/RightScreen.vue'

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
#full_screen{
  height: 1080px;
  width: 1920px;
}

#left_screen{
  height: 1080px;
  width: 120px;
  background-color: #383838;
  float: left;
}

#center_screen{
  height: 1080px;
  width: 1500px;
  float: left;
}

#right_screen{
  height: 1080px;
  width: 300px;
  background-color: #383838;
  float: left;
}
</style>