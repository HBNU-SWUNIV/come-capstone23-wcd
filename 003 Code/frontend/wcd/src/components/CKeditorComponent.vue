<template>
  <ck-editor v-model="editorContent" :editor="editor" :config="editorConfig" />
</template>

<script>
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import CKEditor from '@ckeditor/ckeditor5-vue';

export default {
  components: {'ck-editor': CKEditor.component },
  data() {
    return {
      editorContent: "",
      editor: ClassicEditor,
      editorConfig: {
        toolbar: [ 'heading', '|','fontBackgroundColor', 'fontColor', 'fontSize', 'bold', 'italic', '|', 'alignment', 'bulletedList', 'numberedList', 'indent', 'outdent', '|', 'imageUpload', 'insertTable', 'link', '|', 'undo', 'redo' ],
        table: {
          contentToolbar: [ 'tableColumn', 'tableRow', 'mergeTableCells', 'tableProperties', 'tableCellProperties' ],
        },
        image: {
          resize: true,
          toolbar: [ 'imageStyle:alignLeft', 'imageStyle:alignRight', 'imageStyle:inline', 'imageStyle:side']
        },
      }
    }
  }
}
</script>

<!-- 넓이 높이 조절 -->
<style>
.ck.ck-editor {
  width: 100%
}
.ck-editor__editable {
  height: 600px;
  font-size: 12px;
  color: black;
}
</style>

