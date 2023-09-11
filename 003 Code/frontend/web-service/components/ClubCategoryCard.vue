<template>
  <v-card>
    <v-card-title>{{ title }}</v-card-title>
    <v-list style="display: flex; flex-direction: row">
      <v-list-item
        v-for="(category, i) in categories"
        :key="i"
        :to="category.to"
        router
        exact
        style="
          padding: 0;
          display: flex;
          flex-direction: row;
          justify-content: center;
        "
      >
        <v-list-item-action
          style="
            margin: 0;
            padding-top: 10px;
            padding-bottom: 10px;
            display: flex;
            flex-direction: column;
            align-items: center;
          "
        >
          <v-icon>{{ category.icon }}</v-icon>
          <v-list-item-title style="margin-top:5px;">{{ category.title }}</v-list-item-title>
        </v-list-item-action>
      </v-list-item>
    </v-list>
  </v-card>
</template>

<script>
export default {
  name: "ClubCategoryCard",
  data() {
    return {
      categories: [
        {
          icon: "mdi-music-box",
          title: "음악",
          to: "/music",
        },
        {
          icon: "mdi-chef-hat",
          title: "요리",
          to: "/cook",
        },
        {
          icon: "mdi-soccer",
          title: "운동",
          to: "/sports",
        },
        {
          icon: "mdi-video-vintage",
          title: "영화",
          to: "/movie",
        },
        {
          icon: "mdi-book-open-page-variant",
          title: "독서",
          to: "/book",
        },
        {
          icon: "mdi-airplane",
          title: "여행",
          to: "/travel",
        },
        {
          icon: "mdi-translate-variant",
          title: "언어교환",
          to: "/language",
        },
        {
          icon: "mdi-palette",
          title: "예술",
          to: "/art",
        },
      ],
      title: "Club Categories",
    };
  },
};
</script>
