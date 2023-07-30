<template>
  <div class="app">
    <div class="calendar">
      <div class="headerWrap">
        <button class="calendarBtn mr-5" @click="prevMonth">이전 달</button>
        <h2 style="font-size: 40px">
          {{ currentYear }} 년 &nbsp; {{ currentMonth + 1 }} 월
        </h2>
        <button class="calendarBtn ml-5" @click="nextMonth">다음 달</button>
      </div>
      <div class="weekdays">
        <div
          style="border: none; height: 40px"
          v-for="day in weekdays"
          :key="day"
          :class="{ weekday: true, saturday: day === 6, sunday: day === 0 }"
        >
          {{ dayNames[day] }}
        </div>
      </div>
      <div class="days">
        <!-- 빈 칸 만들기 -->
        <div
          v-for="emptyDay in firstDay"
          :key="'empty' + emptyDay"
          class="empty-day"
        ></div>
        <!-- 날짜 표시 -->
        <div
          v-for="(day, index) in days"
          :key="index"
          :class="getDayClass(day)"
          @click="goCreateSchedule(day)"
          style="cursor: pointer"
        >
          <div>{{ day }}</div>
          <div v-for="schedule in schedules" :key="schedule">
            <div v-if="(schedule.clubId === $route.params.id) && (schedule.day === day)" style="font-size: 15px; text-align: left; margin-left: 5px">
              일정내용
              {{ schedule.content }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from "vue";
import router from "@/router/index";
import axios from "axios";

export default {
  setup() {
    const currentDate = ref(new Date());
    const currentYear = ref(currentDate.value.getFullYear());
    const currentMonth = ref(currentDate.value.getMonth());

    const selectedYear = ref(null);
    const selectedMonth = ref(null);
    const selectedDay = ref(null);

    // 현재 월의 첫째 날짜의 요일과 마지막 날짜 구하기
    const firstDay = computed(() =>
      new Date(currentYear.value, currentMonth.value, 1).getDay()
    );
    const lastDate = computed(() =>
      new Date(currentYear.value, currentMonth.value + 1, 0).getDate()
    );

    // 해당 월의 날짜 배열 구하기
    const days = computed(() =>
      Array.from({ length: lastDate.value }, (_, i) => i + 1)
    );

    // 요일 배열 (일요일부터 시작하도록 변경)
    const weekdays = [0, 1, 2, 3, 4, 5, 6]; // 0: 일요일, 1: 월요일, ..., 6: 토요일
    const dayNames = ["일", "월", "화", "수", "목", "금", "토"]; // 첫 번째 요소를 일요일로 변경

    const getDayClass = (day) => {
      const date = new Date(currentYear.value, currentMonth.value, day);
      const weekday = date.getDay();

      if (weekday === 0) {
        return "sunday";
      } else if (weekday === 6) {
        return "saturday";
      } else {
        return "day";
      }
    };

    const prevMonth = () => {
      if (currentMonth.value === 0) {
        currentYear.value -= 1;
        currentMonth.value = 11;
      } else {
        currentMonth.value -= 1;
      }
    };

    const nextMonth = () => {
      if (currentMonth.value === 11) {
        currentYear.value += 1;
        currentMonth.value = 0;
      } else {
        currentMonth.value += 1;
      }
    };

    const goCreateSchedule = (day) => {
      router.push({
        name: "CreateSchedulePage",
        params: {
          day: day,
          month: currentMonth.value + 1,
          year: currentYear.value,
        },
      });
      // console.log(day)
      // console.log(currentMonth.value + 1)
      // console.log(currentYear.value)
    };

    const schedules = ref("");

    const getSchedule = () => {
      axios
        .get("http://localhost:3000/schedules")
        .then((res) => {
          console.log(res.data);
          schedules.value = res.data;
        })
        .catch((error) => {
          console.error(error);
        });
    };

    onMounted(() => {
      // 현재 년도와 월에 따른 달력 생성
      currentYear.value = currentDate.value.getFullYear();
      currentMonth.value = currentDate.value.getMonth();
      getSchedule();
    });

    return {
      currentYear,
      currentMonth,
      firstDay,
      dayNames,
      days,
      weekdays,
      getDayClass,
      prevMonth,
      nextMonth,
      goCreateSchedule,
      selectedYear,
      selectedMonth,
      selectedDay,
      schedules,
    };
  },
};
</script>

<style>
/* 필요한 경우 커스텀 스타일을 추가하세요 */
.app {
  max-width: 1400px;
  margin: 0 auto;
  padding: 20px;
  border: none;
  font-size: 25px;
}
.header {
  max-width: 1400px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.headerWrap {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-top: 25px;
}
.calendar {
  height: 100%;
  text-align: center;
  font-size: 25px;
}

.weekdays {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 5px;
  margin-top: 40px;
}

.weekday {
  font-weight: bold;
  padding: 5px;
}

.saturday {
  color: rgb(131, 131, 255); /* 토요일은 파란색으로 */
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  height: 125px;
}

.sunday {
  color: rgb(248, 124, 124); /* 일요일은 빨간색으로 */
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.days {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 5px;
  margin-top: 15px;
}

.day {
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  height: 125px;
}
.calendarBtn {
  height: 45px;
  background-color: #383838;
  border: none;
  border-radius: 10px;
  color: white;
}
</style>
