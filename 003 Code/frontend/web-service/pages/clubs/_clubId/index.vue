<template>
  <div style="width: 100%">
    <ClubNavigation />
    <div style="color: white; display: flex; flex-direction: row">
      <LeftClubNav />
      <div style="width: 100%; padding:10px; padding-left: 0;">
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
import LeftClubNav from "../../../components/LeftClubNav.vue";
import ClubNavigation from "../../../components/ClubNavigation.vue";
import BoardCard from "../../../components/ClubCard/BoardCard.vue";
import ScheduleCard from "../../../components/ClubCard/ScheduleCard.vue";
import NoticeCard from "../../../components/ClubCard/NoticeCard.vue";
import CommunicationCard from "../../../components/ClubCard/CommunicationCard.vue";

export default {
  components: {
    ClubNavigation,
    LeftClubNav,
    BoardCard,
    ScheduleCard,
    NoticeCard,
    CommunicationCard,
  },
};
</script>