<template>
  <div style="width: 100%">
    <ClubNavigation />
    <div style="color: white; display: flex; flex-direction: row">
      <LeftClubNav />
      <div style="width: 100%; padding: 20px">
        <v-btn :to="`/clubs/${this.$route.params.clubId}/board-create`"
          >게시글 작성</v-btn
        >
        <div v-for="post in boards" :key="post.id">
          <!-- 게시물 내용을 표시하는 부분 -->
          <p>
            {{ post.title }} {{ post.writerName }}
            {{ formatDate(post.createdAt) }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>
  
<script>
import LeftClubNav from "../../../components/LeftClubNav.vue";
import ClubNavigation from "../../../components/ClubNavigation.vue";

export default {
  components: {
    ClubNavigation,
    LeftClubNav,
  },
  data() {
    return {
      boards: [], // API에서 받아온 데이터를 저장할 배열
      pageCount: 0, // 총 페이지 수
      currentPage: 1, // 현재 페이지 번호
    };
  },
  methods: {
    async getBoards() {
      try {
        const access_token = this.$store.state.access_token;
        const page = this.currentPage;
        // API 주소를 동적으로 생성
        const config = {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
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
          });
      } catch (err) {
        console.error("err", err);
        this.boards = [];
      }
    },

    generatePageLink(page) {
      return `/clubs/${this.$route.params.clubId}/board?page=${page}&size=10`;
    },

    formatDate(dateTimeString) {
      const dateTime = new Date(dateTimeString);
      const year = dateTime.getFullYear();
      const month = (dateTime.getMonth() + 1).toString().padStart(2, "0");
      const day = dateTime.getDate().toString().padStart(2, "0");
      const hours = dateTime.getHours().toString().padStart(2, "0");
      const minutes = dateTime.getMinutes().toString().padStart(2, "0");
      const seconds = dateTime.getSeconds().toString().padStart(2, "0");

      return `${year}년 ${month}월 ${day}일 ${hours}:${minutes}:${seconds}`;
    },
  },
  watch: {
    // 현재 페이지 번호가 변경될 때 getBoards 메서드 호출
    currentPage(newValue) {
      this.getBoards();
    },
  },
  created() {
    this.getBoards();
  },
};
</script>