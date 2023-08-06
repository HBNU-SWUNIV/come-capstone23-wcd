<template>
  <div class="form-container">
    <div class="input-row">
      <label for="title" class="input-label">Title:</label>
      <input v-model="title" id="title" type="text" class="input-field"/>
    </div>

    <div class="input-row">
      <label for="category" class="input-label">Category:</label>
      <select v-model="category" id="category" class="input-field">
        <option disabled value="">Please select one</option>
        <option>General</option>
        <option>Announcement</option>
      </select>
    </div>

    <CKEditorComponent v-model="content" class="editor"/>

    <button @click="sendToServer" class="submit-button">Submit</button>
  </div>
</template>

<script>
import axios from 'axios'
import CKEditorComponent from "@/components/CKeditorComponent"
import router from '@/router' // router import
import { ref } from 'vue'
import { useRoute } from 'vue-router'

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
          await router.push({
            name: 'BoardDetail',
            params: {
              clubId: currentClubId.value,
              boardId: response.data
            }
          });
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

<style scoped>
.form-container {
  display: flex;
  flex-direction: column;
  align-items: start;
  padding: 20px;
  gap: 20px;
  max-width: 1400px;
  margin: 0 auto;
}

.input-row {
  display: flex;
  align-items: center;
  gap: 10px;
}

.input-label {
  width: 80px;
}

.input-field {
  flex-grow: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.editor {
  height: 600px;
  width: 100%;
}

.submit-button {
  padding: 10px 20px;
  background-color: #4caf50;
  border: none;
  color: white;
  cursor: pointer;
  border-radius: 4px;
}

.submit-button:hover {
  background-color: #45a049;
}
</style>