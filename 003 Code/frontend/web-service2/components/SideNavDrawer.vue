<template>
    <v-navigation-drawer color="grey-lighten-3" rail>
      <router-link to="/">
        <v-avatar
            class="d-block text-center mx-auto mt-4"
            color="grey-darken-1"
            size="36"
        ></v-avatar>
      </router-link>
      <v-divider class="mx-3 my-5"></v-divider>

      <template v-for="(club) in clubs">
        <router-link :to="`/clubs/${club.id}`">
        <v-avatar
              class="d-block text-center mx-auto mt-4"
              color="grey-darken-1"
              size="36"
          ></v-avatar>
        </router-link>
      </template>
    </v-navigation-drawer>
</template>

<script>
// SideNavDrawer.vue
import { ref } from 'vue';
import axios from 'axios';
import { useAuthStore } from "@/stores/auth";

export default {
  async setup() {
    const clubs = ref([]);
    const authStore = useAuthStore();

    async function fetchData() {
      try {
        const res = await axios.get(`/club-service/users/${authStore.userId}/clubs`);

        if (res.status === 200) {
          clubs.value = res.data;
        }
      } catch (error) {
        console.error("클럽 목록을 불러오는 중 오류가 발생했습니다.", error);
      }
    }

    await fetchData();

    return {
      clubs
    }
  }
}
</script>