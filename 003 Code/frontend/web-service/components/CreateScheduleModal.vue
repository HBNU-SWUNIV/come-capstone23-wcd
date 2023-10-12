<template>
  <div class="modal-overlay">
    <div class="modal">
      <h1>일정</h1>
      <v-form @submit.prevent="addEvent">
        <div style="margin-top: 30px">
          <v-text-field
            v-model="title"
            label="일정 제목"
            placeholder="일정 제목"
            required
            type="text"
          ></v-text-field>
        </div>
        <div>
          <v-text-field
            v-model="content"
            label="일정 내용"
            placeholder="일정 내용"
            required
            type="text"
          ></v-text-field>
        </div>
        <div style="text-align: left">
          <label for="event-start">시작 날짜 :</label>
          <input
            id="event-start"
            v-model="start"
            type="date"
            style="background-color: white"
          />
          <select
            class="select-style"
            id="event-start-hour"
            v-model="startHour"
          >
            <option
              v-for="hour in 24"
              :key="hour"
              :value="String(hour - 1).padStart(2, '0')"
            >
              {{ String(hour - 1).padStart(2, "0") }}
            </option>
          </select>
          시
          <select
            class="select-style"
            id="event-start-minute"
            v-model="startMinute"
          >
            <option
              v-for="minute in 60"
              :key="minute"
              :value="String(minute - 1).padStart(2, '0')"
            >
              {{ String(minute - 1).padStart(2, "0") }}
            </option>
          </select>
          분
        </div>
        <div style="text-align: left">
          <label for="event-end">종료 날짜 :</label>
          <input
            id="event-end"
            v-model="end"
            type="date"
            style="background-color: white"
          />
          <select class="select-style" id="event-end-hour" v-model="endHour">
            <option
              v-for="hour in 24"
              :key="hour"
              :value="String(hour - 1).padStart(2, '0')"
            >
              {{ String(hour - 1).padStart(2, "0") }}
            </option>
          </select>
          시
          <select
            class="select-style"
            id="event-end-minute"
            v-model="endMinute"
          >
            <option
              v-for="minute in 60"
              :key="minute"
              :value="String(minute - 1).padStart(2, '0')"
            >
              {{ String(minute - 1).padStart(2, "0") }}
            </option>
          </select>
          분
        </div>
        <div style="text-align: left">
          <label for="event-allDay">하루 종일 (00:00 ~ 23:59) : </label>
          <input
            id="event-allDay"
            v-model="allDay"
            type="checkbox"
            @change="updateTimeForAllDay"
          />
        </div>
        <v-btn
          type="submit"
          :disabled="isAddEventButtonDisabled"
          style="color: rgb(125, 255, 125)"
          >일정 추가</v-btn
        >
      </v-form>
    </div>
    <div class="close" @click="$emit('close-modal')">
      <v-icon>mdi-close</v-icon>
    </div>
  </div>
</template>
  
<script>
export default {
  data() {
    return {
      title: "",
      content: "",
      start: "",
      startHour: "00",
      startMinute: "00",
      end: "",
      endHour: "00",
      endMinute: "00",
      allDay: false,
    };
  },
  methods: {
    async addEvent() {
      if (this.allDay) {
        // 하루 종일로 체크되었을 때 시간 설정
        this.startHour = "00";
        this.startMinute = "00";
        this.endHour = "23";
        this.endMinute = "59";
      }

      const startDateTime = `${this.start}T${this.startHour}:${this.startMinute}:00`;
      const endDateTime = `${this.end}T${this.endHour}:${this.endMinute}:00`;

      let ScheduleData = {
        title: this.title,
        content: this.content,
        start: startDateTime,
        end: endDateTime,
        allDay: this.allDay,
      };
      try {
        if (this.title === "") {
          alert("제목을 입력하세요.");
          return;
        }
        if (this.content === "") {
          alert("내용을 입력하세요.");
          return;
        }
        const access_token = this.$store.state.access_token;
        const config = {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
          },
        };

        await this.$axios
          .post(
            `/schedule-service/clubs/${this.$route.params.clubId}/calendars`,
            JSON.stringify(ScheduleData),
            config
          )
          .then((res) => {
            console.log(res);
            alert("일정이 생성되었습니다.");
            // 모달을 닫는 이벤트를 발생시킵니다.
            this.$emit("close-modal");
          });
      } catch (err) {
        console.log(err);
      }
    },
    updateTimeForAllDay() {
      // 하루 종일 체크박스가 변경될 때 호출되는 메소드
      if (this.allDay) {
        // 체크되면 시간을 00:00로 설정
        this.startHour = "00";
        this.startMinute = "00";
        this.endHour = "23";
        this.endMinute = "59";
      } else {
        // 체크 해제되면 이전 값으로 복원
        this.startHour = "00";
        this.startMinute = "00";
        this.endHour = "00";
        this.endMinute = "00";
      }
    },
  },
  computed: {
    // 모든 필수 필드가 비어있으면 true를 반환
    isAddEventButtonDisabled() {
      return (
        !this.title ||
        !this.content ||
        !this.start ||
        !this.startHour ||
        !this.startMinute ||
        !this.end ||
        !this.endHour ||
        !this.endMinute
      );
    },
  },
};
</script>


<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  background-color: #000000da;
  color: rgb(0, 0, 0);
  z-index: 9999;
}

.modal {
  text-align: center;
  background-color: rgb(0, 0, 0);
  height: 550px;
  width: 500px;
  margin-top: 10%;
  padding: 40px;
  border-radius: 20px;
  color: rgb(255, 255, 255);
  border: 1px solid white;
}
.close {
  margin: 10% 0 0 16px;
  cursor: pointer;
}

h6 {
  font-weight: 500;
  font-size: 28px;
  margin: 20px 0;
}

p {
  font-size: 16px;
  margin: 20px 0;
}

button {
  background-color: #ac003e;
  width: 150px;
  height: 40px;
  color: white;
  font-size: 14px;
  border-radius: 16px;
  margin-top: 50px;
}

input {
  border: 1px solid black;
  margin-top: 20px;
  border-radius: 5px;
  padding: 5px;
}
.select-style {
  width: 50px;
  height: 38px;
  background-color: white;
  border-radius: 5px;
  padding:5px;
}
</style>