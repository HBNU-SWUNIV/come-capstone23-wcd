<template>
  <div style="width: 100%">
    <ClubNavigation />
    <div style="color: white; display: flex; flex-direction: row">
      <LeftClubNav />

      <div style="width: 1000px; padding: 20px; padding-right: 50px">
        <FullCalendar :options="calendarOptions" @dateClick="openModal" />
      </div>
      <!-- <v-btn @click="openModal">일정추가</v-btn> -->
      <CreateScheduleModal v-if="isModalVisible" @close-modal="closeModal" />
    </div>
  </div>
</template>
  
<script>
import LeftClubNav from "@/components/LeftClubNav.vue";
import ClubNavigation from "@/components/ClubNavigation.vue";
import FullCalendar from "@fullcalendar/vue";
import interactionPlugin from "@fullcalendar/interaction";
import dayGridPlugin from "@fullcalendar/daygrid";
import CreateScheduleModal from "../../../components/CreateScheduleModal.vue";

export default {
  components: {
    ClubNavigation,
    LeftClubNav,
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

