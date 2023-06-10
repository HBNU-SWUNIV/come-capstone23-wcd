<template>
  <div id="left_screen">
    <div id="app_name">W &nbsp; C &nbsp; D</div>
    <img id="user_profile_img" src="../assets/basicProfile.jpeg" alt="Logo" />
    <button id="club_create_btn" @click="goCreate">생성</button>
    <button id="club_find_btn" @click="goSearch">검색</button>
  </div>
</template>

<script>
export default {
  methods: {
    goCreate() {
      this.$router.push({
        path: "/clubCreate",
      });
    },
    goSearch() {
      this.$router.push({
        path: "/clubSearch",
      });
    },
  }
}
</script>


<style scoped>
#left_screen {
  position: absolute;
  width: 120px;
  height: 1080px;
  left: 0px;
  top: 0px;
  background-color: #383838;
}

#app_name {
  width: 60px;
  height: 30px;
  text-align: center;
  color: white;
  font-size: 14px;
  margin-left: 30px;
  margin-top: 15px;
}

#user_profile_img {
  max-width: 70px;
  max-height: 70px;
  margin-left: 25px;
  margin-top: 5px;
  border-radius: 20%;
}

#club_create_btn {
  position: absolute;
  width: 70px;
  height: 70px;
  left: 25px;
  top: 880px;
  background: #d9d9d9;
  border-radius: 30px;
  font-size:25px;
}
#club_find_btn {
  position: absolute;
  width: 70px;
  height: 70px;
  left: 25px;
  top: 960px;
  background: #d9d9d9;
  border-radius: 30px;
  font-size:25px;
}
</style>
