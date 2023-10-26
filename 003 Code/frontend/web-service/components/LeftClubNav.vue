<template>
  <div
    style="
      height: 100%;
      /*padding: 20px;*/
      /*padding-right: 30px;*/
      /*padding-left: 50px;*/
      /*padding-top: 50px;*/
    "
  >
    <img
      :src="clubInfo.mainImageUrl"
      style="width: 320px; height: 240px; border-radius: 5px;"
    />

    <h1 style="font-size: 27px; margin-bottom: 5px;">{{ clubInfo.clubName }}</h1>
    <p style="margin-bottom: 5px; font-size: 13px; color: rgb(125, 255, 125)">
      멤버 수 : {{ clubmembers.length }}
    </p>
    <p style="margin-bottom: 15px; color: rgb(202, 202, 202); font-size: 13px">
      {{ clubInfo.description }}
    </p>

    <hr />
    <div class="d-flex flex-column" style="margin-top: 10px">
      <v-btn
        v-if="this.user_id == clubInfo.hostId"
        :to="`/clubs/${clubInfo.id}/settings`"
        >모임 관리</v-btn
      >
      <v-btn
        v-if="
          !clubmembers.map((item) => item.userId).includes(parseInt(user_id))
        "
        @click="joinClub"
        >모임 가입</v-btn
      >
    </div>
  </div>
</template>

<script>
export default {
  name: "LeftClubNav",
  data() {
    return {
      clubInfo: [],
      clubmembers: [],
      user_id: "",
    };
  },
  methods: {
    async getClubInfo({ params }) {
      const clubId = params.clubId;
      try {
        const access_token = this.$store.state.access_token;
        const config = {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
          },
        };
        await this.$axios
          .get(`/club-service/clubs/${clubId}`, config)
          .then((res) => {
            console.log(res);
            this.clubInfo = res.data;
          });
      } catch (err) {
        console.log(err);
      }
    },
    async getMemberInfo({ params }) {
      const clubId = params.clubId;
      try {
        const access_token = this.$store.state.access_token;
        const config = {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
          },
        };
        await this.$axios
          .get(`/club-service/clubs/${clubId}/members`, config)
          .then((res) => {
            console.log(res);
            this.clubmembers = res.data;
          });
      } catch (err) {
        console.log(err);
      }
    },
    async joinClub() {
      let joinData = {
        userId: this.user_id,
        clubId: this.$route.params.clubId,
      };

      const confirmJoin = window.confirm("모임에 가입하시겠습니까?");

      if (confirmJoin) {
        try {
          const access_token = this.$store.state.access_token;
          const config = {
            headers: {
              "Content-Type": "application/json", // JSON 형식으로 변경
              Authorization: `Bearer ${access_token}`,
            },
          };

          await this.$axios
            .post(
              `/club-service/clubs/${this.$route.params.clubId}/members`,
              JSON.stringify(joinData),
              config
            )
            .then(async(res) => {
              console.log(res);
              alert("가입되었습니다.");
              await this.getMemberInfo(this.$route);
            });
        } catch (err) {
          console.log(err);
        }
      }
    },
    getImageDataUri(imageData) {
      return `data:image/jpg;base64,${imageData}`;
    },
  },
  created() {
    this.user_id = sessionStorage.getItem("user_id");
    this.getClubInfo(this.$route);
    this.getMemberInfo(this.$route);
  },
};
</script>
