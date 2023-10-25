<template>
  <div style="width:100%;">
    <ClubNavigation />
    <div style="display: flex; flex-direction: row;">
      <router-view />
    </div>
  </div>
</template>

<script>
import ClubNavigation from "@/components/ClubNavigation.vue";

export default {
  components: {
    ClubNavigation,
  },
};
</script>