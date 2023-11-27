<template>
  <div class="centered-content">
    <v-card style="width: 80%; margin: 30px">
      <v-card-title class="d-flex justify-center">
        <h1 style="padding: 20px">CLUB CREATE</h1>
      </v-card-title>
      <v-card-text>
        <div class="d-flex flex-row">
          <!-- 모임 이름 입력 -->
          <v-text-field
            label="모임 이름"
            v-model="clubName"
            style="margin-right: 30px"
          ></v-text-field>

          <!-- 카테고리 선택 -->
          <v-select
            :items="categories"
            label="카테고리를 선택하세요"
            v-model="category"
            item-text="name"
            item-value="name"
            return-object
            style="margin-left: 30px"
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
        </div>

        <!-- 모임 설명 입력 -->
        <v-textarea
          label="모임 설명"
          v-model="description"
          no-resize
          :rows="2"
        ></v-textarea>

        <!-- 승인방식 선택 -->
        <v-radio-group v-model="approvalMethod" label="승인방식">
          <v-radio
            v-for="option in approvalMethods"
            :key="option"
            :label="option"
            :value="option"
          >
          </v-radio>
        </v-radio-group>

        <!-- 모집인원 입력 -->
        <v-text-field
          label="모임 인원 [30 ~ 100]"
          type="number"
          v-model="maximumPeople"
          :rules="[
            (value) =>
              (value >= 30 && value <= 100) ||
              '인원은 30 ~ 100명 사이로 입력해주세요.',
          ]"
        ></v-text-field>

        <!-- 태그 입력 -->
        <v-text-field
          label="태그 입력"
          v-model="tagInput"
          @keyup.enter="addTag"
          placeholder="태그를 입력 후 엔터를 누르세요."
        ></v-text-field>
        <v-chip-group>
          <v-chip
            v-for="(tag, index) in tagList"
            :key="index"
            class="mr-2"
            close
            @click="removeTag(index)"
          >
            {{ tag }}
          </v-chip>
        </v-chip-group>
        <v-file-input
          ref="fileInput"
          label="이미지 업로드"
          v-model="multipartFile"
          prepend-icon="mdi-camera"
          accept="image/*"
          style="width: 40%"
          @change="resizeImage"
        ></v-file-input>
        <v-card-actions>
          <v-btn style="color: rgb(255, 125, 125)" @click="cancel">취소</v-btn>
          <v-btn
            type="submit"
            @click="CreateClubSubmit"
            style="color: rgb(125, 255, 125)"
            >생성</v-btn
          >
        </v-card-actions>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      categories: [
        "음악",
        "요리",
        "운동",
        "영화",
        "독서",
        "여행",
        "언어교환",
        "예술"
      ],
      approvalMethods: ["FREE", "APPROVAL"],
      multipartFile: null, // 이미지 파일
      clubName: "", // 모임 이름
      category: "", // 카테고리
      description: "", // 모임 설명
      approvalMethod: "", // 승인 방식
      maximumPeople: "", // 모임 인원
      tagInput: "",
      tagList: [], // 태그 리스트
      defaultImagePath:
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZfPzoyq-RUkAEjwz13d0WckOBy0-iWE3PqQ&usqp=CAU",
    };
  },
  methods: {
    async CreateClubSubmit() {
      const formData = new FormData();

      if (!this.multipartFile) {
        // 이미지 파일을 불러옵니다.
        const response = await fetch(this.defaultImagePath);
        const defaultImageBlob = await response.blob();

        // File 객체로 변환하여 FormData에 추가합니다.
        const defaultImageFile = new File(
          [defaultImageBlob],
          "defaultImage.jpg"
        );
        formData.append("multipartFile", defaultImageFile);
      } else {
        formData.append("multipartFile", this.multipartFile);
      }

      formData.append("clubName", this.clubName);
      formData.append("category", this.category);
      formData.append("description", this.description);
      formData.append("approvalMethod", this.approvalMethod);
      formData.append("maximumPeople", this.maximumPeople);

      this.tagList.forEach((tag) => {
        formData.append(`tagList`, tag);
      });

      try {
        if (
          !this.clubName ||
          !this.category ||
          !this.description ||
          !this.approvalMethod ||
          !this.maximumPeople ||
          this.tagList.length === 0
        ) {
          alert("필수 정보를 모두 입력하세요.");
          return;
        }

        const access_token = this.$store.state.access_token;

        const config = {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${access_token}`,
          },
        };

        await this.$axios
          .post("/club-service/clubs", formData, config)
          .then((res) => {
            alert("모임이 생성되었습니다.");
            this.$router.push("/");
          });
      } catch (err) {
      }
    },
    addTag() {
      if (this.tagInput.trim() !== "") {
        this.tagList.push(this.tagInput.trim());
        this.tagInput = "";
      } else {
        alert("태그를 입력하세요.");
      }
    },

    removeTag(index) {
      this.tagList.splice(index, 1);
    },

    async cancel() {
      const confirmCancel = confirm(
        "작성한 내용이 모두 사라집니다. 모임생성을 취소하시겠습니까?"
      );

      if (confirmCancel) {
        // 사용자가 확인을 눌렀을 때
        this.$router.push("/"); // 홈 페이지로 이동 또는 원하는 경로로 이동
      }
    },

    async resizeImage(event) {
      const maxSize = 150; // 원하는 최대 크기 (가로 또는 세로)

      if (event.target && event.target.files && event.target.files.length > 0) {
        const file = event.target.files[0];

        const resizedBlob = await new Promise((resolve) => {
          const reader = new FileReader();
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

              const canvas = document.createElement("canvas");
              canvas.width = width;
              canvas.height = height;
              canvas.getContext("2d").drawImage(img, 0, 0, width, height);

              canvas.toBlob((blob) => {
                resolve(blob); // 비동기 작업 완료 후에 Promise를 resolve합니다.
              }, file.type);
            };
          };
          reader.readAsDataURL(file);
        });

        const formData = new FormData();
        formData.append("multipartFile", resizedBlob); // 조정된 이미지 Blob을 추가합니다.
        // 나머지 데이터도 FormData에 추가할 수 있습니다.
      }
    },
  },
};
</script>

<style scoped>
.centered-content {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center; /* 수평 가운데 정렬 */
  align-items: center; /* 수직 가운데 정렬 */
}
</style>
