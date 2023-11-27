<template>
  <v-card
    ><v-card-title
      >{{ title }}<v-spacer />
      <v-btn :to="`/clubs/${clubId}/schedule`">더보기</v-btn></v-card-title
    >
    <v-list>
      <v-list-item
        v-for="(schedule, i) in displayedSchedules"
        :key="i"
        :to="`/clubs/${clubId}/schedule`"
        router
        exact
      >
        <v-list-item-content style="display: flex">
          <v-list-item-title>{{ schedule.title }} : {{ schedule.description }} </v-list-item-title>
          <p style="margin: 0; font-size: 13px; color: rgb(128,128,128)">
            [{{ schedule.start }} ~ {{ schedule.end }}]
          </p>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    </v-card>
</template>

<script>
export default {
  name: "ScheduleCard",
  data() {
    return {
      title: "일정",
      clubId: null,
      schedules: [],
      yymm: "",
    };
  },
  methods: {
    async getSchedules() {
      const currentDate = new Date();
      const year = currentDate.getFullYear();
      const month = (currentDate.getMonth() + 1).toString().padStart(2, "0");

      // yymm 값 설정
      this.yymm = `${year.toString().slice(-2)}${month}`;
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
            this.schedules = res.data;
          });
      } catch (err) {
        console.log(err);
      }
    },
  },
  computed: {
    displayedSchedules() {
      // 최대 5개 클럽만 표시
      return this.schedules.slice(0, 5);
    },
  },
  created() {
    // 라우터의 clubId 값을 가져와서 변수에 할당
    this.clubId = this.$route.params.clubId;
    this.getSchedules();
  },
};
</script>
