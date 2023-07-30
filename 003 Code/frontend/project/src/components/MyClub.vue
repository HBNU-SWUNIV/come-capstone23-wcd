<template>
  <div class="MyClubBar">
    <div v-if="$store.state.isLogin">
      <div class="LogoArea mt-3">W C D</div>
      <div class="MyProfile" @click="goHome">
        <img class="UserProfile mt-2 mb-3" src="../assets/test.jpg" />
      </div>
      <div class="MyClub mt-3">
        <div
          class="d-flex flex-column align-items-center"
          v-for="club in clubs"
          :key="club.id"
          @click="goClubHome(club.id)"
        >
          <img class="MyClubProfile mt-2" src="../assets/test2.jpg" />
          <label class="ClubName mt-2">{{ club.clubName }}</label>
        </div>
      </div>
      <div class="CreateSearchClub">
        <button class="SearchButton" @click="goSearchPage">
          <img class="SearchIcon" src="../assets/search.png" />
        </button>
        <button class="CreateButton" @click="goCreatePage">
          <img class="AddIcon" src="../assets/add.png" />
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import router from "../router/index";
import { ref } from "vue";
import axios from "axios";

export default {
  setup() {
    let clubs = ref([]);

    const goSearchPage = () => {
      router.push({
        name: "SearchClubPage",
      });
    };

    const goCreatePage = () => {
      router.push({
        name: "CreateClubPage",
      });
    };

    const goClubHome = (id) => {
      router.push({
        name: "PageHome",
        params: {
          id: id,
        },
      });
    };

    const goHome = () => {
      router.push({
        name: "HomePage",
      });
    };

    const getClubs = async () => {
      try {
        await axios.get("http://localhost:3000/clubs").then((response) => {
          const clubsData = response.data.map((item) => {
            const parsedItem = JSON.parse(Object.keys(item)[0]);
            parsedItem.id = item.id;
            console.log(parsedItem.id)
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
      goSearchPage,
      goCreatePage,
      getClubs,
      goHome,
      clubs,
      goClubHome,
    };
  },
};
</script>

<style>
.MyClubBar {
  height: 100%;
  width: 120px;
  text-align: center;
}
.MyProfile{
  cursor: pointer;
}
.LogoArea {
  font-size: 13px;
}
.UserProfile {
  height: 80px;
  width: 80px;
  border-radius: 20px 20px 20px 20px;
}
.MyClubProfile {
  height: 70px;
  width: 70px;
  border-radius: 20px 20px 20px 20px;
  cursor: pointer;
}
.ClubName {
  font-size: 15px;
}
.SearchButton {
  position: fixed;
  top: 885px;
  left: 25px;
  width: 70px;
  height: 70px;
  border-radius: 30px 30px 30px 30px;
  border: none;
}
.CreateButton {
  position: fixed;
  top: 965px;
  left: 25px;
  width: 70px;
  height: 70px;
  border-radius: 30px 30px 30px 30px;
  border: none;
}
.SearchIcon {
  height: 40px;
  width: 40px;
}
.AddIcon {
  height: 40px;
  width: 40px;
}
</style>