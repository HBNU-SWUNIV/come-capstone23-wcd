<template>
  <v-card style="width: 50%; margin: 10px; height: fit-content;" 
    ><v-card-title
      >{{ title }}<v-spacer />
      <v-btn :to="`/clubs/${clubId}/notice`">더보기</v-btn></v-card-title
    >
    <v-list>
      <v-list-item
        v-for="(notice, i) in displayedNotices"
        :key="i"
        :to="`/clubs/${clubId}/notice/${notice.id}`"
        router
        exact
      >
        <v-list-item-content style="display: flex;">
          <v-list-item-title>{{ notice.title }} </v-list-item-title>
          <p style="margin:0; font-size: 13px; color:gray;">작성자: {{ notice.writerName }}</p>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    </v-card
  >
</template>

<script>
export default {
  name: "NoticeCard",
  data() {
    return {
      title: "공지사항",
      clubId: null,
      notices: [],
    };
  },
  methods: {
    async getNotices() {
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
            `/notice-service/clubs/${this.$route.params.clubId}/posts`,
            config
          )
          .then((res) => {
            console.log(res.data);
            this.notices = res.data.content;
          });
      } catch (err) {
        console.error("err", err);
        this.notices = [];
      }
    },
  },
  computed: {
    displayedNotices() {
      // 최대 5개 클럽만 표시
      return this.notices.slice(0, 5);
    },
  },
  created() {
    // 라우터의 clubId 값을 가져와서 변수에 할당
    this.clubId = this.$route.params.clubId;
    this.getNotices();
  },
};
</script>