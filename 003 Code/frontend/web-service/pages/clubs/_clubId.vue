<template>
  <div style="width:100%; height:100%;">
    <ClubNavigation style="border-bottom: solid 0.3px #e0e0e0"/>
    <div style="display: flex; flex-direction: row; height: calc(100% - 64px);">
      <router-view/>
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
