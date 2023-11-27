<template>
  <div style="display: flex; flex-direction: column; justify-content: space-between; width: 100%; padding: 30px; gap: 20px">
    <ClubCategoryCard style="height: 150px"/>
    <ClubRecommendCard style="height: 50%"/>
    <div style="display: flex; flex-direction: row; height: 50%; gap: 20px">
      <MyClubCard />
      <MyCreateClubCard />
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

</style>
