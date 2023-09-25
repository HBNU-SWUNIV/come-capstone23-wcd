<template>
  <div style="width: 100%">
    <ClubNavigation />
    <div style="color: white; display: flex; flex-direction: row">
      <LeftClubNav />
      <div
        style="
          width: 950px;
          padding: 20px;
          border-right: 1px solid rgb(127, 127, 127);
        "
      >
      <v-btn :to="`/clubs/${this.$route.params.clubId}/board-create`">게시글 작성</v-btn>
      </div>
    </div>

    <div>
      <!-- 게시물 목록을 표시하는 부분 -->
      <div v-for="post in boards" :key="post.id">
        <!-- 게시물 내용을 표시하는 부분 -->
        <div>{{ post.title }}</div>
        <div>{{ post.content }}</div>
      </div>

      <!-- 페이지네이션 링크 생성 -->
      <router-link
        v-for="page in pageCount"
        :key="page"
        :to="generatePageLink(page)"
      >
        {{ page }}
      </router-link>
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
        const apiUrl = `/board-service/clubs/${this.$route.params.clubId}/posts?page=${page}&size=10`;

        const config = {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
          },
        };
        await this.$axios.get(apiUrl, config).then((res) => {
          console.log(res.data);
          this.myclubs = res.data;
        });
      } catch (err) {
        console.error("err", err);
        this.boards = [];
      }
    },

    generatePageLink(page) {
      return `/clubs/${this.$route.params.clubId}/board?page=${page}&size=10`;
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