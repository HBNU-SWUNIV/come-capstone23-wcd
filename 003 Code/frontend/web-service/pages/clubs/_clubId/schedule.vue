<template>
  <div style="width: 100%;">
    <ClubNavigation />
    <div style="color: white; display: flex; flex-direction: row">
      <LeftClubNav />

      <div style="width:50%; padding:20px; padding-right: 50px;">
        <FullCalendar :options="calendarOptions" @dateClick="openModal"/>
      </div>

      <!-- <v-btn @click="openModal">일정추가</v-btn> -->
      <CreateScheduleModal v-if="isModalVisible" @close-modal="closeModal"/>
    </div>
  </div>
</template>
  
<script>
import LeftClubNav from "../../../components/LeftClubNav.vue";
import ClubNavigation from "@/components/ClubNavigation.vue";
import FullCalendar from '@fullcalendar/vue'
import interactionPlugin from '@fullcalendar/interaction'
import dayGridPlugin from '@fullcalendar/daygrid'
import CreateScheduleModal from '../../../components/CreateScheduleModal.vue';

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
        initialView: 'dayGridMonth',
        nowIndicator: true,
        editable: true,
        initialEvents: [
          { 
            title: 'new Schedule', 
            content: "new schedule",
            start: "2023-10-02T14:00:00",
            end: "2023-10-07T02:00:00",
            allDay: false,
          }
        ],
        eventTextColor: 'white',
        dateClick: this.openModal,
      },
      isModalVisible: false,
    }
  },
  methods: {
    openModal() {
      // 날짜를 클릭할 때 모달을 표시
      this.isModalVisible = true;
    },
    closeModal() {
      // 모달을 닫을 때 호출되는 메서드
      this.isModalVisible = false;
    }
  }
};
</script>

