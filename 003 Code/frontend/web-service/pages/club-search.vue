<template>
  <div style="width: 100%; padding: 30px; text-align: center;">
    <div class="input-container" style="margin-bottom: 30px;">
      <v-icon class="icon">mdi-magnify</v-icon>
      <input
        type="text"
        id="search"
        name="search"
        placeholder="검색어를 입력하세요..."
      />
    </div>
    <v-card color="none">
      <v-list>
        <v-row>
          <v-col
            v-for="(club, i) in clubs"
            :key="i"
            cols="3"
            style="padding: 0"
          >
            <v-list-item router exact>
              <v-list-item-content>
                <div
                  style="
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                  "
                >
                  <img
                    :src="getImageDataUri(club.multipartFile)"
                    style="height: 210px; width: 280px"
                  />
                  <v-list-item-title style="font-size: 17px; margin: 5px">{{
                    club.clubName
                  }}</v-list-item-title>
                  <v-list-item-subtitle>{{
                    club.description
                  }}</v-list-item-subtitle>
                </div>
              </v-list-item-content>
            </v-list-item>
          </v-col>
        </v-row>
      </v-list>
    </v-card>
  </div>
</template>


    
  <script>
export default {
  data() {
    return {
      clubs: [],
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
        this.clubs = response.data.content;
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