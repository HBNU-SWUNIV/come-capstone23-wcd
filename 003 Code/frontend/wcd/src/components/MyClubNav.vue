<!-- 로그인 되어있을때 앱 로고와 가입된 클럽, 클럽검색, 클럽생성 버튼
  로그인 되어있지 않을때 클럽 네비게이션 없음 -->
<template>
  <div class="MyClubNav">
    <div class="My-Club-Wrap" v-if="$store.state.isLogin">
      <div class="Text-Logo-Area">
        <label class="Text-Logo">W C D</label>
      </div>
      <div class="Img-Logo-Area">
        <img
          class="Img-Logo"
          src="https://cdn-icons-png.flaticon.com/512/3097/3097126.png "
          @click="goHomePage"
        />
      </div>
      <div class="MyClub-Logo-Area">
        <div
          class="Club-Icon-Box"
          v-for="club in clubs"
          :key="club.id"
          @click="goClubHomePage(club.id)"
        >
          <img
            class="MyClub-Logo"
            src="https://demo.ycart.kr/shopboth_farm_max5_001/data/editor/1612/cd2f39a0598c81712450b871c218164f_1482469221_493.jpg"
            @click="goClubHomePage"
          />
          <label class="MyClub-Name">{{ club.clubName }}</label>
        </div>
      </div>
      <div class="Search-Create-Btn-Area">
        <button class="Club-Search-Btn" @click="goClubSearchPage">
          <img
            class="search-create-icon"
            src="https://cdn-icons-png.flaticon.com/512/151/151773.png"
          />
        </button>
        <button class="Club-Create-Btn" @click="goClubCreatePage">
          <img
            class="search-create-icon"
            src="https://cdn-icons-png.flaticon.com/512/1237/1237946.png "
          />
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import router from "../router/index";
import { ref } from "vue";
import axios from "axios";
import store from "@/vuex/store";

export default {
  setup() {
    let clubs = ref([]);

    const goHomePage = () => {
      router.push({
        name: "HomePage",
      });
    };

    const goClubHomePage = (id) => {
      store.commit('setSelectedMeetingId', id)
      router.push({
        name: "ClubHomePage",
        params: {
          id: id,
        },
      });
    };

    const goClubSearchPage = () => {
      router.push({
        name: "ClubSearchPage",
      });
    };

    const goClubCreatePage = () => {
      router.push({
        name: "ClubCreatePage",
      });
    };

    const getClubs = async () => {
      try {
        await axios.get("http://localhost:3000/clubs").then((response) => {
          const clubsData = response.data.map((item) => {
            const parsedItem = JSON.parse(Object.keys(item)[0]);
            parsedItem.id = item.id;
            return parsedItem;
          });
          clubs.value = clubsData;
        });
      } catch (error) {
        console.error("클럽 목록을 불러오는 중 오류가 발생했습니다.", error);
      }
    };

    getClubs();

    return {
      goHomePage,
      goClubHomePage,
      goClubSearchPage,
      goClubCreatePage,
      clubs,
      getClubs,
    };
  },
};
</script>

<style>
.MyClubNav {
  height: 1080px;
  width: 120px;
  display: flex;
  flex-direction: column;
}
.My-Club-Wrap {
  height: 100%;
  width: 100%;
}
.Text-Logo-Area {
  height: 45px;
  width: 100%;
}
.Text-Logo {
  height: 45px;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
}
.Img-Logo-Area {
  height: 95px;
  width: 100%;
  display: flex;
  justify-content: center;
}
.Img-Logo {
  height: 80px;
  width: 80px;
  border-radius: 20px 20px 20px 20px;
  cursor: pointer;
}
.MyClub-Logo-Area {
  height: 705px;
  width: 100%;
  overflow:auto;
}
.Club-Icon-Box {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 10px;
}
.MyClub-Logo {
  height: 70px;
  width: 70px;
  border-radius: 30px 30px 30px 30px;
  cursor: pointer;
}
.MyClub-Name {
  font-size: 20px;
}
.Search-Create-Btn-Area {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.Club-Search-Btn {
  width: 70px;
  height: 70px;
  border-radius: 30px 30px 30px 30px;
  border: none;
  margin-top: 15px;
}
.Club-Create-Btn {
  width: 70px;
  height: 70px;
  border-radius: 30px 30px 30px 30px;
  border: none;
  margin-top: 15px;
}
.search-create-icon {
  height: 40px;
  width: 40px;
}
</style>

<style scoped>
.MyClub-Logo-Area::-webkit-scrollbar {
  width: 5px; /* 스크롤바 너비 */
}

.MyClub-Logo-Area::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤바 색상 */
  border-radius: 5px; /* 스크롤바 모서리 둥글게 */
}

.MyClub-Logo-Area::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 스크롤바 마우스 호버 시 색상 */
}
</style>