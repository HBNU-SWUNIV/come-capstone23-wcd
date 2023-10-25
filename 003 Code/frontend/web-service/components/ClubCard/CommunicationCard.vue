<template>
  <v-card style="width: 50%; margin: 10px; height: fit-content;">
    <v-card-title>{{ title }}</v-card-title>
      <v-list>
        <v-list-item
          v-for="(room, i) in rooms"
          :key="i"
          @click="openChattingRoom(clubId)"
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
    openChattingRoom(clubId) {
      this.$router.push(`/clubs/${clubId}/chatting`)
    },
  },
  created() {
    // 라우터의 clubId 값을 가져와서 변수에 할당
    this.clubId = this.$route.params.clubId;
  },
};
</script>