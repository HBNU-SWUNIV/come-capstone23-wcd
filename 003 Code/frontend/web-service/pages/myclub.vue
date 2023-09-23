<template>
  <div>My Club Page<v-btn @click="regenerate">토큰 재발급</v-btn></div>
</template>


<script>
export default{
  methods:{
    async regenerate() {
      
      try {
        const refresh_token = sessionStorage.getItem("refresh_token");
        const config = {
          headers: {
            "Content-Type": "application/json",
          },
        };
        await this.$axios
          .post("/user-service/regenerateToken", {refresh_token: refresh_token}, config)
          .then((res) => {
            console.log(res);
            this.$store.commit('setAccessToken', res.data.access_token);
          });
      } catch (err) {
        console.log(err);
      }
    },
  }
}
</script>
