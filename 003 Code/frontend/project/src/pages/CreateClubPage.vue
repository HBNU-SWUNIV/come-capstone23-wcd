<template>
  <div class="CreateClubPage">
    <div class="CreateClubWrap">
      <div class="CreateClubTitle">모임 생성</div>
      <form class="CreateClubForm" @submit.prevent="CreateClubSubmit">
        <div class="CreateClubBox">
          <input
            type="text"
            class="form-control"
            id="clubName"
            placeholder="모임 이름"
            v-model="clubName"
          />

          <div class="mt-3" style="font-size: 20px">
            <select
              id="category"
              v-model="selectedCategoryId"
              style="width: 500px; height: 50px; border-radius: 5px 5px 5px 5px"
            >
              <option value="">카테고리를 선택하세요</option>
              <option
                v-for="category in categories"
                :value="category.name"
                :key="category.id"
              >
                {{ category.name }}
              </option>
            </select>
          </div>

          <textarea
            type="text"
            class="form-control"
            placeholder="모임 설명"
            v-model="description"
          ></textarea>

          <!-- <input
            type="text"
            class="form-control"
            placeholder="모임 사진"
            v-model="mainImageUrl"
          /> -->

          <div
            class="d-flex flex-row justify-content-between mt-3"
            style="width: 500px; font-size: 20px"
          >
            <label class="ml-3">승인 방식</label>
            <div>
              <label
                class="mr-3"
                v-for="option in approvalOptions"
                :key="option.value"
              >
                <input
                  type="radio"
                  v-model="approvalMethod"
                  :value="option.value"
                />
                {{ option.label }}
              </label>
            </div>
          </div>

          <div
            class="d-flex flex-row justify-content-between mt-3"
            style="width: 500px"
          >
            <label
              class="ml-3"
              for="maxParticipants"
              style="font-size: 20px; margin-bottom: auto"
              >모임 인원 [30 ~ 100]</label
            >
            <input
              class="mr-3"
              type="number"
              v-model="maximumPeople"
              min="30"
              max="100"
              style="width: 100px; height: 40px; border-radius: 5px 5px 5px 5px"
            />
          </div>

          <!-- <input
            type="text"
            class="form-control"
            placeholder="모집 상황"
            v-model="recruitment"
          /> -->
        </div>

        <div class="CreateClubSubmitBox">
          <button class="btn btn-block text-center mt-3" type="submit">
            모임 생성
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import axios from "axios";
import router from "../router/index";

export default {
  setup() {
    const clubName = ref("");
    const description = ref("");
    const maximumPeople = ref("");

    const goClubHome = (id) => {
      router.push({
        name: "PageHome",
        params: {
          id: id,
        },
      });
    };

    const CreateClubSubmit = async () => {
      const ClubData = {
        clubName: clubName.value,
        category: selectedCategoryId.value,
        description: description.value,
        approvalMethod: approvalMethod.value,
        maximumPeople: maximumPeople.value,
      };
      try {
        if (clubName.value === "") {
          alert("모임이름을 입력하세요.");
          return;
        }
        await axios
          .post("http://localhost:3000/clubs", JSON.stringify(ClubData))
          .then((res) => {
            console.log(res);
            const createdClub = res.data;
            alert("모임이 생성되었습니다.");
            goClubHome(createdClub.id);
            window.location.reload()
          });
      } catch (err) {
        alert("다시 시도해주세요.");
        console.log(err);
      }
    };

    const categories = [
      { id: 1, name: "음악" },
      { id: 2, name: "요리" },
      { id: 3, name: "스포츠" },
      { id: 4, name: "영화" },
      { id: 5, name: "공예" },
      { id: 6, name: "독서" },
      { id: 7, name: "여행" },
      { id: 8, name: "봉사활동" },
      { id: 9, name: "언어교환" },
      { id: 10, name: "예술" },
    ];
    const selectedCategoryId = ref("");

    const approvalType = ref("manual");
    const approvalOptions = [
      { value: "manual", label: "수동 승인" },
      { value: "auto", label: "자동 승인" },
    ];
    const approvalMethod = ref("");

    const maxParticipants = ref(30);

    return {
      clubName,
      description,
      CreateClubSubmit,
      categories,
      selectedCategoryId,
      approvalType,
      approvalOptions,
      approvalMethod,
      maxParticipants,
      maximumPeople,
    };
  },
};
</script>

<style>
.CreateClubPage {
  display: flex;
  justify-content: center;
  height: 100%;
  width: 100%;
  font-family: "font";
}
.CreateClubWrap {
  height: 400px;
  width: 1000px;
}
.CreateClubTitle {
  font-size: 60px;
  text-align: center;
  color: white;
}
.CreateClubForm {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.CreateClubBox {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.CreateClubSubmitBox {
  display: flex;
  flex-direction: column;
  align-items: center;
  color: white;
}
.form-control {
  height: 50px;
  width: 500px;
  margin-top: 30px;
}
.btn {
  width: 500px;
  height: 50px;
  font-size: 23px;
  color: white;
  background-color: #383838;
}
textarea {
  height: 200px;
  resize: none; /* 크기 조절 비활성화 */
}
</style>