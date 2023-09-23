<template>
  <div style="width: 100%">
    <ClubNavigation />
    <div style="color: white; display: flex; flex-direction: row">
      <LeftClubNav/>
      <div
        style="
          width: 950px;
          padding: 20px;
          border-right: 1px solid rgb(127, 127, 127);
        "
      >
      <CKEditor style="color:black"/>
      </div>
    </div>
  </div>
</template>
  
  <script>
import LeftClubNav from "../../../components/LeftClubNav.vue";
import ClubNavigation from "../../../components/ClubNavigation.vue";
import CKEditor from '~/components/CKEditor.vue';

export default {
  components: {
    ClubNavigation,
    LeftClubNav,
    CKEditor,
  },
};
</script>