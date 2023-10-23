<template>
  <div style="width:100%;">
    <ClubNavigation />
    <div style="display: flex; flex-direction: row; height:100%;">
      <LeftClubNav />
      <router-view />
    </div>
  </div>
</template>

<script>
import LeftClubNav from "@/components/LeftClubNav.vue";
import ClubNavigation from "@/components/ClubNavigation.vue";

export default {
  components: {
    ClubNavigation,
    LeftClubNav,
  },
};
</script>