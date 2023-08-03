<template>
  <div class="BoardPage">
    <div class="SearchArea">
      <div class="d-flex flex-row justify-content-center">
        <form class="mt-4" style="margin-left: 200px">
          <select class="BoardFilter form-select">
            <option selected>필터</option>
            <option value="title" class="mt-3">제목</option>
            <option value="writer" class="mt-3">작성자</option>
            <option value="date" class="mt-3">날짜</option>
          </select>

          <input
            type="text"
            class="BoardSearchInput search-input"
            v-model="searchText"
          />

          <button class="BoardSearchBtn" type="submit">검색</button>
        </form>
        <button class="WriteBtn" @click="goCreateBoardPage">글 작성</button>
      </div>
    </div>
    <div class="BoardBar">
      <label class="BoardNav" style="margin-left: 150px">NO.</label>
      <label class="BoardNav" style="margin-left: 425px">제목</label>
      <label class="BoardNav" style="margin-left: 425px">작성자</label>
      <label class="BoardNav" style="margin-left: 200px">날짜</label>
    </div>

    <div class="BoardContentsArea">
      <div class="d-flex flex-row mt-4" v-for="board in displayedBoardsWithClubId" :key="board.id">
        <div class="Contents" style="width: 100px; text-align: center; margin-left: 115px">
          <label> {{ board.id }} </label>
        </div>
        <div class="Contents" style="width: 650px; text-align: left; margin-left: 100px; cursor: pointer;" @click="goBoardDetail(currentClubId, board.id)">
          <label> {{ board.title }}</label>
        </div>
        <div class="Contents" style="width: 200px; text-align: center; margin-left: 30px">
          <label> {{ board.writerName }}</label> <!-- Displaying writerName -->
        </div>
        <div class="Contents" style="width: 250px; text-align: center; margin-left: 20px">
          <label>{{ formatDate(board.createdAt) }}</label> <!-- Displaying formatted createdAt -->
        </div>
      </div>
    </div>
    <!-- ... (rest of the template) ... -->
  </div>
</template>

<script>
import router from "@/router/index";
import { ref, computed } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";

export default {
  setup() {
    const route = useRoute();
    const currentClubId = route.params.id;

    const goCreateBoardPage = () => {
      router.push({
        name: "CreateBoardPage",
      });
    };

    // Function to format date as "YYYY-MM-DD HH:mm:ss" or "HH:mm:ss" for current day posts
    const formatDate = (timestamp) => {
      const date = new Date(timestamp);
      const now = new Date();

      const isToday =
          date.getDate() === now.getDate() &&
          date.getMonth() === now.getMonth() &&
          date.getFullYear() === now.getFullYear();

      if (isToday) {
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        return `${hours}:${minutes}`;
      } else {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
      }
    };

    const goBoardDetail = (clubId, boardId) => {
      router.push({
        name: 'BoardDetail',
        params: {
          clubId: clubId,
          boardId: boardId,
        },
      })
    }
    const perPage = 10; // 페이지 당 보여줄 항목 수
    const boards = ref([]);
    const currentPage = ref(1);

    // JWT 토큰 가져오기
    const access_token = localStorage.getItem('access_token');

    // JWT 토큰이 존재하는 경우에만 헤더 설정
    if (access_token) {
      axios.defaults.headers.common['Authorization'] = `Bearer ${access_token}`;
    }

    // JSON 서버에서 데이터 받아오기
    // const fetchData = async () => {
    //   const response = await axios.get(`http://localhost:8000/board-service/clubs/${currentClubId}/posts`);
    //   const responseData = response.data;
    //
    //   boards.value = responseData.content; // 게시글 목록 설정
    // };

    const fetchData = async () => {
      const response = await axios.get(`http://localhost:8000/board-service/clubs/${currentClubId}/posts`);
      const responseData = response.data;

      boards.value = responseData.content.sort((a, b) => b.id - a.id); // 게시글 목록 설정, 내림차순으로 정렬
    };

    fetchData(); // fetchData 함수를 호출하여 비동기로 데이터 받아오기

    const displayedBoardsWithClubId = computed(() => {
      const start = (currentPage.value - 1) * perPage;
      const end = start + perPage;
      return boards.value.slice(start, end);
    });

    const totalPages = computed(() => {
      return boards.value.length > 0 ? fetchData.totalPages : 0;
    });

    const prevPage = () => {
      if (currentPage.value > 1) {
        currentPage.value--;
      }
    };

    const nextPage = () => {
      if (currentPage.value < totalPages.value) {
        currentPage.value++;
      }
    };

    const gotoPage = (pageNumber) => {
      if (pageNumber >= 1 && pageNumber <= totalPages.value) {
        currentPage.value = pageNumber;
      }
    };

    return {
      goCreateBoardPage,
      totalPages,
      currentPage,
      prevPage,
      nextPage,
      gotoPage,
      currentClubId,
      displayedBoardsWithClubId,
      goBoardDetail,
      formatDate, // Expose the formatDate function to the template
    };
  },
};
</script>


<style>
.SearchArea {
  height: 140px;
  width: 100%;
}
.BoardBar {
  height: 40px;
  width: 100%;
  background-color: #383838;
}
.BoardNav {
  font-size: 20px;
  margin-top: 0.4rem;
}
.BoardContentsArea {
  height: 600px;
  width: 100%;
  font-size: 20px;
}
.PageNavArea {
  height: 50px;
  display: flex;
  justify-content: center;
  margin-top: 70px;
  font-size: 25px;
}
.PageBox {
  width: 80px;
  text-align: center;
}
.BoardFilter {
  width: 95px;
  height: 40px;
  text-align: center;
  background-color: #383838;
  color: white;
  border: none;
  border-radius: 0.25rem;
  margin-top: 30px;
}
.BoardSearchBtn {
  width: 95px;
  height: 40px;
  text-align: center;
  background-color: #383838;
  color: white;
  border: none;
  border-radius: 0.25rem;
  margin-top: 30px;
}
.BoardSearchInput {
  width: 640px;
  height: 40px;
  border-radius: 0.25rem;
  margin-top: 30px;
}
.active {
  color: #34da19; /* 원하는 활성 상태의 버튼 배경색 지정 */
}
.WriteBtn {
  background-color: #383838;
  border: none;
  color: white;
  height: 60px;
  width: 100px;
  border-radius: 5px 5px 5px 5px;
  margin-top: 44px;
  margin-left: 150px;
  font-size: 20px;
}
</style>