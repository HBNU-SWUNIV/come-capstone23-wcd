<template>
  <div class="modal-overlay">
    <div class="modal">
      <h1>일정</h1>
      <div style="margin-top: 10px">
        <label for="event-title">일정 제목:</label>
        <input id="event-title" v-model="title" type="text" />
      </div>
      <div>
        <label for="event-content">일정 내용:</label>
        <input id="event-content" v-model="content" />
      </div>
      <div>
        <label for="event-start">시작 날짜:</label>
        <input id="event-start" v-model="start" type="date" />
      </div>
      <div>
        <label for="event-end">종료 날짜:</label>
        <input id="event-end" v-model="end" type="date" />
      </div>
      <!-- <div>
        <label for="event-allDay">하루 종일:</label>
        <input id="event-allDay" v-model="eventData.allDay" type="checkbox">
      </div> -->
      <v-btn @click="addEvent">일정 추가</v-btn>
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
      end: "",
      allDay: false,
    };
  },
  methods: {
    async addEvent() {
      let ScheduleData = {
        title: this.title,
        description: this.content,
        start: this.start + "T14:00:00",
        end: this.end + "T14:00:00",
        allDay: false,
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
            "Content-Type": "application/json", // JSON 형식으로 변경
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
  background-color: rgb(255, 255, 255);
  height: 500px;
  width: 500px;
  margin-top: 10%;
  padding: 60px;
  border-radius: 20px;
  color: black;
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
</style>