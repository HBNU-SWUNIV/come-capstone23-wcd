<template>
  <div style="width: 90%; padding: 30px;">
    <h1>게시글 작성</h1>
    <CKEditor style="color: black" />
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
