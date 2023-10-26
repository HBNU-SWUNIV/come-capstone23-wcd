<template>
    <div style="width: 90%; padding: 30px; margin: auto">
      <h1 style="margin-bottom: 10px">공지사항 수정</h1>
      <v-form @submit.prevent="EditNoticeSubmit">
        <v-text-field
          v-model="title"
          label="공지사항 제목"
          placeholder="공지사항 제목"
          required
          type="text"
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
      async EditNoticeSubmit() {
        let NoticeData = {
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
            `/notice-service/clubs/${this.$route.params.clubId}/posts/${this.$route.params.noticeId}`,
            JSON.stringify(NoticeData),
            config
          );
  
          console.log(response);
          alert("게시글이 수정되었습니다.");
          this.$router.push(
            `/clubs/${this.$route.params.clubId}/notice/${this.$route.params.noticeId}`
          );
        } catch (err) {
          console.error(err);
        }
      },
      async getNoticeDetail() {
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
              `/notice-service/clubs/${this.$route.params.clubId}/posts/${this.$route.params.noticeId}`,
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
            `/clubs/${this.$route.params.clubId}/notice/${this.$route.params.noticeId}`
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
      this.getNoticeDetail();
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