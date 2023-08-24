<template>
  <v-navigation-drawer width="244">
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
import { ref, watch } from "vue";
import { useRoute } from "vue-router";

export default {
  setup() {
    const route = useRoute();
    const items = ref([]);

    // 목록 항목을 생성 및 반환하는 함수
    const createItems = (currentPath) => {
      // 현재 경로에 따라 다른 목록을 반환
      if (currentPath === '/') {
        return [
          { title: '홈', path: `/` },
          { title: '모임찾기', path: `/find` }
        ];
      } else if (currentPath.startsWith('/clubs')) {
        const id = route.params.clubId;
        return [
          { title: '홈', path: `/clubs/${id}` },
          { title: '공지사항', path: `/clubs/${id}/notice` },
          { title: '일정', path: `/clubs/${id}/schedule` },
          { title: '게시판', path: `/clubs/${id}/board` },
        ];
      }
      return [];
    }

    // 현재 경로 변경 감지
    watch(() => route.path, (newPath) => {
      items.value = createItems(newPath);
    }, { immediate: true });  // 초기 설정 시에도 실행

    return {
      items
    }
  }
};
</script>