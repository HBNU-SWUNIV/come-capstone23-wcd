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
        style="height: 350px; margin-top: 20px"
        v-html="formattedContent"
      ></div>
      <div style="margin-top: 20px">
        <v-btn @click="goBoardList">목록</v-btn>
        <v-btn
          style="color: rgb(125, 255, 125)"
          :to="`/clubs/${this.$route.params.clubId}/board/${this.$route.params.boardId}/edit`"
          >수정</v-btn
        >
        <v-btn style="color: rgb(255, 125, 125)" @click="deleteBoard"
          >삭제</v-btn
        >
      </div>
    </div>
    <div style="width: 30%; padding: 20px; background-color: rgb(39, 39, 39)">
      <div class="d-flex">
        <h2>댓글</h2>
        <h2 style="margin-left: 10px; color: rgb(161, 161, 161)">
          {{ comments.totalElements }}개
        </h2>
      </div>
      <v-form
        class="d-flex"
        style="margin-top: 20px"
        @submit.prevent="CommentSubmit"
      >
        <input
          class="comment-input"
          placeholder="댓글을 입력하세요..."
          v-model="comment"
        />
        <button type="submit" class="comment-submit-btn" :disabled="!comment">
          댓글
        </button>
      </v-form>
      <div
        style="height: 600px; margin-top: 10px; background-color: rgb(0, 0, 0)"
      >
        {{ comments.content }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      post: [],
      comments: [],
      formattedContent: "",
      comment: "",
      user_id: "",
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

    async getComments() {
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
            `/board-service/clubs/${this.$route.params.clubId}/posts/${this.$route.params.boardId}/comments`,
            config
          )
          .then((res) => {
            console.log(res);
            this.comments = res.data;
          });
      } catch (err) {
        console.log(err);
      }
    },

    async deleteBoard() {
      const confirmDelete = confirm("게시글을 삭제하시겠습니까?");
      if (confirmDelete) {
        try {
          const access_token = this.$store.state.access_token;
          const config = {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${access_token}`,
            },
          };
          await this.$axios
            .delete(
              `/board-service/clubs/${this.$route.params.clubId}/posts/${this.$route.params.boardId}`,
              config
            )
            .then((res) => {
              console.log(res);
              alert("게시글이 삭제되었습니다.");
              this.$router.push(`/clubs/${this.$route.params.clubId}/board`);
            });
        } catch (err) {
          console.log(err);
        }
      }
    },

    //댓글 기능
    async CommentSubmit() {
      let CommentData = {
        content: this.comment,
        commentStep: 1,
        commentGroup: 1,
        commentOrder: 1,
      };
      try {
        const access_token = this.$store.state.access_token;
        const config = {
          headers: {
            "Content-Type": "application/json", // JSON 형식으로 변경
            Authorization: `Bearer ${access_token}`,
          },
        };

        await this.$axios
          .post(
            `board-service/clubs/${this.$route.params.clubId}/posts/${this.$route.params.boardId}/comments`,
            JSON.stringify(CommentData),
            config
          )
          .then((res) => {
            console.log(res);
            this.$router.push(
              `/clubs/${this.$route.params.clubId}/board/${this.$route.params.boardId}`
            );
          });
      } catch (err) {
        console.log(err);
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
    this.getComments();
    this.user_id = sessionStorage.getItem("user_id")
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

.comment-input {
  width: 100%;
  height: 40px;
  padding: 10px;
  border: 1px solid #ccc;
  color: white;
  border-radius: 4px;
  font-size: 16px;
  margin-bottom: 10px;
  transition: border-color 0.2s ease-in-out;
}

.comment-input:focus {
  border-color: #007bff; /* 입력 창에 포커스가 있을 때의 색상 */
  outline: none; /* 포커스 효과 제거 */
}

.comment-input::placeholder {
  color: #ccc; /* 입력 창에 플레이스홀더 텍스트 색상 */
}

.comment-submit-btn {
  height: 40px;
  width: 60px;
  margin-left: 10px;
  background-color: #565656; /* 제출 버튼 배경 색상 */
  color: white; /* 제출 버튼 텍스트 색상 */
  border: none;
  border-radius: 4px;
  font-size: 15px;
  cursor: pointer;
  transition: background-color 0.2s ease-in-out;
}

.comment-submit-btn:hover {
  background-color: #717171; /* 버튼에 호버 시 배경 색상 변경 */
}
</style>