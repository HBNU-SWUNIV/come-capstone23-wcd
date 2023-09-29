<template>
  <div>
    {{ post.content }} {{ post.createdAt }} {{ post.title }}
    {{ post.writerName }} {{ post.updatedAt }}
  </div>
</template>

<script>
export default {
  data() {
    return {
      post: [],
    };
  },
  methods: {
    async getBoardDetail() {
      try {
        const access_token = this.$store.state.access_token;
        const config = {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
          },
        };
        await this.$axios
          .get(
            `/board-service/clubs/${this.$route.params.clubId}/posts/${this.$route.params.boardId}`,
            config
          )
          .then((res) => {
            console.log(res.data);
            this.post = res.data;
          });
      } catch (err) {
        console.error("err", err);
      }
    },
  },
  created() {
    this.getBoardDetail();
  },
};
</script>
