<!-- <template>
    <div>
        <v-btn @click="deleteClub">모임 삭제</v-btn>
    </div>
</template>

<script>
export default{
    methods:{
        async deleteClub(){
            const confirmDelete = confirm("모임을 삭제하시겠습니까?");
      if (confirmDelete) {
        try {
          const access_token = this.$store.state.access_token;
          const config = {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${access_token}`,
            },
          };
          await this.$axios
            .delete(
              `/board-service/clubs/${this.$route.params.clubId}/posts/${this.$route.params.boardId}`,
              config
            )
            .then((res) => {
              console.log(res);
              alert("게시글이 삭제되었습니다.");
              this.$router.push(`/clubs/${this.$route.params.clubId}/board`);
            });
        } catch (err) {
          console.log(err);
        }
      }
        }
    }
}
</script> -->

<template>
    <div>모임 설정</div>
</template>