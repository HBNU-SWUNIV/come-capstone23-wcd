<template>
  <div class="ClubSearchPage">
    <div class="SearchArea">
      <div class="d-flex flex-row justify-content-center">
        <form class="mt-4" @submit.prevent="searchClubs">
          <select class="BoardFilter form-select">
            <option selected>필터</option>
            <option value="title" class="mt-3">카테고리</option>
            <option value="writer" class="mt-3">모임설명</option>
          </select>

          <input
            type="text"
            class="BoardSearchInput search-input"
            v-model="searchText"
          />

          <button class="BoardSearchBtn" type="submit">검색</button>
        </form>
      </div>
    </div>
    
    <div class="clubs-list">
      <div class="d-flex flex-row justify-content-center" v-if="filteredClubs.length === 0" >
        <label style="width:300px; color:rgb(255, 125, 125); font-size:30px; text-align: center;">검색 결과가 없습니다.</label>
      </div>
      <div v-else v-for="club in filteredClubs" :key="club.id" class="club-item" @click="goClubHomePage(club.id)">
        <img
            class="MyClubLogo"
            src="https://demo.ycart.kr/shopboth_farm_max5_001/data/editor/1612/cd2f39a0598c81712450b871c218164f_1482469221_493.jpg"
          />
        <div class="club-name">{{ club.clubName }}</div>
        <div class="club-description">{{ club.description }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import router from '@/router/index'

const clubs = ref([]);
const searchText = ref('');

// JSON 서버로부터 모임 정보를 가져옵니다.
onMounted(async () => {
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
});

// 검색어로 필터링된 모임들을 보여주는 계산된 속성입니다.
const filteredClubs = computed(() => {
  if (!searchText.value) {
    return clubs.value;
  }
  const searchKeyword = searchText.value.toLowerCase();
  return clubs.value.filter((club) => club.clubName && club.clubName.toLowerCase().includes(searchKeyword));
});

// 검색어로 모임을 검색하는 메소드입니다.
const searchClubs = async () => {
  // 검색 로직은 computed에 정의된 filteredClubs에서 처리됩니다.
  // 검색어가 입력될 때마다 자동으로 필터링 됩니다.
};

const goClubHomePage = (id) => {
  router.push({
    name:'ClubHomePage',
    params:{
      id:id
    }
  })
}
</script>

<style>
.ClubSearchPage{
  display: flex;
  flex-direction: column;
}
.clubs-list{
  height:860px;
  display: flex;
  flex-direction: column;
}
.club-item{
  width:1200px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  border: 1px solid #3f3f3f;
  margin-left: 150px;
  margin-bottom: 25px;
}
.club-name{
  font-size:30px;
  width:280px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: left;
  margin-left: 20px;
}
.club-description{
  font-size:20px;
  width:760px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: left;
  margin-left: 20px;
}
.MyClubLogo{
  height:120px;
  width:120px;
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

.clubs-list {
  cursor: pointer;
  max-height: 800px; /* 스크롤 가능한 컨테이너의 최대 높이를 지정 */
  overflow-y: auto;
}

.clubs-list::-webkit-scrollbar {
  width: 10px; /* 스크롤바 너비 */
}

.clubs-list::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤바 색상 */
  border-radius: 5px; /* 스크롤바 모서리 둥글게 */
}

.clubs-list::-webkit-scrollbar-thumb:hover {
  background-color: #555; /* 스크롤바 마우스 호버 시 색상 */
}

/* 스크롤바 track 스타일 (트랙은 스크롤바의 배경) */

</style>
