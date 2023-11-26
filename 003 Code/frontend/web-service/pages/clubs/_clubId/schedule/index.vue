<template>
  <div style="width: 100%">
    <div
      style="
        color: white;
        display: flex;
        flex-direction: row;
        justify-content: center;
      "
    >
      <div style="width: 900px; padding: 20px; padding-right: 20px">
        <FullCalendar :options="calendarOptions" @dateClick="openModal" />
        <v-dialog
          v-model="eventDialog"
          max-width="500"
          persistent
          @click:outside="eventDialog = false"
        >
          <v-card>
            <!-- 제목부분 스타일 조정 -->
            <v-card-title>
              {{ eventTitle }}
            </v-card-title>
            <v-divider></v-divider>

            <!-- 시간표시부분 스타일 조정 -->
            <v-card-text>
              <span class="font-weight-medium">{{ eventTimeRange }}</span>
            </v-card-text>

            <v-card-title>
              {{ eventDescription }}
            </v-card-title>

            <!-- 액션버튼 스타일 조정 -->
            <v-card-actions class="justify-end mt-5">
              <v-btn style="color:rgb(125, 125, 255)" @click="editEvent">수정</v-btn>
              <v-btn style="color:rgb(255, 125, 125)" @click="deleteEvent">삭제</v-btn>
              <v-btn @click="eventDialog = false"
                >닫기</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>
      </div>
      <!-- <v-btn @click="openModal">일정추가</v-btn> -->
      <CreateScheduleModal v-if="isModalVisible" @close-modal="closeModal" :modalData="dataForModal"/>
    </div>
  </div>
</template>
  
<script>
import FullCalendar from "@fullcalendar/vue";
import interactionPlugin from "@fullcalendar/interaction";
import dayGridPlugin from "@fullcalendar/daygrid";
import CreateScheduleModal from "@/components/CreateScheduleModal.vue";

export default {
  components: {
    FullCalendar,
    CreateScheduleModal,
  },
  data() {
    return {
      calendarOptions: {
        plugins: [interactionPlugin, dayGridPlugin],
        initialView: "dayGridMonth",
        defaultDate: new Date(),
        nowIndicator: true,
        editable: false,
        initialEvents: [],
        events: [],
        dayMaxEventRows: true, // for all non-TimeGrid views
        views: {
          timeGrid: {
            dayMaxEventRows: 1, // adjust to 6 only for timeGridWeek/timeGridDay
          },
        },
        eventClick: this.eventClick,
        eventTextColor: "white",
        dateClick: this.openModal,
        datesSet: this.handleDateChange,
      },
      dataForModal: "",
      isModalVisible: false,
      // schedules: [],
      yymm: this.formatDate(new Date()),
      eventDialog: false,
      eventTitle: "",
      eventDescription:"",
      scheduleId: null,
    };
  },
  methods: {
    eventClick(info) {
      this.scheduleId = info.event.id
      console.log(this.scheduleId)
      this.eventDialog = true;
      this.eventTitle = info.event.title;
      this.eventDescription = info.event.extendedProps.description;

      const startTime = info.event.start.toLocaleString();
      if (info.event.end) {
        const endTime = new Date(info.event.end.getTime() - 1).toLocaleString();
        this.eventTimeRange = `${startTime} - ${endTime}`;
      } else {
        eventTimeRange.value = startTime;
      }
    },
    openModal(arg) {
      // 날짜를 클릭할 때 모달을 표시
      this.isModalVisible = true;
      this.dataForModal = arg.dateStr;
      console.log(this.dataForModal)
    },
    closeModal() {
      // 모달을 닫을 때 호출되는 메서드
      this.isModalVisible = false;
      this.getSchedules();
    },
    async getSchedules() {
      try {
        const access_token = this.$store.state.access_token;
        const config = {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
          },
          params: {
            yymm: this.yymm,
          },
        };
        await this.$axios
          .get(
            `/schedule-service/clubs/${this.$route.params.clubId}/calendars`,
            config
          )
          .then((res) => {
            console.log(res.data);
            this.calendarOptions.events = res.data;
            console.log(this.calendarOptions.initialEvents);
          });
      } catch (err) {
        console.log(err);
      }
    },
    async deleteEvent() {
      const confirmation = confirm("삭제하시겠습니까?");

      if (confirmation) {
        try {
          const access_token = this.$store.state.access_token;
          const config = {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${access_token}`,
            },
          };
          await this.$axios
            .delete(
              `/schedule-service/clubs/${this.$route.params.clubId}/calendars/${this.scheduleId}`,
              config
            )
            .then((res) => {
              console.log(res);
              this.eventDialog = false;
            });
        } catch (err) {
          console.log(err);
        }
      }
    },
    async editEvent() {
      console.log("일정 수정 기능구현")
    },
    formatDate(date) {
      const year = String(date.getFullYear()).slice(-2); // Get the last two digits of the year
      const month = String(date.getMonth() + 1).padStart(2, "0");
      return `${year}${month}`;
    },
    handleDateChange(info) {
      // FullCalendar에서 새로운 날짜를 가져와서 yymm 업데이트
      const newDate = info.start;
      newDate.setDate(newDate.getDate() + 8);
      this.yymm = this.formatDate(newDate);
      this.getSchedules(); // 스케줄 업데이트
    },
  },
  created() {
    this.getSchedules();
  },
};
</script>

