<template>
  <v-row justify="center"  v-if="club && club.clubName">
    <v-col cols="12">
      <v-card>
        <v-card-title>{{ club.clubName }}</v-card-title>
        <v-card-subtitle>멤버 : 1 / {{ club.maximumPeople }}</v-card-subtitle>
        <v-card-text>{{ club.description }}</v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-btn
              :color="club.approvalMethod === 'FREE' ? 'green' : 'red'"
              text
          >
            {{ club.approvalMethod === 'FREE' ? '자동 가입 가능합니다.' : '관리자의 승인 후 가입 가능합니다.' }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { ref } from 'vue';
import { useRoute } from "vue-router";
import axios from 'axios';

export default {
  setup() {
    const route = useRoute();
    const club = ref([]);
    const currentClubId = computed(() => route.params.clubId);

    const fetchData = async () => {
      try {
        const response = await axios.get(`club-service/clubs/${currentClubId.value}`);
        club.value = response.data;
      } catch (error) {
        console.error("클럽 데이터를 가져오는 중 오류 발생:", error);
      }
    };

    // 페이지 새로고침, currentClubId 변경 시에 리로드
    watch(currentClubId, fetchData, { immediate: true });

    return {
      club,
    };
  },
};
</script>
