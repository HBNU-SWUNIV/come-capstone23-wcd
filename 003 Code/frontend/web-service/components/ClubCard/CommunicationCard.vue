<template>
  <v-card style="width: 50%; margin: 10px">
    <v-card-title>{{ title }}</v-card-title>
      <v-list>
        <v-list-item
          v-for="(room, i) in rooms"
          :key="i"
          @click="openClubInPopup(clubId)"
        >
          <v-list-item-action>
            <v-icon>{{ room.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ room.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
  </v-card>
</template>

<script>
let popupWindows = {};
export default {
  name: "CommunicationCard",
  data() {
    return {
      title: "커뮤니케이션",
      clubId: null,
      rooms : [
        {
          icon: "mdi-chat",
          title: "채팅방"
        },
        {
          icon: "mdi-camera",
          title: "화상채팅방"
        }
      ]
    };
  },
  methods:{
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
  created() {
    // 라우터의 clubId 값을 가져와서 변수에 할당
    this.clubId = this.$route.params.clubId;
  },
};
</script>