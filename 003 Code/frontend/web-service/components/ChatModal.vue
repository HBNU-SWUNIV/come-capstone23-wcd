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
          @click="openClubInPopup(myclub.id)"
          style="color: black"
        >
          <v-list-item-action>
            <!-- <v-avatar size="42">
              <img :src="getImageDataUri(myclub.multipartFile)" />
            </v-avatar> -->
            <v-avatar size="52">
              <img
                src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxmp7sE1ggI4_L7NGZWcQT9EyKaqKLeQ5RBg&usqp=CAU"
              />
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
let popupWindows = {};
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

    openClubInPopup(clubId) {
      const clubUrl = `/clubs/${clubId}/chatting`;

      // 팝업 창 이름을 동적으로 생성
      const popupName = `ClubPopup_${clubId}`;

      // 팝업 창 옵션 설정
      const popupOptions = {
        width: 450, // 팝업 창의 너비
        height: 650, // 팝업 창의 높이
        left: 100, // 팝업 창의 왼쪽 위치 (원하는 위치로 조절)
        top: 100, // 팝업 창의 상단 위치 (원하는 위치로 조절)
        location: false, // 주소 표시줄 표시 여부
        toolbar: false, // 도구 모음 표시 여부
        resizable: false, // 크기 조절 여부
        scrollbars: false, // 스크롤바 표시 여부
      };

      //이미 열린 팝업 창이 있는지 확인
      let popupWindow = popupWindows[popupName];

      if (popupWindow && !popupWindow.closed) {
        // 이미 열린 팝업 창이 있으면 그 창을 활성화
        popupWindow.focus();
      } else {
        // 팝업 창으로 열기
        popupWindow = window.open(
          clubUrl,
          popupName,
          Object.keys(popupOptions)
            .map((key) => `${key}=${popupOptions[key]}`)
            .join(",")
        );

        // popupWindow.onload = function () {
        //   // 팝업 창 내의 스크롤바를 숨김
        //   const popupDocument = popupWindow.document;
        //   popupDocument.documentElement.style.overflowY = "hidden";
        // };

        // 열린 팝업 창을 추적하기 위해 객체에 저장
        popupWindows[popupName] = popupWindow;
      }
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