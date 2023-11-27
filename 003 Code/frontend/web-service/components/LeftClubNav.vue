<template>
  <div style="height: 100%; padding: 40px">
    <img
      :src="clubInfo.mainImageUrl"
      style="
        width: 320px;
        height: 240px;
        border-radius: 5px;
        border: solid 1px #e0e0e0;
      "
    />

    <h1
      style="
        font-size: 27px;
        color: black;
        font-weight: 600;
        margin-bottom: 5px;
      "
    >
      {{ clubInfo.clubName }}
    </h1>
    <p style="margin-bottom: 5px; font-size: 13px; color: rgb(52, 173, 52)">
      멤버 수 : {{ clubmembers.length }}
    </p>
    <p style="margin-bottom: 15px; color: rgb(202, 202, 202); font-size: 13px">
      {{ clubInfo.description }}
    </p>

    <hr />
    <div class="d-flex flex-column" style="margin-top: 10px">
      <v-btn v-if="this.user_id == clubInfo.hostId" @click="deleteClub"
        >모임 삭제</v-btn
      >
      <v-btn
        v-if="
          !clubmembers.map((item) => item.userId).includes(parseInt(user_id))
        "
        @click="joinClub"
        >모임 가입</v-btn
      >
      <v-btn
        v-if="
          clubmembers.map((item) => item.userId).includes(parseInt(user_id)) &&
          !(this.user_id == clubInfo.hostId)
        "
        @click="outClub"
        >모임 탈퇴</v-btn
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
            .then(async (res) => {
              console.log(res);
              alert("가입되었습니다.");
              await this.getMemberInfo(this.$route);
            });
        } catch (err) {
          console.log(err);
        }
      }
    },
    async deleteClub() {
      const confirmJoin = window.confirm("모임을 삭제하시겠습니까?");

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
            .delete(
              `/club-service/clubs/${this.$route.params.clubId}`,
              config
            )
            .then(async (res) => {
              console.log(res);
              alert("삭제되었습니다.");
              this.$router.push("/")
            });
        } catch (err) {
          console.log(err);
        }
      }
    },
    async outClub() {
      let outData = {
        userId: this.user_id,
        clubId: this.$route.params.clubId,
      };

      const confirmOut = window.confirm("모임에서 탈퇴하시겠습니까?");

      if (confirmOut) {
        try {
          const access_token = this.$store.state.access_token;
          const config = {
            headers: {
              "Content-Type": "application/json", // JSON 형식으로 변경
              Authorization: `Bearer ${access_token}`,
            },
          };

          await this.$axios
            .delete(
              `/club-service/clubs/${this.$route.params.clubId}/members/${this.user_id}`,config,JSON.stringify(outData),
            )
            .then(async (res) => {
              console.log(res);
              alert("탈퇴되었습니다.");
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
