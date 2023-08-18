<template>
  <SideNavDrawer />
  <ClubLeftNavDrawer/>
  <MainAppBar/>
  <MainContent/>
  <ClubRightNavDrawer/>
  <FooterBar/>
  <slot/>
</template>

<script>
import MainAppBar from "@/components/MainAppBar.vue";
import ClubLeftNavDrawer from "@/components/Clubs/ClubLeftNavDrawer.vue";
import MainContent from "@/components/MainContent.vue";
import ClubRightNavDrawer from "@/components/Clubs/ClubRightNavDrawer.vue";
import FooterBar from "@/components/FooterBar.vue";
import SideNavDrawer from "../components/SideNavDrawer";

export default {
  components: {
    SideNavDrawer,
    ClubLeftNavDrawer,
    MainAppBar,
    MainContent,
    ClubRightNavDrawer,
    FooterBar
  },
  setup() {

  }
}
</script>