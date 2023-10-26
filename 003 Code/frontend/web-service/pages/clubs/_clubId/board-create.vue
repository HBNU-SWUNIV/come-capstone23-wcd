<template>
  <div style="width: 100%; height: 100%">
    <v-form style="height: 100%" @submit.prevent="CreateBoardSubmit">
      <div style="height: 100%; display: flex; flex-direction: column">
        <div style="height: 10%">
          <v-text-field
            v-model="title"
            label="게시글 제목"
            placeholder="게시글 제목"
            required
            type="text"
          ></v-text-field>
        </div>
        <ckeditor
          :editor="editor"
          v-model="editorData"
          :config="editorConfig"
        ></ckeditor>

        <div style="margin-top: auto; display: flex; flex-direction: row-reverse; align-items: flex-end; width: 100%; height: 10%">
          <div >
            <v-btn style="color: rgb(255, 125, 125)" @click="cancel"
            >취소
            </v-btn>
          </div>
          <div>
            <v-btn
              type="submit"
              :disabled="!editorData"
              style="color: rgb(125, 255, 125)"
            >게시
            </v-btn>
          </div>
        </div>
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
    };
  },
  methods: {
    async CreateBoardSubmit() {
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
          .post(
            `/board-service/clubs/${this.$route.params.clubId}/posts`,
            JSON.stringify(BoardData),
            config
          )
          .then((res) => {
            console.log(res);
            alert("게시글이 작성되었습니다.");
            this.$router.push(`/clubs/${this.$route.params.clubId}/board`);
          });
      } catch (err) {
        console.log(err);
      }
    },
    async cancel() {
      const confirmCancel = confirm(
        "작성한 내용이 모두 사라집니다. 게시글 작성을 취소하시겠습니까?"
      );

      if (confirmCancel) {
        // 사용자가 확인을 눌렀을 때
        this.$router.push(`/clubs/${this.$route.params.clubId}/board`); // 홈 페이지로 이동 또는 원하는 경로로 이동
      }
    },
  },
};
</script>

<style>
.ck-editor__editable {
  height: 620px;
}

.ck-content {
  font-size: 15px;
}
</style>
