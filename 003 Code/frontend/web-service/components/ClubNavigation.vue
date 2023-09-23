<template>
  <v-list
    no-gutters
    justify-center
    class="d-flex"
    style="
      padding-left: 20%;
      padding-right: 20%;
      padding-bottom: 0;
      padding-top: 0;
    "
  >
    <v-list-item
      v-for="(navList, i) in navLists"
      :key="i"
      :to="`/clubs/${$route.params.clubId}${navList.to}`"
      router
      exact
      cols="auto"
      style="padding: 0; cursor: pointer;"
    >
      <v-list-item-content class="text-center" style="border-radius: 5px">
        <v-icon style="font-size: 18px">{{ navList.icon }}</v-icon>
        <v-list-item-title style="font-size: 14px">{{
          navList.title
        }}</v-list-item-title>
      </v-list-item-content>
    </v-list-item>
  </v-list>
</template>


<script>
export default {
  name: "ClubNavigation",
  data() {
    return {
      navLists: [
        {
          icon: "mdi-home",
          title: "홈",
          to: "",
        },
        {
          icon: "mdi-alert-box",
          title: "공지사항",
          to: "/notice",
        },
        {
          icon: "mdi-calendar",
          title: "일정",
          to: "/schedule",
        },
        {
          icon: "mdi-clipboard-text",
          title: "게시판",
          to: "/board",
        },
        {
          icon: "mdi-webcam",
          title: "화상채팅",
          to: "/cam",
        },
      ],
    };
  },
};
</script>