<!-- 로그인 되어있을때 /모임 추천화면(홈화면)
  params없으면 모임 추천화면
  로그인 되어있지 않을때 "로그인이 필요한 서비스입니다."" -->
<template>
  <div class="HomePage">
    <div class="LoginScreen" v-if="$store.state.isLogin">
      <label class="Recommend-Message">나의 모임</label>
      <div class="scrollable-container">
        <div
          class="scrollable-content d-flex flex-row"
          v-for="club in clubs"
          :key="club.id"
          @click="goClubHome(club.id)"
        >
          <!-- 모임 정보 표시 -->
          <img
            class="MyClub-Logo"
            src="https://demo.ycart.kr/shopboth_farm_max5_001/data/editor/1612/cd2f39a0598c81712450b871c218164f_1482469221_493.jpg"
          />
          <div class="information d-flex flex-column ml-3">
            <h3>{{ club.clubName }}</h3>
            <p>{{ club.description }}</p>
          </div>
          <button class="deleteBtn" @click="deleteClub(club.id)">삭제</button>
        </div>
      </div>
      <div class="NoneClubs" v-if="clubs.length === 0">
        <img
          class="NotLoginImg"
          src="https://cdn-icons-png.flaticon.com/512/11046/11046384.png "
        />
        <label class="NeedLogin">가입한 모임이 없습니다.</label>
        <div class="LoginLinkArea mt-4">
          <router-link class="LoginRouterlink" :to="{ name: 'ClubSearchPage' }"
            >모임검색</router-link
          >
          <router-link
            class="LoginRouterlink"
            style="margin-left: 80px"
            :to="{ name: 'ClubCreatePage' }"
            >모임생성</router-link
          >
        </div>
      </div>
    </div>
    <div class="NotLoginScreen" v-else>
      <img
        class="NotLoginImg"
        src="https://cdn-icons-png.flaticon.com/512/11046/11046434.png"
      />
      <label class="NeedLogin">로그인이 필요한 서비스입니다.</label>
      <div class="LoginLinkArea mt-4">
        <router-link class="LoginRouterlink" :to="{ name: 'LoginPage' }"
          >로그인</router-link
        >
        <router-link
          class="LoginRouterlink"
          style="margin-left: 80px"
          :to="{ name: 'SignupPage' }"
          >회원가입</router-link
        >
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { ref, onMounted } from "vue";
import router from "../router/index";

export default {
  setup() {
    const clubs = ref([]);

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

    const goClubHome = (id) => {
      router.push({
        name: "ClubHomePage",
        params: {
          id: id,
        },
      });
    };

    const deleteClub = async (id) => {
      try {
        // JSON-Server API의 URL을 포함한 요청 주소를 생성합니다.
        const url = `http://localhost:3000/clubs/${id}`;

        // 서버에 DELETE 요청을 보냅니다.
        await axios.delete(url);

        // 성공적으로 삭제되면 원하는 동작을 수행하도록 추가적인 로직을 구현합니다.
        console.log(`ID ${id}에 해당하는 데이터가 성공적으로 삭제되었습니다.`);
        alert("모임이 삭제 되었습니다.");
        router.push({
          name:'HomePage'
        })
      } catch (error) {
        console.error('데이터 삭제 중 오류 발생:', error);
      }
    };

    onMounted(() => {
      getClubs();
    });

    return {
      clubs,
      goClubHome,
      deleteClub,
    };
  },
};
</script>

<style>
.HomePage {
  height: 100%;
  width: 100%;
}
.NoneClubs {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.NotLoginScreen {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.NotLoginImg {
  height: 500px;
  width: 500px;
  margin-top: 100px;
}
.NeedLogin {
  font-size: 30px;
  color: green;
}
.LoginLinkArea {
  display: flex;
}
.Recommend-Message {
  height: 100px;
  width: 1500px;
  font-size: 40px;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.deleteBtn{
  height:30px;
  background-color: red;
  color:white;
  border:none;
  border-radius: 5px;
  font-size: 13px;
}
.information{
  width:270px;
}
</style>

<style scoped>
.LoginRouterlink {
  font-size: 45px;
  color: white;
  text-decoration: none;
}
.LoginRouterlink:hover {
  color: purple;
}
.MyClub-Logo {
  height: 130px;
  width: 130px;
  border-radius: 0px;
}
</style>

<style scoped>
.scrollable-container {
  height:800px;
  width:1500px;
  display: flex;
  flex-wrap: wrap;
  overflow:auto;
}

.scrollable-content {
  width: calc(
    33.33% - 10px
  ); /* 한 줄에 3개씩 표시하기 위해 33.33%로 설정하고 간격을 주기 위해 10px 빼줌 */
  margin: 5px;
  padding: 10px;
  border: 1px solid #484747;
  color: white;
  cursor: pointer;
  max-height: 900px; /* 스크롤 가능한 컨테이너의 최대 높이를 지정 */
  overflow-y: auto;
}

.scrollable-container::-webkit-scrollbar {
  width: 10px; /* 스크롤바 너비 */
}

.scrollable-container::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤바 색상 */
  border-radius: 5px; /* 스크롤바 모서리 둥글게 */
}

.scrollable-container::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 스크롤바 마우스 호버 시 색상 */
}

/* 스크롤바 track 스타일 (트랙은 스크롤바의 배경) */

</style>