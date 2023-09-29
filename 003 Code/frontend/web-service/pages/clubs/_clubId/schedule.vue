<template>
  <div style="width: 100%;">
    <ClubNavigation />
    <div style="color: white; display: flex; flex-direction: row">
      <LeftClubNav />

      <div style="width:660px; padding:20px;">
        <FullCalendar :options="calendarOptions" @dateClick="handleDateClick"/>
      </div>
    </div>
  </div>
</template>
  
  <script>
import LeftClubNav from "../../../components/LeftClubNav.vue";
import ClubNavigation from "../../../components/ClubNavigation.vue";
import FullCalendar from '@fullcalendar/vue'
import interactionPlugin from '@fullcalendar/interaction'
import dayGridPlugin from '@fullcalendar/daygrid'

export default {
  components: {
    ClubNavigation,
    LeftClubNav,
    FullCalendar,
  },
  data() {
    return {
      calendarOptions: {
        plugins: [interactionPlugin, dayGridPlugin],
        initialView: 'dayGridMonth',
        nowIndicator: true,
        editable: true,
        initialEvents: [
          { title: 'nice event', start: new Date() }
        ],
        eventTextColor: 'white'
      }
    }
  },
  
};
</script>