<template>
  <v-card style="width:50%">
    <v-card-title>{{ title }}</v-card-title>
    <v-list>
      <v-list-item
        v-for="(club, i) in clubs"
        :key="i"
        :to="club.to"
        router
        exact
      >
        <v-list-item-action>
          <v-icon>{{ club.icon }}</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>{{ club.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-card>
</template>

<script>
export default {
  name: "MyClubCard",
  data() {
    return {
      clubs: [
        {
          icon: "mdi-panorama-variant-outline",
          title: "Club 1",
          to: "/club/1",
        },
        {
          icon: "mdi-panorama-variant-outline",
          title: "Club 2",
          to: "/club/2",
        },
        {
          icon: "mdi-panorama-variant-outline",
          title: "Club 3",
          to: "/club/3",
        },
        {
          icon: "mdi-panorama-variant-outline",
          title: "Club 4",
          to: "/club/4",
        },
        {
          icon: "mdi-panorama-variant-outline",
          title: "Club 5",
          to: "/club/5",
        },
      ],
      title: "My Club",
    };
  },
};
</script>
