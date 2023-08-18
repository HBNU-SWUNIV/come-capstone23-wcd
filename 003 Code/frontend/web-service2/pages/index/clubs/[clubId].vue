<template>
  <ClubLeftNavDrawer />
  <MainAppBar />
  <router-view/>
  <ClubRightNavDrawer />
  <FooterBar />
</template>

<script>
import MainAppBar from "@/components/MainAppBar.vue";
import ClubLeftNavDrawer from "@/components/Clubs/ClubLeftNavDrawer.vue";
import MainContent from "@/components/MainContent.vue";
import ClubRightNavDrawer from "@/components/Clubs/ClubRightNavDrawer.vue";
import FooterBar from "@/components/FooterBar.vue";

export default {
  components: {
    ClubLeftNavDrawer,
    MainAppBar,
    MainContent,
    ClubRightNavDrawer,
    FooterBar
  },
  setup() {

  },
}
</script>