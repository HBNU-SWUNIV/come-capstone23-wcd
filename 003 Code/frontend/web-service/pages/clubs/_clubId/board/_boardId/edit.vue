<template>
  <div style="width: 100%; padding: 30px;">
    <v-form style="height: 100%" @submit.prevent="EditBoardSubmit">
      <v-text-field
        v-model="title"
        label="게시글 제목"
        placeholder="게시글 제목"
        required
        type="text"
      ></v-text-field>
      <ckeditor
        :editor="editor"
        v-model="editorData"
        :config="editorConfig"
      ></ckeditor>

      <div style="margin-top: auto; display: flex; flex-direction: row-reverse; align-items: flex-end; width: 100%; height: 10%">
      <v-btn  style="margin-left: 10px;" @click="cancel"
        >취소</v-btn
      >
      <v-btn
        type="submit"
        :disabled="!editorData"
        >수정</v-btn>
      </div>
    </v-form>
  </div>
</template>

  <script>
import CKEditor from "@ckeditor/ckeditor5-vue2";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

export default {
  components: {
    ckeditor: CKEditor.component,
  },
  data() {
    return {
      title: "",
      editor: ClassicEditor,
      editorData: "",
      editorConfig: {},
      post: {},
      formattedContent: "",
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
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
          },
        };

        const response = await this.$axios.patch(
          `/board-service/clubs/${this.$route.params.clubId}/posts/${this.$route.params.boardId}`,
          JSON.stringify(BoardData),
          config
        );
        alert("게시글이 수정되었습니다.");
        this.$router.push(
          `/clubs/${this.$route.params.clubId}/board/${this.$route.params.boardId}`
        );
      } catch (err) {
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
            this.post = res.data;
            this.formattedContent = this.formatContent(this.post.content); // 데이터를 받고 나서 가공

            this.title = this.post.title;
            this.editorData = this.post.content;
          });
      } catch (err) {
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
  height: 550px;
}

.ck-content {
  font-size: 15px;
}
</style>
