<template>
  <div style="width: 100%; padding: 30px;">
    <ClubRecommendCard />
    <ClubCategoryCard />
    <div style="display: flex;">
      <MyClubCard style="margin-right: 15px;"/> 
      <MyCreateClubCard style="margin-left: 15px;"/>
    </div>
  </div>
</template>

<script>
import ClubCategoryCard from "../components/ClubCategoryCard.vue";
import ClubRecommendCard from "../components/ClubRecommendCard.vue";
import MyClubCard from "../components/MyClubCard.vue";
import MyCreateClubCard from "../components/MyCreateClubCard.vue";

export default {
  name: "HomePage",
  components: {
    MyClubCard,
    ClubRecommendCard,
    ClubCategoryCard,
    MyCreateClubCard,
  },
};
</script>

<style>
.v-card {
  margin-bottom: 30px;
}
</style> 