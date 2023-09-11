<template>
  <div style="width:100%;">
    <clubRecommendCard />
    <ClubCategoryCard />
    <MyClubCard />
  </div>
</template>

<script>
import ClubCategoryCard from "../components/ClubCategoryCard.vue";
import ClubRecommendCard from "../components/ClubRecommendCard.vue";
import MyClubCard from "../components/MyClubCard.vue";

export default {
  name: "HomePage",
  components: { MyClubCard, ClubRecommendCard, ClubCategoryCard },
};
</script>

<style>
.v-card{
  margin-bottom: 30px;
}
</style> 