<template>
  <ck-editor v-model="editorContent" :editor="editor" :config="editorConfig" />
</template>

<script>
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import CKEditor from '@ckeditor/ckeditor5-vue';
import MyCustomUploadAdapterPlugin from '/uploadAdapter/UploadAdapter.js';
// import { SimpleUploadAdapter } from '@ckeditor/ckeditor5-upload';
// import { Image, ImageCaption, ImageResize, ImageStyle, ImageToolbar } from '@ckeditor/ckeditor5-image';

export default {
  components: {'ck-editor': CKEditor.component },
  data() {
    return {
      editorContent: "",
      editor: ClassicEditor,
      editorConfig: {
        // plugins: [ Image, ImageToolbar, ImageCaption, ImageStyle, ImageResize, LinkImage ],
        // plugins: [SimpleUploadAdapter],
        extraPlugins: [MyCustomUploadAdapterPlugin],
        toolbar: [ 'heading', '|'
          ,'fontBackgroundColor', 'fontColor', 'fontSize', 'bold', 'italic', '|',
          'alignment', 'bulletedList', 'numberedList', 'indent', 'outdent', '|',
          'imageUpload', 'insertTable', 'link', '|',
          'undo', 'redo', '|',
          // 'imageStyle:block', 'imageStyle:side', '|',
          // 'toggleImageCaption', 'imageTextAlternative'
        ],
        table: {
          contentToolbar: [ 'tableColumn', 'tableRow', 'mergeTableCells', 'tableProperties', 'tableCellProperties' ],
        },
        image: {
          // resize: true,
          toolbar: [ 'imageStyle:alignLeft', 'imageStyle:alignRight', 'imageStyle:inline', 'imageStyle:side'],
          upload: {
            types: [ 'jpeg', 'png', 'gif', 'bmp' ],
            url: '/path/to/upload-endpoint'  // 백엔드 업로드 엔드포인트 URL을 여기에 지정해야 합니다.
          }
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