<template>
  <div class="modal-overlay">
    <div class="modal">
      <h2 style="padding-top: 20px; padding-bottom: 15px; text-align: left">
        채팅방
      </h2>

      <v-list style="background-color: white; padding: 0">
        <v-list-item
          v-for="(myclub, i) in displayedClubs"
          :key="i"
          @click="openChattingRoom(myclub.id)"
          style="color: black"
        >
          <v-list-item-action>
            <v-avatar size="52">
              <img :src="myclub.mainImageUrl" />
            </v-avatar>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title style="font-size: 20px">{{
              myclub.clubName
            }}</v-list-item-title>
            <p style="margin: 0">여기에는 최근 채팅내용</p>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </div>
    <div class="close" @click="$emit('close-modal')">
      <v-icon>mdi-close</v-icon>
    </div>
  </div>
</template>
    
<script>
export default {
  name: "MyClubCard",
  data() {
    return {
      myclubs: [],
    };
  },
  methods: {
    async getMyClubs() {
      try {
        const access_token = this.$store.state.access_token;
        const user_id = sessionStorage.getItem("user_id");
        const config = {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
          },
        };
        await this.$axios
          .get(`/club-service/users/${user_id}/clubs`, config)
          .then((res) => {
            console.log(res.data);
            this.myclubs = res.data;
          });
      } catch (err) {
        console.log(err);
      }
    },
    getImageDataUri(imageData) {
      return `data:image/jpg;base64,${imageData}`;
    },

    openChattingRoom(clubId) {
      this.$emit("close-modal");
      this.$router.push(`/clubs/${clubId}/chatting`);
    },
  },
  computed: {
    displayedClubs() {
      // 최대 5개 클럽만 표시
      return this.myclubs.slice(0, 5);
    },
  },
  created() {
    this.getMyClubs();
  },
};
</script>
  
<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  color: rgb(0, 0, 0);
}

.modal {
  background-color: rgb(255, 255, 255);
  height: 500px;
  width: 500px;
  margin-top: 10%;
  padding: 0;
  padding-left: 30px;
  padding-right: 30px;
  border-radius: 20px;
  color: black;
}
.close {
  margin: 10% 0 0 16px;
  cursor: pointer;
}
</style>