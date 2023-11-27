<template>
  <div style="width: 100%; height: 100%;">
    <div style="color: white; height: 100%; display: flex; flex-direction: row;">
      <LeftClubNav/>
      <div style="width: 100%; height: 100%; padding: 30px 40px 30px 0; display: flex; flex-direction: column; gap: 20px">
        <div style="display: flex; flex-grow: 1; gap: 20px">
          <NoticeCard style="height: 100%; width: 50%"/>
          <ScheduleCard style="height: 100%; width: 50%"/>
        </div>
        <div style="display: flex; flex-grow: 1; gap: 20px">
          <BoardCard style="height: 100%; width: 50%"/>
          <CommunicationCard style="height: 100%; width: 50%"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BoardCard from "@/components/ClubCard/BoardCard.vue";
import ScheduleCard from "@/components/ClubCard/ScheduleCard.vue";
import NoticeCard from "@/components/ClubCard/NoticeCard.vue";
import CommunicationCard from "@/components/ClubCard/CommunicationCard.vue";
import LeftClubNav from "@/components/LeftClubNav.vue";

export default {
  components: {
    BoardCard,
    ScheduleCard,
    NoticeCard,
    CommunicationCard,
    LeftClubNav,
  },
};
</script>
