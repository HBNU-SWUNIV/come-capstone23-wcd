<template>
  <div class="NoticePage">
    <div class="SearchArea">
      <div class="d-flex flex-row justify-content-center">
        <form class="mt-4" style="margin-left: 200px">
          <select class="NoticeFilter form-select">
            <option selected>필터</option>
            <option value="title" class="mt-3">제목</option>
            <option value="writer" class="mt-3">작성자</option>
            <option value="date" class="mt-3">날짜</option>
          </select>

          <input type="text" class="NoticeSearchInput search-input" />

          <button class="NoticeSearchBtn" type="submit" @click="getBoard">
            검색
          </button>
        </form>
        <button class="WriteBtn" @click="goCreateNoticePage">글 작성</button>
      </div>
    </div>
    <div class="NoticeBar">
      <label class="NoticeNav" style="margin-left: 150px">NO.</label>
      <label class="NoticeNav" style="margin-left: 425px">제목</label>
      <label class="NoticeNav" style="margin-left: 425px">작성자</label>
      <label class="NoticeNav" style="margin-left: 200px">날짜</label>
    </div>
    <div class="NoticeContentsArea">
      <div
        class="d-flex flex-row mt-4"
        v-for="notice in displayedBoards"
        :key="notice.id"
      >
        <div
          class="Contents"
          style="width: 100px; text-align: center; margin-left: 115px; "
        >
          <label> {{ notice.id }} </label>
        </div>
        <div
          class="Contents"
          style="width: 650px; text-align: left; margin-left: 100px; cursor: pointer;"
          @click="goNoticeDetail(notice.id)"
        >
          <label> {{ notice.title }}</label>
        </div>
        <div
          class="Contents"
          style="width: 200px; text-align: center; margin-left: 30px"
        >
          <label>내가 누구게</label>
        </div>
        <div
          class="Contents"
          style="width: 250px; text-align: center; margin-left: 20px"
        >
          <label>2020-01-01</label>
        </div>
      </div>
    </div>
    <div class="PageNavArea" v-if="totalPages > 1">
      <div
        class="PageBox"
        @click="prevPage"
        v-show="currentPage !== 1"
        style="cursor: pointer"
      >
        이전
      </div>
      <div
        class="PageBox"
        v-for="pageNumber in totalPages"
        :key="pageNumber"
        @click="gotoPage(pageNumber)"
        :class="{ active: currentPage === pageNumber }"
        style="cursor: pointer"
      >
        {{ pageNumber }}
      </div>
      <div
        class="PageBox"
        @click="nextPage"
        v-show="currentPage !== totalPages"
        style="cursor: pointer"
      >
        다음
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router/index";
import { ref, computed } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";

export default {
  setup() {
    const route = useRoute();
    const currentClubId = route.params.id;

    const goCreateNoticePage = () => {
      router.push({
        name: "CreateNoticePage",
      });
    };

    const goNoticeDetail = (a) => {
      router.push({
        name:'NoticeDetail',
        params: {
          num: a,
        },
      })
    }

    const perPage = 10; // 페이지 당 보여줄 항목 수
    const notices = ref([]);
    const currentPage = ref(1);

    // JSON 서버에서 데이터 받아오기
    const fetchData = async () => {
      const response = await axios.get("http://localhost:3000/notices");
      const noticesData = response.data.map((item) => {
        const parsedItem = JSON.parse(Object.keys(item)[0]);
        parsedItem.id = item.id;
        return parsedItem;
      });

      const filteredNotices = noticesData.filter(
        (item) => item.clubId === currentClubId
      );

      notices.value = filteredNotices
        ? filteredNotices.sort((a, b) => b.id - a.id)
        : []; // id를 내림차순으로 정렬
    };

    fetchData(); // fetchData 함수를 호출하여 비동기로 데이터 받아오기

    const displayedBoards = computed(() => {
      const start = (currentPage.value - 1) * perPage;
      const end = start + perPage;
      return notices.value.slice(start, end);
    });

    const totalPages = computed(() => {
      return Math.ceil(notices.value.length / perPage);
    });

    const prevPage = () => {
      currentPage.value--;
    };

    const nextPage = () => {
      currentPage.value++;
    };

    const gotoPage = (pageNumber) => {
      currentPage.value = pageNumber;
    };

    return {
      goCreateNoticePage,
      displayedBoards,
      totalPages,
      currentPage,
      prevPage,
      nextPage,
      gotoPage,
      goNoticeDetail,
    };
  },
};
</script>

<style>
.SearchArea {
  height: 140px;
  width: 100%;
}
.NoticeBar {
  height: 40px;
  width: 100%;
  background-color: #383838;
}
.NoticeNav {
  font-size: 20px;
  margin-top: 0.4rem;
}
.NoticeContentsArea {
  height: 600px;
  width: 100%;
  font-size: 20px;
}
.PageNavArea {
  height: 50px;
  display: flex;
  justify-content: center;
  margin-top: 70px;
  font-size: 25px;
}
.PageBox {
  width: 80px;
  text-align: center;
}
.NoticeFilter {
  width: 95px;
  height: 40px;
  text-align: center;
  background-color: #383838;
  color: white;
  border: none;
  border-radius: 0.25rem;
  margin-top: 30px;
}
.NoticeSearchBtn {
  width: 95px;
  height: 40px;
  text-align: center;
  background-color: #383838;
  color: white;
  border: none;
  border-radius: 0.25rem;
  margin-top: 30px;
}
.NoticeSearchInput {
  width: 640px;
  height: 40px;
  border-radius: 0.25rem;
  margin-top: 30px;
}
.WriteBtn {
  background-color: #383838;
  border: none;
  color: white;
  height: 60px;
  width: 100px;
  border-radius: 5px 5px 5px 5px;
  margin-top: 44px;
  margin-left: 150px;
  font-size: 20px;
}
</style>