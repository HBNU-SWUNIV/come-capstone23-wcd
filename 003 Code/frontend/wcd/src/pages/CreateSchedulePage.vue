<template>
  <div class="SchedulePage">
    <label class="DateTitle">{{ year }}년 {{ month }}월 {{ day }}일</label>
    <form @submit.prevent="ScheduleSubmit">
      <div class="CreateScheduleBox">
        <textarea
          class="ScheduleContent"
          v-model="scheduleContent"
          placeholder="일정 내용"
        ></textarea>
      </div>
      <div class="ScheduleBtnArea">
        <button
          class="button"
          style="background-color: red"
          @click="CancelSchedule"
        >
          취소
        </button>
        <button class="button" style="background-color: green" type="submit">
          생성
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import { useRoute } from "vue-router";
import { ref } from "vue";
import router from "@/router/index";
import axios from "axios";

export default {
  setup() {
    const route = useRoute();
    // 라우트 파라미터를 통해 선택한 날짜 정보에 접근합니다
    // const clubId = route.params.id
    const year = route.params.year;
    const month = route.params.month;
    const day = route.params.day;
    const clubId = route.params.id;

    console.log("선택한 년도:", year);
    console.log("선택한 월:", month);
    console.log("선택한 일:", day);
    console.log("현재 모임아이디:", clubId);

    const CancelSchedule = () => {
      router.push({
        name: "ClubSchedulePage",
        params: {
          id: clubId,
        },
      });
    };

    const scheduleContent = ref("");

    const ScheduleSubmit = async () => {
      const scheduleData = {
        clubId: clubId,
        year: year,
        month: month,
        day: day,
        content: scheduleContent.value,
      };
      try {
        if (scheduleContent.value === "") {
          alert("일정을 입력하세요.");
          return;
        }
        await axios
          .post("http://localhost:3000/schedules", scheduleData)
          .then((res) => {
            console.log(res);
            alert("일정이 생성되었습니다.");

            router.push({
              name: "ClubSchedulePage",
              params: {
                id: clubId,
              },
            });
          });
      } catch (err) {
        alert("다시 시도해주세요.");
        console.log(err);
      }
    };

    return {
      year,
      month,
      day,
      CancelSchedule,
      scheduleContent,
      ScheduleSubmit,
    };
  },
};
</script>

<style>
.DateTitle {
  height: 150px;
  width: 100%;
  text-align: center;
  font-size: 50px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.CreateScheduleBox {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.ScheduleContent {
  height: 100px;
  width: 700px;
}
.ScheduleBtnArea {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-top: 30px;
}
</style>