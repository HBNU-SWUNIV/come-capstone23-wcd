<template>
  <v-card>
    <v-card-title>{{ title }}</v-card-title>
    <v-list>
      <v-row style="margin: 0">
        <v-col v-for="(club, i) in clubs" :key="i" cols="6" style="padding: 0">
          <v-list-item :to="`/clubs/${club.id}`" router exact>
            <v-list-item-action>
              <img
                :src="getImageDataUri(club.multipartFile)"
                style="height: 45px; width: 45px"
              />
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>{{ club.clubName }}</v-list-item-title>
              <v-list-item-subtitle>{{
                club.description
              }}</v-list-item-subtitle>
            </v-list-item-content>
            <v-spacer/>
            <v-list-item-content style="font-size:15px; color:gray;">
              {{ club.category }}
            </v-list-item-content>
          </v-list-item>
        </v-col>
      </v-row>
    </v-list>
  </v-card>
</template>
  
<script>
export default {
  name: "ClubRecommendCard",
  data() {
    return {
      clubs: [],
      title: "이런 모임은 어떤가요?",
    };
  },
  methods: {
    async getClubs() {
      try {
        const access_token = this.$store.state.access_token;
        const config = {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
          },
        };
        const response = await this.$axios.get(`/club-service/clubs`, config);
        console.log(response.data.content);
        const shuffledClubs = response.data.content.sort(
          () => 0.5 - Math.random()
        );
        this.clubs = shuffledClubs.slice(0, 6);
      } catch (err) {
        console.error("Error while fetching clubs:", err);
        this.clubs = []; // 에러 발생 시 빈 배열로 초기화
      }
    },
    getImageDataUri(imageData) {
      return `data:image/jpg;base64,${imageData}`;
    },
  },
  created() {
    this.getClubs();
  },
};
</script>

<style>
.v-card {
  background-color: white;
}
</style>