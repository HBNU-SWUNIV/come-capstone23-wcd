<template>
  <div style="width: 100%; height: 100%">
    <div style="height: 100%; color: white; display: flex; flex-direction: column">
      <div style="width: 100%;">
        <div
          v-for="post in boards"
          :key="post.id"
          class="post-container"
          @click="goBoardDetail(clubId, post.id)"
          style="width: 100%; display: flex; flex-direction: row; cursor: pointer;"
        >
          <!-- 게시물 내용을 표시하는 부분 -->
          <div style="width: 80%; height: 100%; display: flex; align-items: center; padding-left: 2%"><p
            class="post-title">{{ post.title }}</p></div>
          <div style="width: 10%; height: 100%; display: flex; align-items: center"><p class="post-writer">
            {{ post.writerName }}</p></div>
          <div style="width: 10%; height: 100%; display: flex; align-items: center"><p class="post-date">
            {{ formatDate(post.updateAt) }}</p></div>
        </div>
      </div>
      <div style="display: flex; margin-top: auto; align-items: flex-end; flex-direction: row">
        <div style="width: 70%; margin-left: 15%;">
          <v-pagination
            style="width: auto"
            v-model="pageNumber"
            :length="totalPages"
            :total-visible="10"
            @input="handlePageChange"
          ></v-pagination>
        </div>
        <div>
          <v-btn :to="`/clubs/${clubId}/board-create`">게시글 작성</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  components: {},
  data() {
    return {
      boards: [], // API에서 받아온 데이터를 저장할 배열
      clubId: null,
      pageNumber: 1,
      totalPages: null,
    };
  },
  methods: {
    async getBoards() {
      try {
        const access_token = this.$store.state.access_token;
        const config = {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
          },
          params: {
            page: this.pageNumber - 1,
            size: 10,
            sort: "id,desc"
          },
        };
        await this.$axios
          .get(
            `/board-service/clubs/${this.$route.params.clubId}/posts`,
            config
          )
          .then((res) => {
            console.log(res.data);
            this.boards = res.data.content;
            this.totalPages = res.data.totalPages;
          });
      } catch (err) {
        console.error("err", err);
        this.boards = [];
      }
    },

    formatDate(dateTimeString) {
      const dateTime = new Date(dateTimeString);
      const year = dateTime.getFullYear();
      const month = (dateTime.getMonth() + 1).toString().padStart(2, "0");
      const day = dateTime.getDate().toString().padStart(2, "0");
      // const hours = dateTime.getHours().toString().padStart(2, "0");
      // const minutes = dateTime.getMinutes().toString().padStart(2, "0");
      // const seconds = dateTime.getSeconds().toString().padStart(2, "0");

      return `${year}-${month}-${day}`;
    },

    goBoardDetail(clubId, boardId) {
      this.$router.push(`/clubs/${clubId}/board/${boardId}`);
    },

    handlePageChange() {
      // 페이지 번호가 변경될 때 호출되는 메서드
      this.getBoards(this.pageNumber); // 현재 페이지 번호를 인자로 전달하여 데이터를 가져옴
    },
  },
  created() {
    this.clubId = this.$route.params.clubId; // clubId를 설정
    this.getBoards();
  },
};
</script>

<style scoped>
/* CSS for styling 게시글 (post) section */

.post-container {
  width: calc(50% - 10px);
  background-color: #272727;
  border-radius: 5px;
  margin-bottom: 10px;
  padding: 15px;
  display: flex;
  flex-direction: column;
}

.post-title {
  font-size: 14px;
  margin: 0;
}

.post-writer {
  color: #b4b4b4;
  font-size: 14px;
  margin: 0;
}

.post-date {
  color: #b4b4b4;
  font-size: 14px;
  margin: 0;
}
</style>
