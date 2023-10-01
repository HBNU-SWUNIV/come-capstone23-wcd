<template>
  <div style="width: 90%; padding: 30px; margin: auto">
    <h1 style="margin-bottom: 10px">게시글 수정</h1>
    <v-form @submit.prevent="EditBoardSubmit">
      <v-text-field
        v-model="title"
        label="게시글 제목"
        placeholder="게시글 제목"
        required
        type="Title"
      ></v-text-field>
      <ckeditor
        :editor="editor"
        v-model="editorData"
        :config="editorConfig"
      ></ckeditor>

      <v-btn style="margin-top: 20px; color: rgb(255, 125, 125)" @click="cancel"
        >취소</v-btn
      >
      <v-btn
        type="submit"
        :disabled="!editorData"
        style="margin-top: 20px; color: rgb(125, 255, 125)"
        >수정</v-btn
      >
    </v-form>
  </div>
</template>
  
  <script>
import CKEditor from "~/components/CKEditor.vue";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

export default {
  components: {
    CKEditor,
  },
  data() {
    return {
      title: "",
      editor: ClassicEditor,
      editorData: "",
      editorConfig: {},
      post: {},
    };
  },
  methods: {
    async EditBoardSubmit() {
      let BoardData = {
        category: "category",
        title: this.title,
        content: this.editorData,
      };

      try {
        if (this.title === "") {
          alert("제목을 입력하세요.");
          return;
        }
        const access_token = this.$store.state.access_token;
        const config = {
          headers: {
            "Content-Type": "application/json", // JSON 형식으로 변경
            Authorization: `Bearer ${access_token}`,
          },
        };

        await this.$axios
          .patch(
            `/board-service/clubs/${this.$route.params.clubId}/posts/${this.$route.params.boardId}`,
            JSON.stringify(BoardData),
            config
          )
          .then((res) => {
            console.log(res);
            alert("게시글이 수정되었습니다.");
            this.$router.push(`/clubs/${this.$route.params.clubId}/board/${this.$route.params.boardId}`);
          });
      } catch (err) {
        console.log(err);
      }
    },
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

            this.title = this.post.title;
            this.editorData = this.post.content;
          });
      } catch (err) {
        console.error("err", err);
      }
    },
    async cancel() {
      const confirmCancel = confirm(
        "작성한 내용이 모두 사라집니다. 게시글 수정을 취소하시겠습니까?"
      );

      if (confirmCancel) {
        // 사용자가 확인을 눌렀을 때
        this.$router.push(
          `/clubs/${this.$route.params.clubId}/board/${this.$route.params.boardId}`
        ); // 홈 페이지로 이동 또는 원하는 경로로 이동
      }
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
  
  <style>
.ck-editor__editable {
  height: 400px;
}
.ck-content {
  font-size: 15px;
}
</style>