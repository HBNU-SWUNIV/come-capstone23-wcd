<template>
  <div>
    <v-app>
      <v-navigation-drawer
        v-model="drawer"
        :mini-variant="miniVariant"
        :mini-variant-width="miniVariantWidth"
        :clipped="clipped"
        :permanent="true"
        fixed
        app
      >
        <v-list style="height: 100%; padding: 0">
          <div
            ref="homeHeight"
            style="height: fit-content"
          >
            <v-list-item
              v-for="(home, i) in homes"
              :key="i"
              :to="home.to"
              router
              exact
              @click="getMyClubsAgain"
            >
              <v-list-item-action class="d-block mx-auto">
                <v-icon style="width: 100%">{{ home.icon }}</v-icon>
                <v-list-item-title
                  style="font-size: 12px; text-align: center"
                  >{{ home.title }}</v-list-item-title
                >
              </v-list-item-action>
            </v-list-item>
          </div>
          <v-divider style="margin: 0 8px"></v-divider>
          <div class="drawer-content" ref="clubHeight">
            <v-list-item
              v-for="(myclub, i) in myclubs"
              :key="i"
              :to="`/clubs/${myclub.id}`"
              router
              exact
              style="padding: 4px 16px"
            >
              <v-tooltip right>
                <template v-slot:activator="{ on }">
                  <v-list-item-action
                    v-on="on"
                    style="height: 100%; display: flex; flex-direction: column; align-items: center; justify-items: center; margin: 0; gap: 4px"
                  >
                    <img class="image" :src="myclub.mainImageUrl" />
                    <v-list-item-title
                      style="font-size: 12px; text-align: center"
                    >{{ myclub.clubName }}</v-list-item-title
                    >
                  </v-list-item-action>
                </template>
                <span>{{ myclub.clubName }}</span>
              </v-tooltip>
            </v-list-item>
          </div>

          <div
            ref="itemHeight"
            style="
              position: absolute;
              bottom: 0;
              width: 100%;
              text-align: center;
            "
          >
            <v-list-item
              v-for="(item, i) in items"
              :key="i"
              :to="item.to"
              router
              exact
              class="d-block mx-auto"
              style="text-align: center"
            >
              <v-list-item-action>
                <v-icon style="width: 100%; display: block; margin: 0 auto">{{
                  item.icon
                }}</v-icon>
                <v-list-item-title style="font-size: 12px">{{
                  item.title
                }}</v-list-item-title>
              </v-list-item-action>
            </v-list-item>
          </div>
        </v-list>
      </v-navigation-drawer>
      <v-app-bar :clipped-left="clipped" fixed app style="background-color: #ffffff; box-shadow: none; border-bottom: solid 1px #e0e0e0">
        <!-- <v-app-bar-nav-icon @click.stop="drawer = !drawer" /> -->
        <div class="input-container">
          <v-icon class="icon">mdi-magnify</v-icon>
          <input
            type="text"
            id="search"
            name="search"
            placeholder="검색어를 입력하세요..."
          />
        </div>
        <v-spacer />
        <v-btn icon>
          <v-icon @click="openModal">mdi-chat</v-icon>
        </v-btn>
        <ChatModal v-if="isModalVisible" @close-modal="closeModal" />
        <!-- <v-btn icon>
          <v-icon @click="goMyInfoPage">mdi-account-circle</v-icon>
        </v-btn> -->
        <v-btn icon @click.stop="rightDrawer = !rightDrawer">
          <v-icon>mdi-bell</v-icon>
        </v-btn>
        <v-btn style="color: #757575" @click="Logout"
        >로그아웃</v-btn
        >
      </v-app-bar>
      <v-main>
        <v-container
          style="
            max-width: 100%;
            height: 100%;
            padding: 0;
            display: flex;
            justify-content: center;
          "
        >
          <Nuxt />
        </v-container>
      </v-main>
      <v-navigation-drawer
        v-model="rightDrawer"
        :right="right"
        temporary
        fixed
        style="width: 300px"
      >
        <v-list>
          <v-list-item>
            <v-list-item-title>NOTIFICATIONS</v-list-item-title>
          </v-list-item>
        </v-list>
        <v-list style="padding: 0">
          <!-- <v-list-item
            v-for="(notification, i) in notifications"
            :key="i"
            :to="notification.to"
            router
            exact
          >
            <v-list-item-action style="margin: 0; margin-right: 16px">
              <img class="image" :src="notification.image" />
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>{{ notification.clubName }}</v-list-item-title>
              <v-list-item-subtitle>{{
                notification.contents
              }}</v-list-item-subtitle>
              <v-list-item-subtitle>{{
                notification.time
              }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item> -->
          <v-list-item   v-for="(notification, i) in noti" :key="i">
            <v-list-item-content>
              <v-list-item-title>{{ notification }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
      <v-footer :fixed="true" style="font-size: 13px" app>
        <span
          >&copy; {{ new Date().getFullYear() }} WCD Capstone Design Teams</span
        >
      </v-footer>
    </v-app>
  </div>
</template>

<script>
import ChatModal from "../components/ChatModal.vue";

export default {
  name: "DefaultLayout",
  components: {
    ChatModal,
  },
  data() {
    return {
      clipped: false,
      drawer: false,
      fixed: false,
      miniVariant: true,
      right: true,
      rightDrawer: false,
      miniVariantWidth: 80,
      homes: [
        {
          icon: "mdi-home-circle",
          title: "Home",
          to: "/",
        },
      ],
      notifications: [
        {
          image: require("@/static/1.jpg"),
          clubName: "모임1",
          contents: "'사용자' 님이 모임에 가입했습니다.",
        },
        {
          image: require("@/static/2.jpg"),
          clubName: "모임2",
          contents: "새로운 게시글이 있습니다.",
        },
        {
          image: require("@/static/3.jpg"),
          clubName: "모임3",
          contents: "새로운 일정이 있습니다.",
        },
        {
          image: require("@/static/4.jpg"),
          clubName: "모임4",
          contents: "새로운 공지사항을 확인해주세요.",
        },
        {
          image: require("@/static/5.jpg"),
          clubName: "모임5",
          contents: "'사용자3' 님이 모임에 가입했습니다.",
        },
      ],
      myclubs: [],
      items: [
        {
          icon: "mdi-plus",
          title: "Create",
          to: "/club-create",
        },
        {
          icon: "mdi-magnify",
          title: "Search",
          to: "/club-search",
        },
        {
          icon: "mdi-cog",
          title: "Settings",
          to: "/settings",
        },
      ],
      isModalVisible: false,
      noti: [],
    };
  },
  methods: {
    goMyInfoPage() {
      this.$router.push("/myprofile");
    },
    async Logout() {
      const confirmed = window.confirm("로그아웃하시겠습니까?");

      if (confirmed) {
        let LogoutData = {
          access_token: this.$store.state.access_token,
          refresh_token: sessionStorage.getItem("refresh_token"),
        };
        try {
          const config = {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${LogoutData.access_token}`,
            },
          };

          await this.$axios
            .post(
              "/user-service/user/logout",
              JSON.stringify(LogoutData),
              config
            )
            .then((res) => {
              alert("로그아웃 되었습니다.");

              this.$store.commit("setAccessToken", null);
              sessionStorage.removeItem("refresh_token");
              sessionStorage.removeItem("user_id");

              this.$router.push("/login");
            });
        } catch (error) {
          console.log(error);
        }
      }
    },

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
            console.log(res);
            this.myclubs = res.data;
          });
      } catch (err) {
        console.log(err);
      }
    },

    async getSSE() {
      const userId = sessionStorage.getItem("user_id");
      const sse = new EventSource(
        `https://wcd.kro.kr/api/alarm-service/connect/${userId}`
      );
      sse.addEventListener("connect", (e) => {
        const { data: receivedConnectData } = e;
        console.log("connect event data: ", receivedConnectData); // "connected!"
      });
      sse.addEventListener("notifyJoinClubMember", (e) => {
        const { data: receivedConnectData } = e;
        console.log("join event data: ", receivedConnectData); // "connected!"
        this.noti.push(receivedConnectData);
      });
      sse.addEventListener("notifyCreateSchedule", (e) => {
        const { data: receivedConnectData } = e;
        console.log("schedule event data: ", receivedConnectData); // "connected!"
        this.noti.push(receivedConnectData);
      });
    },
    getImageDataUri(imageData) {
      return `data:image/jpg;base64,${imageData}`;
    },
    adjustDrawerHeight() {
      // homeHeight, itemHeight, clubHeight의 높이 값을 가져옴
      const homeHeight = this.$refs.homeHeight.clientHeight;
      const itemHeight = this.$refs.itemHeight.clientHeight;
      const windowHeight = window.innerHeight;

      // clubHeight에 적절한 높이를 설정
      this.$refs.clubHeight.style.height =
        windowHeight - homeHeight - itemHeight - 80 + "px";
    },

    async getMyClubsAgain() {
      await this.getMyClubs();
    },
    openModal() {
      // 날짜를 클릭할 때 모달을 표시
      this.isModalVisible = true;
    },
    closeModal() {
      // 모달을 닫을 때 호출되는 메서드
      this.isModalVisible = false;
    },
  },
  watch: {
    // 화면 크기가 변경될 때마다 높이를 조절
    $route() {
      this.adjustDrawerHeight();
    },
    $vuetify: {
      handler() {
        this.adjustDrawerHeight();
      },
      deep: true,
    },
  },
  created() {
    this.getMyClubs();
    this.getSSE();
  },
};
</script>

<style>
/* 입력 필드와 아이콘을 감싸는 div를 정의 */
.input-container {
  display: flex;
  align-items: center;
  background-color: #efefef;
  height: 40px;
  width: 400px;
  border-radius: 5px;
}

/* 아이콘 스타일 설정 */
.icon {
  padding: 0 10px; /* 아이콘과 입력 필드 사이 여백 조정 */
}

/* 입력 필드 스타일 설정 */
#search {
  width: 100%;
  height: 100%;
  flex: 1; /* 남은 공간을 채우도록 설정 */
  color: rgb(255, 255, 255); /* 입력 텍스트 색상 설정 */
}

#search::placeholder {
  color: #8e8e8e; /* 입력창의 플레이스홀더(검색어를 입력하세요) 색상 설정 */
}

#search:focus {
  color: white;
}

#search:focus::placeholder {
  color: gray;
}

.image {
  width: 45px;
  height: 45px;
  border-radius: 5px;
  object-fit: cover;
}
.drawer-content {
  height: calc(100vh - 56px); /* 56px는 앱 바 높이 */
  overflow-y: auto; /* 세로 스크롤 활성화 */
}
.drawer-content::-webkit-scrollbar {
  width: 5px;
}

.drawer-content::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤바 색상 */
  border-radius: 5px;
}

.drawer-content::-webkit-scrollbar-thumb:hover {
  background-color: #555;
}
</style>
