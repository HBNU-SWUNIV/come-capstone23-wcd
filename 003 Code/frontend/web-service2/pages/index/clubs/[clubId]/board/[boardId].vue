<template>
  <v-container>
    <!-- Title Area -->
    <v-row align="center" justify="space-between">
      <v-col>
        <v-subheader class="BoardTitle">{{ board.title }}</v-subheader>
      </v-col>
      <v-col cols="auto">
        <v-menu bottom left>
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon v-bind="attrs" v-on="on">
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          <v-list>
            <v-list-item @click="editBoard">
              <v-list-item-title>수정</v-list-item-title>
            </v-list-item>
            <v-list-item @click="deleteBoard(board.id)">
              <v-list-item-title>삭제</v-list-item-title>
            </v-list-item>
          </v-list>
          </template>
        </v-menu>
      </v-col>
    </v-row>

    <!-- Info Area -->
    <v-row>
      <v-col>
        <v-chip class="BoardInfo" small>{{ board.writerName }}</v-chip>
        <v-chip class="BoardInfo" small>{{ formatDateAndTime(board.createdAt) }}</v-chip>
      </v-col>
    </v-row>

    <!-- Content Area -->
    <v-row>
      <v-col class="ContentArea" v-html="board.content"></v-col>
    </v-row>

    <!-- Comment Area -->
    <v-row>
      <v-col>
        <v-list two-line>
          <v-list-item v-for="comment in comments.content" :key="comment.id">
            <v-list-item-content>
              <v-list-item-title class="CommentAuthor">{{ comment.writerName }}</v-list-item-title>
              <v-list-item-subtitle class="CommentTime">{{ formatDateAndTime(comment.createdAt) }}</v-list-item-subtitle>
              <div v-html="comment.content"></div>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>

    <!-- Button Area -->
    <v-row>
      <v-col class="text-center">
<!--        <v-btn color="grey" @click="goBoardPage">목록</v-btn>-->
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import {ref, computed} from "vue";
import { useRoute } from "vue-router";

export default {
  setup() {
    const board = ref({});
    const comments = ref([])
    const route = useRoute();
    const currentClubId = computed(() => route.params.clubId);
    const currentBoardId = computed( () => route.params.boardId);
    const showMenu = ref(false);
    const currentUser = ref(/* 현재 사용자의 정보 */);

    const goBoardPage = () => {
      navigateTo(`/clubs/${currentClubId.value}/board`);
    };

    const editBoard = () => {
      // 게시글 수정 로직
    };

    const fetchData = async () => {
      const response = await axios.get(`/board-service/clubs/${currentClubId.value}/posts/${currentBoardId.value}`);
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
        const url = `/board-service/clubs/${currentClubId.value}/posts/${currentBoardId.value}`;
        await axios.delete(url);
        alert("게시글이 삭제 되었습니다.");
        goBoardPage();
      } catch (error) {
        console.error('데이터 삭제 중 오류 발생:', error);
      }
    };

    const fetchComments = async () => {
      const response = await axios.get(`/board-service/clubs/${currentClubId.value}/posts/${currentBoardId.value}/comments`);
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


    watch(currentBoardId, async () => {
      await fetchData();
      await fetchComments();
    }, { immediate: true });

    fetchData();

    return {
      board,
      comments,
      formatDateAndTime,
      goBoardPage,
      deleteBoard,
      showMenu,
      currentUser,
      editBoard
    };
  },
};
</script>