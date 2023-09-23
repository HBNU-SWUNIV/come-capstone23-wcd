<template>
    <ckeditor :editor="editor" v-model="data" />
  </template>
  
  <script>
  import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
  import CKEditor from '@ckeditor/ckeditor5-vue2';
  
  export default {
    components: {
      ckeditor: CKEditor.component,
    },
    data() {
      return {
        editor: ClassicEditor,
        data: '',
      };
    },
  };
  </script>
  