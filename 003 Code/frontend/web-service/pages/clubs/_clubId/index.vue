<template>
  <div style="width: 100%; height: 100%;">
    <div style="color: white; height: 100%; display: flex; flex-direction: row;">
      <LeftClubNav/>
      <div style="width: 100%; height: 100%; padding-top: 30px; padding-right: 40px;">
        <div class="d-flex">
          <NoticeCard />
          <ScheduleCard />
        </div>
        <div class="d-flex">
          <BoardCard />
          <CommunicationCard />
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
