<template>
  <div class="d-flex" style="width: 100%">
    <div style="width: 70%; padding: 60px; padding-top: 120px">
      <h1 style="margin-bottom: 20px">{{ post.title }}</h1>
      <p
        style="
          margin: 0;
          margin-bottom: 5px;
          font-size: 15px;
          color: rgb(181, 255, 181);
        "
      >
        작성자: {{ post.writerName }}
      </p>
      <p style="margin: 0; font-size: 13px; color: gray">
        작성일: {{ formatDate(post.createdAt) }}
      </p>
      <p style="margin: 0; font-size: 13px; color: gray">
        수정일: {{ formatDate(post.updateAt) }}
      </p>
      <hr style="margin-top: 20px" />
      <!-- <p style="height:350px; margin-top: 20px; font-size: 20px;">{{ post.content }}</p> -->
      <div
        class="drawer-content"
        style="
          height: 350px;
          margin-top: 20px;
        "
        v-html="formattedContent"
      ></div>
      <div style="margin-top: 20px">
        <v-btn @click="goBoardList">목록</v-btn>
        <v-btn style="color: rgb(125, 255, 125)">수정</v-btn>
        <v-btn style="color: rgb(255, 125, 125)">삭제</v-btn>
      </div>
    </div>
    <div style="width: 30%; padding: 20px; background-color: rgb(39, 39, 39)">
      <div class="d-flex">
        <h2>댓글</h2>
        <h2 style="margin-left: 10px; color: rgb(161, 161, 161)">229개</h2>
      </div>
      <div>댓글구역</div>
      <div>댓글작성구역</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      post: [],
      formattedContent: "",
    };
  },
  methods: {
    async getBoardDetail() {
      try {
        const access_token = this.$store.state.access_token;
        const config = {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
          },
        };
        await this.$axios
          .get(
            `/board-service/clubs/${this.$route.params.clubId}/posts/${this.$route.params.boardId}`,
            config
          )
          .then((res) => {
            console.log(res.data);
            this.post = res.data;
            this.formattedContent = this.formatContent(this.post.content); // 데이터를 받고 나서 가공
          });
      } catch (err) {
        console.error("err", err);
      }
    },
    formatDate(dateTimeString) {
      const dateTime = new Date(dateTimeString);
      const year = dateTime.getFullYear();
      const month = (dateTime.getMonth() + 1).toString().padStart(2, "0");
      const day = dateTime.getDate().toString().padStart(2, "0");
      const hours = dateTime.getHours().toString().padStart(2, "0");
      const minutes = dateTime.getMinutes().toString().padStart(2, "0");
      const seconds = dateTime.getSeconds().toString().padStart(2, "0");

      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },

    goBoardList() {
      this.$router.push(`/clubs/${this.$route.params.clubId}/board`);
    },

    formatContent(content) {
      // HTML 태그를 파싱하고 스타일을 적용
      const parser = new DOMParser();
      const doc = parser.parseFromString(content, "text/html");

      // 예시: <p> 태그에 스타일 추가
      const paragraphs = doc.getElementsByTagName("p");
      for (let i = 0; i < paragraphs.length; i++) {
        paragraphs[i].style.fontSize = "16px";
        paragraphs[i].style.color = "white";
      }

      // HTML로 변환
      const formattedContent = new XMLSerializer().serializeToString(doc);

      // 가공된 내용을 반환
      return formattedContent;
    },
  },
  created() {
    this.getBoardDetail();
  },
};
</script>

<style scoped>
.drawer-content {
  height: 300px; /* 56px는 앱 바 높이 */
  overflow-y: auto; /* 세로 스크롤 활성화 */
}
.drawer-content::-webkit-scrollbar {
  width: 5px;
}

.drawer-content::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤바 색상 */
  border-radius: 5px;
}

.drawer-content::-webkit-scrollbar-thumb:hover {
  background-color: #555;
}
</style>