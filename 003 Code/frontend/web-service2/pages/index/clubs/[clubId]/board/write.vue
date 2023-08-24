<template>
  <client-only>
  <div class="form-container">
    <div class="input-row">
      <label for="title" class="input-label">Title:</label>
      <input v-model="title" id="title" type="text" class="input-field"/>
    </div>

    <div class="input-row">
      <label for="category" class="input-label">Category:</label>
      <select v-model="category" id="category" class="input-field">
        <option disabled value="">선택해주세요</option>
        <option>일반</option>
        <option>공지사항</option>
      </select>
    </div>

      <CKEditorComponent v-model="content" class="editor"/>

    <button @¬click="sendToServer" class="submit-button">제출</button>
  </div>
  </client-only>
</template>

<script>
import axios from 'axios'
import CKEditorComponent from "@/components/CKeditorComponent"
import {ref} from 'vue'
import {useRoute} from 'vue-router'

export default {
  components: {
    CKEditorComponent
  },
  setup() {
    const title = ref('')
    const content = ref('')
    const category = ref('')
    const route = useRoute()
    const currentClubId = ref(route.params.clubId)

    const sendToServer = async () => {
      try {
        const response = await axios.post(`/board-service/clubs/${currentClubId.value}/posts`, {
          title: title.value,
          content: content.value,
          category: category.value
        })

        console.log(response)

        // if the response status is 200 or 201 (successful), then proceed
        if (response && (response.status === 200 || response.status === 201)) {
          navigateTo(`/clubs/${currentClubId.value}/board/${response.data}`);
        }
      } catch (error) {
        console.error(error)
      }
    }

    return {
      title,
      content,
      category,
      sendToServer
    }
  }
}
</script>