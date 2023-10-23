<template>
  <div style="width: 100%">
    <div style="color: white; display: flex; flex-direction: row">
      <div style="width: 100%; padding: 20px; padding-right: 50px">

        <v-btn :to="`/clubs/${clubId}/board-create`">게시글 작성</v-btn>

        <div style="margin-top: 20px" class="post-columns">
          <div
            v-for="post in boards"
            :key="post.id"
            class="post-container"
            @click="goBoardDetail(clubId, post.id)"
            style="cursor: pointer;"
          >
            <!-- 게시물 내용을 표시하는 부분 -->
            <div class="post-content">
              <h3>{{ post.title }}</h3>
              <p class="post-writer">작성자: {{ post.writerName }}</p>
              <p class="post-date">{{ formatDate(post.updateAt) }}</p>
            </div>
          </div>
        </div>
        <v-pagination
          v-model="pageNumber"
          :length="totalPages"
          :total-visible="10"
          @input="handlePageChange"
        ></v-pagination>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  components: {
  },
  data() {
    return {
      boards: [], // API에서 받아온 데이터를 저장할 배열
      clubId: null,
      pageNumber:1,
      totalPages:null,
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
.post-columns {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between; /* Adjust as needed */
}
.post-container {
  width: calc(50% - 10px);
  background-color: #272727;
  border-radius: 5px;
  margin-bottom: 10px;
  padding: 15px;
  display: flex;
  flex-direction: column;
}

.post-content {
  flex: 1;
}

.post-content h3 {
  font-size: 18px;
  margin-bottom: 5px;
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