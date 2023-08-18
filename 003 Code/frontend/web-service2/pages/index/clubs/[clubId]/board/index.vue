<template>
  <v-container class="BoardPage">
    <!-- Search Area -->
    <v-row class="SearchArea justify-center mb-4">
      <v-col cols="8">
        <v-card flat tile>
          <v-row no-gutters>
            <!-- Filter Select -->
            <v-col cols="3">
              <v-select
                  label="필터"
                  v-model="selectedFilter"
                  :items="['제목', '작성자', '날짜']"
                  outlined
                  dense
              ></v-select>
            </v-col>

            <!-- Search Input -->
            <v-col cols="6">
              <v-text-field
                  v-model="searchText"
                  label="검색"
                  outlined
                  dense
                  hide-details
              ></v-text-field>
            </v-col>

            <!-- Search Button -->
            <v-col cols="3" class="d-flex align-center">
              <v-btn @click.prevent="search" color="primary" block>검색</v-btn>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
      <v-col cols="2" class="text-end">
        <v-btn @click="goCreateBoardPage" color="secondary">글 작성</v-btn>
      </v-col>
    </v-row>

    <v-row class="BoardBar">
      <v-col cols="1" class="BoardNav text-center">NO.</v-col>
      <v-col cols="6" class="BoardNav">제목</v-col>
      <v-col cols="3" class="BoardNav">작성자</v-col>
      <v-col cols="2" class="BoardNav text-end">날짜</v-col>
    </v-row>

    <v-row v-for="board in displayedBoardsWithClubId" :key="board.id" class="BoardContentsArea">
      <v-col cols="1" class="Contents text-center">{{ board.id }}</v-col>
      <v-col cols="6" class="Contents" @click="goBoardDetail(currentClubId, board.id)">
        {{ board.title }}
      </v-col>
      <v-col cols="3" class="Contents text-center">{{ board.writerName }}</v-col>
      <v-col cols="2" class="Contents text-end">{{ formatDate(board.createdAt) }}</v-col>
    </v-row>
  </v-container>
</template>

<script>
import { ref, computed } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";

export default {
  setup() {
    const route = useRoute();
    const currentClubId = computed(() => route.params.clubId);

    const goCreateBoardPage = () => {
      navigateTo(`/clubs/${currentClubId.value}/board/write`);
    }

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
      navigateTo(`/clubs/${currentClubId.value}/board/${boardId}`);
    }
    const perPage = 10; // 페이지 당 보여줄 항목 수
    const boards = ref([]);
    const currentPage = ref(1);

    const fetchData = async () => {
      const response = await axios.get(`/board-service/clubs/${currentClubId.value}/posts`);
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