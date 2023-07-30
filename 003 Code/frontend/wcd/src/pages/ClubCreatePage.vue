<template>
  <div class="ClubCreatePage">
    <div class="Club-Create-Wrap">
      <label class="Club-Create-Title">모임 생성</label>
      <form class="Club-Create-Form" @submit.prevent="CreateClubSubmit">
        <div class="ClubName-Category-Box">
          <input
            type="text"
            class="Club-Name-Input"
            placeholder="모임 이름"
            v-model="clubName"
          />

          <select
            class="Category-Box"
            id="selectedCategoryId"
            v-model="category"
          >
            <option value="">카테고리를 선택하세요</option>
            <option
              v-for="selectedCategoryId in categories"
              :value="selectedCategoryId.name"
              :key="selectedCategoryId.id"
            >
              {{ selectedCategoryId.name }}
            </option>
          </select>
        </div>
        <textarea
          type="text"
          class="Description-Box"
          placeholder="모임 설명"
          v-model="description"
        ></textarea>

        <!-- <input
              type="text"
              class="form-control"
              placeholder="모임 사진"
              v-model="mainImageUrl"
            /> -->

        <div class="Approval-Box">
          <label class="Approval-Title">승인 방식</label>
          <div>
            <label
              class="Approval-Label"
              v-for="option in approvalOptions"
              :key="option.value"
            >
              <input
                class="Approval-Radio"
                type="radio"
                v-model="approvalMethod"
                :value="option.value"
              />
              {{ option.label }}
            </label>
          </div>
        </div>

        <div class="MaximumPeople-Box">
          <label class="MaximumPeople-Label">모임 인원 [30 ~ 100]</label>
          <input
            class="MaximumPeople-Input"
            type="number"
            v-model="maximumPeople"
            min="30"
            max="100"
          />
        </div>

        <div class="Btn-Box">
          <button class="Cancel-Btn" @click="CancelSubmit">취소</button>
          <button class="Create-Btn" type="submit">생성</button>
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
        name: "ClubHomePage",
        params: {
          id: id,
        },
      });
    };

    const CancelSubmit = () => {
      router.push({
        name: "HomePage",
      });
    };

    const CreateClubSubmit = async () => {
      const ClubData = {
        hostId: "testId",
        clubName: clubName.value,
        category: category.value,
        description: description.value,
        mainImageUrl: "test",
        approvalMethod: approvalMethod.value,
        maximumPeople: maximumPeople.value,
        recruitment: true,
        createdAt: "2020-01-02",
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
    const category = ref("");

    const approvalType = ref("manual");
    const approvalOptions = [
      { value: "manual", label: "수동 승인" },
      { value: "auto", label: "자동 승인" },
    ];
    const approvalMethod = ref("");

    return {
      clubName,
      description,
      goClubHome,
      CancelSubmit,
      CreateClubSubmit,
      categories,
      category,
      approvalType,
      approvalOptions,
      approvalMethod,
      maximumPeople,
    };
  },
};
</script>
  
<style>
.ClubCreatePage {
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
}
.Club-Create-Title {
  width: 100%;
  text-align: center;
  font-size: 60px;
}
.Club-Create-Wrap {
  height: 1000px;
  width: 1000px;
}
.ClubName-Category-Box {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.Club-Name-Input {
  height: 50px;
  width: 490px;
  font-size: 20px;
  border-radius: 5px 5px 5px 5px;
}
.Category-Box {
  width: 50px;
  width: 490px;
  font-size: 20px;
  border-radius: 5px 5px 5px 5px;
}
textarea {
  height: 200px;
  width: 1000px;
  resize: none;
  font-size: 25px;
  border-radius: 5px 5px 5px 5px;
  margin-top: 20px;
}
.Approval-Box {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 10px;
}
.Approval-Title {
  font-size: 25px;
}
.Approval-Label {
  font-size: 25px;
}
.Approval-Radio {
  height: 25px;
  width: 25px;
  margin-left: 20px;
}
.MaximumPeople-Box {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 10px;
}
.MaximumPeople-Label {
  font-size: 25px;
}
.MaximumPeople-Input {
  height: 42px;
  width: 200px;
  font-size: 25px;
  text-align: center;
  border-radius: 5px 5px 5px 5px;
}
.Btn-Box {
  display: flex;
  flex-direction: row;
  justify-content: right;
  margin-top: 10px;
}
.Cancel-Btn {
  height: 50px;
  width: 80px;
  background-color: red;
  border: none;
  border-radius: 5px 5px 5px 5px;
  color: white;
  font-size: 20px;
  margin-left: 10px;
}
.Create-Btn {
  height: 50px;
  width: 80px;
  background-color: green;
  border: none;
  border-radius: 5px 5px 5px 5px;
  color: white;
  font-size: 20px;
  margin-left: 10px;
}
</style>