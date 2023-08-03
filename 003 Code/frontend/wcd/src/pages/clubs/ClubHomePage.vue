<template>
  <div v-if="$store.state.isLogin">
    <div class="ClubProfileArea d-flex flex-row">
      <img
          class="ClubProfileImg ml-5 mt-5"
          src="https://demo.ycart.kr/shopboth_farm_max5_001/data/editor/1612/cd2f39a0598c81712450b871c218164f_1482469221_493.jpg"
      />
      <div class="ClubInfoArea d-flex flex-column ml-4 mt-5">
        <label style="font-size: 45px; margin-top: 10px">
          {{ clubs.clubName }}
        </label>
        <label style="font-size: 25px; margin-top: 5px">
          멤버 : 1 / {{ clubs.maximumPeople }}
        </label>
        <label style="font-size: 20px; margin-top: 10px">
          {{ clubs.description }}
        </label>
        <label style="font-size: 20px; color:rgb(133, 255, 133)">
          {{ clubs.approvalMethod === 'FREE' ? '자동 가입 가능합니다.' : '관리자의 승인 후 가입 가능합니다.' }}
        </label>
      </div>
    </div>
    <img
        class="VirtualView ml-5"
        src="https://image.news1.kr/system/photos/2021/11/30/5097050/article.gif"
        @click="goVirtualPage($route.params.id)"
    />
  </div>
</template>
  
<script>
import router from "@/router/index";
import { ref } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";

export default {
  components: {},
  setup() {
    const route = useRoute();
    let clubs = ref([]);
    const currentClubId = route.params.id;

    const goVirtualPage = (id) => {
      router.push({
        name: "ClubVirtualPage",
        params: {
          id: id,
        },
      });
    };

    // JWT 토큰 가져오기
    const access_token = localStorage.getItem('access_token');

    // JWT 토큰이 존재하는 경우에만 헤더 설정
    if (access_token) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${access_token}`;
    }

    const fetchData = async () => {
      await axios.get(`http://localhost:8000/club-service/clubs/${currentClubId}`)
          .then((res) => {
            clubs.value = res.data;
          });
    };

    fetchData();

    return {
      clubs,
      goVirtualPage,
    };
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