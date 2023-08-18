<template>
  <div>
    <FullCalendar :options="calendarOptions"/>

    <!-- 일정 정보창 -->
    <v-dialog v-model="eventDialog" max-width="500" persistent @click:outside="eventDialog = false">
      <v-card>
        <!-- 제목부분 스타일 조정 -->
        <v-card-title>
          {{ eventTitle }}
        </v-card-title>
        <v-divider></v-divider>

        <!-- 시간표시부분 스타일 조정 -->
        <v-card-text>
          <v-icon>access_time</v-icon>
          <span class="font-weight-medium">{{ eventTimeRange }}</span>
        </v-card-text>

        <!-- 액션버튼 스타일 조정 -->
        <v-card-actions class="justify-end mt-5">
          <v-btn icon color="blue darken-1" @click="editEvent">
            <v-icon>edit</v-icon>
          </v-btn>
          <v-btn icon color="red darken-1" @click="deleteEvent">
            <v-icon>delete</v-icon>
          </v-btn>
          <v-btn text color="green darken-1" @click="eventDialog = false">닫기</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>


    <!-- 일정 추가 창 -->
    <v-dialog v-model="createDialog" max-width="500px">
      <v-card>
        <v-form ref="form" v-model="validForm">
          <v-card-title>일정을 입력하세요.</v-card-title>
          <v-card-text>
            <v-text-field
                label="일정 제목"
                v-model="newEventTitle"
                :rules="[v => !!v || '제목은 필수입니다.']"></v-text-field>  <!-- 제목 유효성 검사 -->
            <v-text-field label="일정 내용" v-model="newEventDescription"></v-text-field>

            <!-- 시작 날짜와 시간 -->
            <v-card-text>시작 날짜와 시간</v-card-text>
            <v-row>
              <v-col cols="6">
                <v-text-field
                    type="date"
                    id="start-datetime-date"
                    v-model="newEventStartDate"/>
              </v-col>
              <v-col cols="6">
                <v-text-field
                    type="time"
                    id="start-datetime-time"
                    v-model="newEventStartTime"
                    :disabled="newEventAllDay"
                ></v-text-field>
              </v-col>
            </v-row>

            <!-- 종료 날짜와 시간 -->
            <v-card-text>종료 날짜와 시간</v-card-text>
            <v-row>
              <v-col cols="6">
                <v-text-field type="date" id="end-datetime-date" v-model="newEventEndDate"/>
              </v-col>
              <v-col cols="6">
                <v-text-field
                    type="time"
                    id="end-datetime-time"
                    v-model="newEventEndTime"
                    :disabled="newEventAllDay"
                ></v-text-field>
              </v-col>
            </v-row>

            <v-checkbox v-model="newEventAllDay" label="하루 종일"></v-checkbox>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn :disabled="!validForm" color="warning" @click="createEvent">추가</v-btn>  <!-- 유효성 검사 결과에 따라 버튼 활성화/비활성화 -->
            <v-btn text @click="createDialog = false">취소</v-btn>
          </v-card-actions>
        </v-form>
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
import { ref } from 'vue';

export default {
  components: {
    FullCalendar
  },

  // ----- 데이터 -----
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
        dateClick: this.handleDateClick,
        eventClick: this.eventClick,
        weekends: true,
        events: this.events,
      }
    };
  },

  // ----- 초기 설정 -----
  setup() {
    const validForm = ref(false);
    const events = ref([]);
    const eventDialog = ref(false);
    const createDialog = ref(false);
    const eventTitle = ref("");
    const eventTimeRange = ref("");
    const newEventTitle = ref("");
    const newEventDescription = ref("");
    const newEventStartDate = ref("");
    const newEventStartTime = ref("");
    const newEventEndDate = ref("");
    const newEventEndTime = ref("");
    const newEventAllDay = ref(false);

    return {
      validForm,
      events,
      eventDialog,
      createDialog,
      eventTitle,
      eventTimeRange,
      newEventTitle,
      newEventDescription,
      newEventStartDate,
      newEventStartTime,
      newEventEndDate,
      newEventEndTime,
      newEventAllDay,
      eventClick,
      handleDateClick,
      editEvent,
      deleteEvent,
      createEvent,
      formatDate,
      formatTime,
      addHours
    };
  },

  // ----- 메서드 -----
  methods: {
    // 클릭한 이벤트를 처리하는 메서드
    eventClick(info) {
      eventTitle.value = info.event.title;
      const startTime = info.event.start.toLocaleString();
      if (info.event.end) {
        const endTime = new Date(info.event.end.getTime() - 1).toLocaleString();
        eventTimeRange.value = `${startTime} - ${endTime}`;
      } else {
        eventTimeRange.value = startTime;
      }
      eventDialog.value = true;
    },

    // 날짜 클릭 이벤트를 처리하는 메서드
    handleDateClick(arg) {
      createDialog.value = true;
      const newEventStartDateTime = arg.date;
      newEventStartDate.value = formatDate(newEventStartDateTime);
      newEventStartTime.value = formatTime(newEventStartDateTime);
      addHours(arg.date, 1);
      const newEventEndDateTime = arg.date;
      newEventEndDate.value = formatDate(newEventEndDateTime);
      newEventEndTime.value = formatTime(newEventEndDateTime);
    },

    // 이벤트 수정 메서드
    editEvent() {
      // 이벤트 수정 로직 추가
    },

    // 이벤트 삭제 메서드
    deleteEvent() {
      // 이벤트 삭제 로직 추가
    },

    // 이벤트 생성 메서드
    createEvent() {
      const newEventStartDateTime = new Date(`${newEventStartDate.value}T${newEventStartTime.value}`);
      const newEventEndDateTime = new Date(`${newEventEndDate.value}T${newEventEndTime.value}`);
      events.value.push({
        title: newEventTitle,
        start: newEventStartDateTime,
        end: newEventEndDateTime.toISOString(),
        allDay: newEventAllDay,
      });
      createDialog.value = false;
    },

    // 날짜 포맷 메서드
    formatDate(date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      return `${year}-${month}-${day}`;
    },

    // 시간 포맷 메서드
    formatTime(date) {
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      return `${hours}:${minutes}`;
    },

    // 시간 추가 메서드
    addHours(date, hours) {
      date.setTime(date.getTime() + hours * 60 * 60 * 1000);
      return date;
    }
  }
};
</script>