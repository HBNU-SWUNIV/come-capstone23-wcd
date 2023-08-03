<template>
  <div>
    <div class="TitleArea">
      <label class="BoardTitle">{{ board.title }}</label>
    </div>
    <div class="InfoArea">
      <label class="BoardInfo">[ 작성자: {{ board.writerName }}]</label>
      <label class="BoardInfo">{{ board.createdAt }}</label>
    </div>
    <div class="ContentArea">
      <label class="BoardContent">{{ board.content }}</label>
    </div>
    <div class="BtnArea">
      <button
          class="button"
          style="background-color: gray"
          @click="goBoardPage"
      >
        목록
      </button>
      <button class="button" style="background-color: green">수정</button>
      <button class="button" style="background-color: red" @click="deleteBoard(board.id)">
        삭제
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { ref } from "vue";
import { useRoute } from "vue-router";
import router from "@/router/index";

export default {
  setup() {
    const board = ref({});
    const route = useRoute();
    const currentClubId = route.params.clubId;
    const currentBoardId = route.params.boardId;

    const goBoardPage = () => {
      router.push({
        name: "ClubBoardPage",
        params: {
          id: currentClubId,
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
      const response = await axios.get(`http://localhost:8000/board-service/clubs/${currentClubId}/posts/${currentBoardId}`);
      board.value = response.data;

      // 날짜와 시간을 분 단위까지만 표시
      const date = new Date(board.value.createdAt);

      // Get Year, Month and Date
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0'); // JavaScript months are 0-based
      const day = String(date.getDate()).padStart(2, '0');
      const formattedDate = `${year}.${month}.${day}`;

      // Get Hours and Minutes in 24-hour format
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const formattedTime = `${hours}:${minutes}`;

      board.value.createdAt = `${formattedDate} ${formattedTime}`;
    };

    const deleteBoard = async () => {
      try {
        const url = `http://localhost:8000/board-service/clubs/${currentClubId}/posts/${currentBoardId}`;
        await axios.delete(url);
        alert("게시글이 삭제 되었습니다.");
        goBoardPage();
      } catch (error) {
        console.error('데이터 삭제 중 오류 발생:', error);
      }
    };

    console.log("클럽: ", currentClubId, "게시판: ", currentBoardId)
    fetchData();

    return {
      board,
      goBoardPage,
      deleteBoard,
    };
  },
};
</script>

<style>
.TitleArea {
  height: 300px;
  display: flex;
  flex-direction: column;
}
.BoardTitle {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: end;
  font-size: 70px;
  margin-left: 150px;
}
.InfoArea {
  height: 100px;
  display: flex;
  flex-direction: column;
}
.BoardInfo {
  font-size: 20px;
  margin-left: 150px;
}
.ContentArea {
  height: 400px;
}
.BoardContent {
  height: 100%;
  display: flex;
  font-size: 40px;
  margin-left: 150px;
}
.BtnArea {
  height: 200px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-left: 20px;
}
.button {
  height: 60px;
  width: 80px;
  border-radius: 10px;
  margin: 10px;
  font-size: 20px;
  color: white;
}
</style>