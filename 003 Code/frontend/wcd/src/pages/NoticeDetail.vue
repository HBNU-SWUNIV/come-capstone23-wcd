<template>
  <div>
    <div class="TitleArea">
      <label class="NoticeTitle">{{ notices.title }}</label>
    </div>
    <div class="InfoArea">
      <label class="NoticeInfo">[ 작성자 ]</label>
      <label class="NoticeInfo">2020-01-25</label>
    </div>
    <div class="ContentArea">
      <label class="NoticeContent">{{ notices.content }}</label>
    </div>
    <div class="BtnArea">
      <button
        class="button"
        style="background-color: gray"
        @click="goNoticePage"
      >
        목록
      </button>
      <button class="button" style="background-color: green">수정</button>
      <button class="button" style="background-color: red" @click="deleteNotice(notices.id)">삭제</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { ref } from "vue";
import { useRoute } from "vue-router";
import router from "@/router/index";

export default {
  setup() {
    const notices = ref([]);
    const route = useRoute();
    const currentNoticeId = route.params.num;

    const goNoticePage = () => {
      router.push({
        name: "ClubNoticePage",
        params: {
          id: route.params.id,
        },
      });
    };

    const fetchData = async () => {
      const response = await axios.get("http://localhost:3000/notices");
      const noticesData = response.data.map((item) => {
        const parsedItem = JSON.parse(Object.keys(item)[0]);
        parsedItem.id = item.id;
        return parsedItem;
      });

      notices.value = noticesData[currentNoticeId - 1];
    };

    const deleteNotice = async (id) => {
      try {
        // JSON-Server API의 URL을 포함한 요청 주소를 생성합니다.
        const url = `http://localhost:3000/notices/${id}`;

        // 서버에 DELETE 요청을 보냅니다.
        await axios.delete(url);

        // 성공적으로 삭제되면 원하는 동작을 수행하도록 추가적인 로직을 구현합니다.
        console.log(`ID ${id}에 해당하는 데이터가 성공적으로 삭제되었습니다.`);
        alert("공지사항이 삭제 되었습니다.");
        goNoticePage();
      } catch (error) {
        console.error("데이터 삭제 중 오류 발생:", error);
      }
    };

    fetchData();

    return {
      notices,
      goNoticePage,
      deleteNotice,
    };
  },
};
</script>

<style>
.TitleArea {
  height: 300px;
  display: flex;
  flex-direction: column;
}
.NoticeTitle {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: end;
  font-size: 70px;
  margin-left: 150px;
}
.InfoArea {
  height: 100px;
  display: flex;
  flex-direction: column;
}
.NoticeInfo {
  font-size: 20px;
  margin-left: 150px;
}
.ContentArea {
  height: 400px;
}
.NoticeContent {
  height: 100%;
  display: flex;
  font-size: 40px;
  margin-left: 150px;
}
.BtnArea {
  height: 200px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-left: 20px;
}
.button {
  height: 60px;
  width: 80px;
  border-radius: 10px;
  margin: 10px;
  font-size: 20px;
  color: white;
}
</style>