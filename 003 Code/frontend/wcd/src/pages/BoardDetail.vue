<template>
  <div>
    <div class="TitleArea">
      <label class="BoardTitle">{{ board.title }}</label>
      <button v-if="isAuthor" class="hamburger-button" @click="showMenu = !showMenu">
        &#9776;
      </button>
      <div v-if="showMenu" class="menu">
        <button class="menu-button" @click="editBoard">수정</button>
        <button class="menu-button" @click="deleteBoard(board.id)">삭제</button>
      </div>
    </div>
    <div class="InfoArea">
      <label class="BoardInfo">[ 작성자: {{ board.writerName }}]</label>
      <label class="BoardInfo">{{ formatDateAndTime(board.createdAt) }}</label>
    </div>
    <div class="ContentArea" v-html="board.content">
    </div>
    <div class="CommentArea">
      <div v-for="comment in comments.content" :key="comment.id">
        <p class="CommentAuthor">{{comment.writerName}}</p>
        <p class="CommentTime">{{formatDateAndTime(comment.createdAt)}}</p>
        <p v-html="comment.content"></p>
      </div>
    </div>
    <div class="BtnArea">
      <button
          class="button"
          style="background-color: gray"
          @click="goBoardPage"
      >
        목록
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {onMounted, ref, computed} from "vue";
import { useRoute } from "vue-router";
import router from "@/router/index";

export default {
  setup() {
    const board = ref({});
    const comments = ref([])
    const route = useRoute();
    const currentClubId = route.params.clubId;
    const currentBoardId = route.params.boardId;
    const showMenu = ref(false);
    const currentUser = ref(/* 현재 사용자의 정보 */);

    const isAuthor = computed(() => board.value.writerName === currentUser.value.name);

    const goBoardPage = () => {
      router.push({
        name: "ClubBoardPage",
        params: {
          id: currentClubId,
        },
      });
    };

    const editBoard = () => {
      // 게시글 수정 로직
    };

    const fetchData = async () => {
      const response = await axios.get(`http://localhost:8000/board-service/clubs/${currentClubId}/posts/${currentBoardId}`);
      board.value = response.data;
      const date = new Date(board.value.createdAt);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const formattedDate = `${year}.${month}.${day}`;
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const formattedTime = `${hours}:${minutes}`;
      board.value.createdAt = `${formattedDate} ${formattedTime}`;
    };

    const deleteBoard = async () => {
      try {
        const url = `/board-service/clubs/${currentClubId}/posts/${currentBoardId}`;
        await axios.delete(url);
        alert("게시글이 삭제 되었습니다.");
        goBoardPage();
      } catch (error) {
        console.error('데이터 삭제 중 오류 발생:', error);
      }
    };

    const fetchComments = async () => {
      const response = await axios.get(`/board-service/clubs/${currentClubId}/posts/${currentBoardId}/comments`);
      comments.value = response.data;
    };

    const formatDateAndTime = (dateString) => {
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const formattedDate = `${year}.${month}.${day}`;
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const formattedTime = `${hours}:${minutes}`;
      return `${formattedDate} ${formattedTime}`;
    };


    onMounted(async () => {
      await fetchData();
      await fetchComments();
    });

    fetchData();

    return {
      board,
      comments,
      formatDateAndTime,
      goBoardPage,
      deleteBoard,
      showMenu,
      currentUser,
      isAuthor,
      editBoard
    };
  },
};
</script>

<style>

.TitleArea {
  height: 0%;
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
  margin-left: 150px;  /* Adjust left margin */
  margin-right: 150px; /* Adjust right margin */
}

.BtnArea {
  height: 100px;
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

.CommentArea {
  height: 600px;
  margin-left: 150px;
  margin-right: 150px;
}

.CommentArea div {
  margin-bottom: 10px;
  border-bottom: 1px solid #ccc;
  padding-bottom: 10px;
}

.CommentArea div p {
  font-size: 20px;
  margin: 0;
}

.CommentAuthor {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 5px;
}

.CommentTime {
  font-size: 18px;
  color: #777;
}
</style>
