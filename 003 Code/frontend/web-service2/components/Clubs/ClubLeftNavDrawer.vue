<template>
  <v-navigation-drawer width="244">
    <!-- <v-sheet color="grey-lighten-5" height="128" width="100%"></v-sheet> -->
    <v-list>
      <v-list-item
          v-for="item in items"
          :key="item.title"
          :to="item.path"
          link
      >
        {{ item.title }}
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
import {ref} from "vue";
import {useRoute } from "vue-router";

export default {
  setup() {
    const route = useRoute();
    const currentClubId = computed(() => route.params.clubId);

    const items = ref([]);

    // 목록 항목을 생성 및 반환하는 함수
    const createItems = (id) => {
      return [
        { title: '홈', path: `/clubs/${id}` },
        { title: '공지사항', path: `/clubs/${id}/notice` },
        { title: '일정', path: `/clubs/${id}/schedule` },
        { title: '게시판', path: `/clubs/${id}/board` },
      ];
    }

    // 현재 경로 변경 감지
    watch(currentClubId, (newId) => {
      items.value = createItems(newId);
    }, { immediate: true });  // 초기 설정 시에도 실행

    return {
      items
    }
  }
};
</script>