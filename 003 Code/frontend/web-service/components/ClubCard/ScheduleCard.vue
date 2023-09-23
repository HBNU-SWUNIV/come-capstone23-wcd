<template>
    <v-card style="width:50%; margin:10px;"
      ><v-card-title
        >{{ title }}<v-spacer /> <v-btn :to="`/clubs/${clubId}/schedule`">더보기</v-btn></v-card-title
      ></v-card
    >
  </template>

<script>
export default {
  name: "ScheduleCard",
  data() {
    return {
      title: "일정",
      clubId: null,
    };
  },
  created() {
    // 라우터의 clubId 값을 가져와서 변수에 할당
    this.clubId = this.$route.params.clubId;
  },
};
</script>