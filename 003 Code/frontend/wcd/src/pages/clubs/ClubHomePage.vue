<template>
  <div v-if="$store.state.isLogin">
    <div class="ClubProfileArea d-flex flex-row">
      <img
        class="ClubProfileImg ml-5 mt-5"
        src="https://demo.ycart.kr/shopboth_farm_max5_001/data/editor/1612/cd2f39a0598c81712450b871c218164f_1482469221_493.jpg"
      />
      <div class="ClubInfoArea d-flex flex-column ml-4 mt-5">
        <label style="font-size: 45px; margin-top: 10px">
          <!-- {{ clubs.clubName }} -->
          모임 이름
        </label>
        <label style="font-size: 25px; margin-top: 5px"
          >멤버 : 1 /
          <!-- {{ clubs.maximumPeople }} -->
          100
        </label>
        <label style="font-size: 20px; margin-top: 10px">
          <!-- {{clubs.description}} -->
          모임 설명
        </label>
        <!-- <label style="font-size: 20px" v-if="clubs.approvalMethod === 'manual'"
          >관리자의 승인 후 가입가능합니다.</label
        >
        <label style="font-size: 20px" v-else>자동 가입가능합니다.</label> -->

        <label style="font-size: 20px; color:rgb(133, 255, 133)">관리자의 승인 후 가입가능합니다.</label>
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

    const fetchData = async () => {
      const response = await axios.get("http://localhost:3000/clubs");
      const clubsData = response.data.map((item) => {
        const parsedItem = JSON.parse(Object.keys(item)[0]);
        parsedItem.id = item.id;
        return parsedItem;
      });
      clubs.value = clubsData[currentClubId - 1];
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