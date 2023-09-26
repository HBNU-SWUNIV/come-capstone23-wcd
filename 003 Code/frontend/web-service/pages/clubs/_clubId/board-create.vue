<template>
  <div style="width: 90%; padding: 30px">
    <h1 style="margin-bottom: 20px;">게시글 작성</h1>
    <CKEditor style="color: black" />
    <v-btn style="margin:10px;">취소</v-btn>
    <v-btn style="margin:10px;">게시</v-btn>
  </div>
</template>

<script>
import CKEditor from "~/components/CKEditor.vue";

export default {
  components: {
    CKEditor,
  },
};
</script>

<style>
.ck-editor__editable {
  height: 400px;
}
.ck-content {
  font-size: 12px;
}
</style>