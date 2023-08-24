<template>
  <SideNavDrawer/>
  <LeftNavDrawer/>
  <MainAppBar/>
  <MainContent/>
  <RightNavDrawer/>
  <FooterBar/>
</template>

<script>
import SideNavDrawer from "@/components/Main/SideNavDrawer";
import FooterBar from "@/components/Main/FooterBar";
import RightNavDrawer from "@/components/Main/RightNavDrawer";
import MainContent from "@/components/Main/MainContent";
import MainAppBar from "@/components/Main/MainAppBar";
import LeftNavDrawer from "@/components/Main/LeftNavDrawer";

export default {
  components: {
    LeftNavDrawer,
    MainAppBar,
    MainContent,
    RightNavDrawer,
    FooterBar,
    SideNavDrawer
  },
  setup() {

  }
}
</script>