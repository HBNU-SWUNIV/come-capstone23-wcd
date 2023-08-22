<template>
  <v-navigation-drawer color="grey-lighten-3" rail>
    <client-only>

      <!-- 메인 이미지 버튼 -->
      <v-tooltip
          location="end"
      >
        <template #activator="{ props }">
          <v-btn
              title="메인"
              v-bind="props"
              class="d-block mx-auto my-5"
              size="38"
              color="grey-darken-1"
              large
              rounded="xl"
              @click="navigateTo('/')"
          >
            메인
          </v-btn>
        </template>
        <span>메인</span>
      </v-tooltip>

      <v-divider class="mx-3 my-5"></v-divider>

      <v-tooltip
          v-if="clubs"
          v-for="club in clubs"
          :key="club.id"
          location="end"
      >
        <template #activator="{ props }">
          <v-btn
              :key="club.id"
              :title="club.clubName"
              v-bind="props"
              class="d-block mx-auto my-5 club-button"
              size="38"
              color="grey-darken-1"
              large
              rounded="xl"
              @click="navigateTo(`/clubs/${club.id}`)"
          >
            <template v-if="club.mainImageUrl">
              <img :src="club.mainImageUrl" alt="Club Image" />
            </template>
            <template v-else>
              {{ club.clubName }}
            </template>
          </v-btn>
        </template>
        <span>{{ club.clubName }}</span>
      </v-tooltip>

      <!-- 모임 생성 버튼 -->
      <v-tooltip
          location="end"
      >
        <template #activator="{ props }">
          <v-btn
              title="모임 생성"
              v-bind="props"
              class="d-block mx-auto my-5"
              size="38"
              icon="mdi-plus"
              color="grey-darken-1"
              @click="showDialog = true"
              large
              rounded="xl"
          >
          </v-btn>
        </template>
        <span>모임 생성</span>
      </v-tooltip>

    </client-only>
  </v-navigation-drawer>

  <!-- 모임 생성 다이얼로그 -->
  <v-dialog v-model="showDialog" max-width="600px" @click:outside="showDialog = false">
    <v-card>
      <v-card-title>모임 생성</v-card-title>
      <v-card-text>
        <v-file-input
            label="이미지 업로드"
            v-model="imageFile"
            prepend-icon="mdi-camera"
            accept="image/*"
            @change="resizeImage"
        ></v-file-input>
        <!-- 모임 이름 입력 -->
        <v-text-field label="모임 이름" v-model="clubName"></v-text-field>

        <!-- 카테고리 선택 -->
        <v-select
            :items="categories"
            label="카테고리를 선택하세요"
            v-model="category"
            item-text="name"
            item-value="name"
            return-object
        >
          <option value="">카테고리를 선택하세요</option>
          <option
              v-for="selectedCategoryId in categories"
              :value="selectedCategoryId.name"
              :key="selectedCategoryId.id"
          >
            {{ selectedCategoryId.name }}
          </option>
        </v-select>

        <!-- 모임 설명 입력 -->
        <v-textarea label="모임 설명" v-model="description"></v-textarea>

        <!-- 승인방식 선택 -->
        <v-radio-group v-model="approvalMethod">
          <v-row>
          <v-radio
              v-for="option in approvalMethods"
              :key="option"
              :label="option"
              :value="option"
          >
          </v-radio>
          </v-row>
        </v-radio-group>

        <!-- 모집인원 입력 -->
        <v-text-field
            label="모임 인원 [30 ~ 100]"
            type="number"
            v-model="maximumPeople"
            :rules="[value => value >= 30 && value <= 100 || '인원은 30 ~ 100명 사이로 입력해주세요.']"
        ></v-text-field>

        <!-- 태그 입력 -->
        <v-text-field
            label="태그 입력"
            v-model="tagInput"
            @keyup.enter="addTag()"
            placeholder="태그를 입력 후 엔터를 누르세요."
        ></v-text-field>
        <v-chip-group>
          <v-chip
              v-for="(tag, index) in tags"
              :key="index"
              class="mr-2"
              close
              @click="removeTag(index)"
          >
            {{ tag }}
          </v-chip>
        </v-chip-group>
      </v-card-text>
      <v-card-actions>
        <v-btn color="blue darken-1" text @click="createClub">생성</v-btn>
        <v-btn text @click="showDialog = false">취소</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

</template>


<script>
import {ref} from 'vue';
import axios from 'axios';
import {useAuthStore} from "@/stores/auth";

export default {
  async setup() {
    const authStore = useAuthStore();
    const clubs = ref([]);
    const showDialog = ref(false);

    const imageFile = ref(null);
    const clubName = ref('');       // 모임 이름
    const categories = ref([ "음악" , "요리", "스포츠", "영화", "공예", "독서", "여행", "봉사활동", "언어교환", "예술"]);     // 카테고리 선택값
    const category = ref('');
    const description = ref('');    // 모임 설명
    const approvalMethods = ref(['FREE', 'APPROVAL']); // 승인 방식 선택값
    const approvalMethod = ref(''); // 승인 방식 선택값
    const maximumPeople = ref(30);    // 기본값 30으로 설정한 모집 인원
    const tagInput = ref('');       // 태그 입력 필드 값
    const tags = ref([]);           // 입력된 태그 목록


    async function fetchData() {
      try {
        const res = await axios.get(`/club-service/users/${authStore.userId}/clubs`);

        if (res.status === 200) {
          clubs.value = res.data;
        }
      } catch (error) {
        console.error("클럽 목록을 불러오는 중 오류가 발생했습니다.", error);
      }
    }

    async function createClub() {
      const formData = new FormData();
      if (imageFile && imageFile.value && Array.isArray(imageFile.value) && imageFile.value[0]) {
        formData.append("image", imageFile.value[0]);
      }
      formData.append("clubName", clubName.value);
      formData.append("category", category.value);
      formData.append("description", description.value);
      formData.append("approvalMethod", approvalMethod.value);
      formData.append("maximumPeople", maximumPeople.value);
      formData.append("tagList", tags.value);

      // 태그들을 FormData에 추가
      tags.value.forEach((tag) => {
        formData.append(`tagList`, tag);
      });

      try {
        const config = {
          headers: {
            "Content-Type": "multipart/form-data",
          }
        }
        const res = await axios.post("/club-service/clubs", formData, config);
        console.log(res);
        alert("모임이 생성되었습니다.");

      } catch (err) {
        alert("다시 시도해주세요.");
        console.log(err);
      }
    }

    await fetchData();

    function addTag() {
      if (this.tagInput.trim() !== '') {
        this.tags.push(this.tagInput.trim());
        tagInput.value = tags.value.join(" ") + " " + tagInput.value;
        // this.tagInput = '';
      }
    }
    function removeTag(index) {
      this.tags.pop(index);
    }

    const resizeImage = async (event) => {
      const file = event.target.files[0];
      const maxSize = 150; // 원하는 최대 크기 (가로 또는 세로)

      if (file) {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = (event) => {
          const img = new Image();
          img.src = event.target.result;

          img.onload = () => {
            let width = img.width;
            let height = img.height;

            if (width > height) {
              if (width > maxSize) {
                height *= maxSize / width;
                width = maxSize;
              }
            } else {
              if (height > maxSize) {
                width *= maxSize / height;
                height = maxSize;
              }
            }

            const canvas = document.createElement('canvas');
            canvas.width = width;
            canvas.height = height;
            canvas.getContext('2d').drawImage(img, 0, 0, width, height);

            canvas.toBlob((blob) => {
              // blob 객체는 크기가 조정된 이미지입니다. 필요에 따라 사용하세요.
            }, file.type);
          };
        };
      }
    };

    return {
      clubs,
      showDialog,
      imageFile,
      clubName,
      categories,
      category,
      description,
      approvalMethods,
      approvalMethod,
      maximumPeople,
      tagInput,
      tags,
      resizeImage,
      createClub,
      addTag,
      removeTag,
    }
  }
}
</script>

<style scoped>
.club-button {
  white-space: nowrap;
  overflow: hidden;
}
</style>