<template>
  <div style="width:100%; height:100%;">
    <ClubNavigation />
    <div style="display: flex; flex-direction: row; height: calc(100% - 64px); padding: 20px 30px">
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
