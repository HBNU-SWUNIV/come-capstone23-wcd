<template>
  <div>
    <FullCalendar :options="calendarOptions"/>

    <!-- 일정 정보창 -->
    <v-dialog v-model="eventDialog" max-width="350" persistent @click:outside="eventDialog = false">
      <v-card>
        <v-card-title class="headline blue--text text--darken-2">
          <v-icon class="mr-2">event</v-icon>
          {{ eventTitle }}
        </v-card-title>
        <v-divider></v-divider>
        <v-card-text class="subtitle-1 grey--text text--darken-1 pa-4">
          <v-icon class="mr-2">access_time</v-icon>
          {{ eventTimeRange }}
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn icon color="blue darken-1" @click="editEvent">
            <v-icon>edit</v-icon>
          </v-btn>
          <v-btn icon color="red darken-1" @click="deleteEvent">
            <v-icon>delete</v-icon>
          </v-btn>
          <v-btn text color="green darken-1" @click="eventDialog = false">Close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- 일정 추가 창 -->
    <v-dialog v-model="createDialog" max-width="500px">
      <v-card>
        <v-card-title>일정을 입력하세요.</v-card-title>
        <v-card-text>
          <v-text-field label="일정 제목" v-model="newEventTitle"></v-text-field>
          <v-text-field label="일정 내용" v-model="newEventDescription"></v-text-field>

          <!-- 시작 날짜와 시간 -->
          <v-card-text>시작 날짜와 시간</v-card-text>
          <v-row>
            <v-col cols="6">
              <v-text-field type="date" id="start-datetime-date" v-model="newEventStartDate"/>
            </v-col>
            <v-col cols="6">
              <!-- 하루 종일 체크박스가 체크되면 disabled 속성이 true가 되어 입력이 비활성화됩니다 -->
              <v-text-field type="time" id="start-datetime-time" v-model="newEventStartTime" :disabled="newEventAllDay"/>
            </v-col>
          </v-row>

          <!-- 종료 날짜와 시간 -->
          <v-card-text>종료 날짜와 시간</v-card-text>
          <v-row>
            <v-col cols="6">
              <v-text-field type="date" id="end-datetime-date" v-model="newEventEndDate"/>
            </v-col>
            <v-col cols="6">
              <!-- 하루 종일 체크박스가 체크되면 disabled 속성이 true가 되어 입력이 비활성화됩니다 -->
              <v-text-field type="time" id="end-datetime-time" v-model="newEventEndTime" :disabled="newEventAllDay"/>
            </v-col>
          </v-row>


          <v-checkbox v-model="newEventAllDay" label="하루 종일"></v-checkbox>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="warning" @click="createEvent">추가</v-btn>
          <v-btn text @click="createDialog = false">취소</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </div>
</template>

<script>
import FullCalendar from '@fullcalendar/vue3';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import timeGridPlugin from '@fullcalendar/timegrid';
import momentPlugin from '@fullcalendar/moment';
import momentTimezonePlugin from '@fullcalendar/moment-timezone';

import {ref} from 'vue';

export default {
  components: {
    FullCalendar
  },
  setup() {
    const events = ref([]);
    const eventDialog = ref(false);
    const createDialog = ref(false);
    const selectedDate = ref("");
    const eventTitle = ref("");
    const eventTimeRange = ref("");
    const newEventTitle = ref("");
    const newEventDescription = ref("");
    const newEventStartDate = ref("");
    const newEventStartTime = ref("");
    const newEventEndDate = ref("");
    const newEventEndTime = ref("");
    const newEventAllDay = ref(false);

    function eventClick(info) {
      eventTitle.value = info.event.title;

      const startTime = info.event.start.toLocaleString();
      if (info.event.end) {
        const endTime = new Date(info.event.end.getTime() - 1).toLocaleString();
        eventTimeRange.value = `${startTime} - ${endTime}`;
      } else {
        eventTimeRange.value = startTime;
      }

      eventDialog.value = true;
    }

    function editEvent() {
      // 여기에 이벤트 수정 로직 추가
    }

    function deleteEvent() {
      // 여기에 이벤트 삭제 로직 추가
    }

    function handleDateClick(arg) {
      createDialog.value = true;
      const newEventStartDateTime = arg.date;
      newEventStartDate.value = formatDate(newEventStartDateTime);
      newEventStartTime.value = formatTime(newEventStartDateTime);
      addHours(arg.date, 1);
      const newEventEndDateTime = arg.date
      newEventEndDate.value = formatDate(newEventEndDateTime);
      newEventEndTime.value = formatTime(newEventEndDateTime);
    }

    // 로컬 시간대에 따라 YYYY-MM-DD 형식의 날짜 문자열 반환
    function formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    }

    // 로컬 시간대에 따라 HH:MM:SS 형식의 시간 문자열 반환
    function formatTime(date) {
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = String(date.getSeconds()).padStart(2, '0');
      return `${hours}:${minutes}:${seconds}`;
    }

    function addHours(date, hours) {
      date.setTime(date.getTime() + hours * 60 * 60 * 1000);

      return date;
    }

    function createEvent() {
      const newEventStartDateTime = new Date(`${newEventStartDate.value}T${newEventStartTime.value}`);
      const newEventEndDateTime = new Date(`${newEventEndDate.value}T${newEventEndTime.value}`);
      events.value.push({
        title: newEventTitle,
        start: newEventStartDateTime,
        end: newEventEndDateTime.toISOString(),
        allDat: newEventAllDay,
      })
      createDialog.value = false;
    }

    return {
      events,
      eventDialog,
      createDialog,
      selectedDate,
      eventTitle,
      eventTimeRange,
      handleDateClick,
      eventClick,
      createEvent,
      editEvent,
      deleteEvent,
      newEventTitle,
      newEventDescription,
      newEventStartDate,
      newEventStartTime,
      newEventEndDate,
      newEventEndTime,
      newEventAllDay,
    };
  },
  data() {
    return {
      calendarOptions: {
        plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin, momentPlugin, momentTimezonePlugin],
        initialView: 'dayGridMonth',
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        // timeZone: 'local',
        // eventTimeFormat: 'HH:MM',
        dateClick: this.handleDateClick,
        eventClick: this.eventClick,
        weekends: true,
        events: this.events,
      }
    };
  }
};
</script>