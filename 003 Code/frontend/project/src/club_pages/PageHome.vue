<template>
  <div v-if="$store.state.isLogin">
    <div class="ClubProfileArea d-flex flex-row">
      <img class="ClubProfileImg ml-5 mt-5" src="../assets/test2.jpg" />
      <div class="ClubInfoArea d-flex flex-column ml-4 mt-5">
        <label style="font-size: 45px; margin-top: 10px">test1</label>
        <label style="font-size: 25px; margin-top: 5px">멤버 : 99 / 100</label>
        <label style="font-size: 20px; margin-top: 10px"
          >안녕하세요. 음악을 좋아하는 사람들의 모임입니다.</label
        >
        <label style="font-size: 20px">관리자의 승인 후 가입가능합니다.</label>
      </div>
    </div>
    <img
      class="VirtualView ml-5"
      src="../assets/test4.jpg"
      @click="goVirtualPage"
    />
  </div>
</template>
  
<script>
import router from "../router/index";

export default {
  components: {},
  setup() {
    const goVirtualPage = () => {
      router.push({
        name:'VirtualPage'
      })
    };

    return{
      goVirtualPage,
    }
  },
};
</script>
  
  <style>
.ClubProfileImg {
  width: 400px;
  height: 225px;
  border-radius: 15px 15px 15px 15px;
}
.VirtualView {
  width: 1400px;
  height: 640px;
  border-radius: 15px 15px 15px 15px;
  margin-top: 40px;
  cursor: pointer;
}
</style>