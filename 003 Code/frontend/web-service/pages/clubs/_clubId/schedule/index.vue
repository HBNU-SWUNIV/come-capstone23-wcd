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
      </div>
      <!-- <v-btn @click="openModal">일정추가</v-btn> -->
      <CreateScheduleModal v-if="isModalVisible" @close-modal="closeModal" />
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
        eventClick: function (info) {
          const confirmation = confirm("삭제하시겠습니까?");
          console.log(info.event)
          if (confirmation) {
            this.deleteEvent(info.event.id);
          }
        },
        eventTextColor: "white",
        dateClick: this.openModal,
        datesSet: this.handleDateChange,
      },
      isModalVisible: false,
      // schedules: [],
      yymm: this.formatDate(new Date()),
    };
  },
  methods: {
    openModal() {
      // 날짜를 클릭할 때 모달을 표시
      this.isModalVisible = true;
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
    async deleteEvent(scheduleId){
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
            `/schedule-service/clubs/${this.$route.params.clubId}/calendars/${scheduleId}`,
            config
          )
          .then((res) => {
            console.log(res);
          });
      } catch (err) {
        console.log(err);
      }
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

