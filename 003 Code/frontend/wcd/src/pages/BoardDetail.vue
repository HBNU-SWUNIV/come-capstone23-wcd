<template>
  <div>
    <div class="TitleArea">
      <label class="BoardTitle">{{ boards.title }}</label>
    </div>
    <div class="InfoArea">
      <label class="BoardInfo">[ 작성자 ]</label>
      <label class="BoardInfo">2020-01-25</label>
    </div>
    <div class="ContentArea">
      <label class="BoardContent">{{ boards.content }}</label>
    </div>
    <div class="BtnArea">
      <button
        class="button"
        style="background-color: gray"
        @click="goBoardPage"
      >
        목록
      </button>
      <button class="button" style="background-color: green">수정</button>
      <button class="button" style="background-color: red" @click="deleteBoard(boards.id)">
        삭제
      </button>
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
    const boards = ref([]);
    const route = useRoute();
    const currentBoardId = route.params.num;

    const goBoardPage = () => {
      router.push({
        name: "ClubBoardPage",
        params: {
          id: route.params.id,
        },
      });
    };

    const fetchData = async () => {
      const response = await axios.get("http://localhost:3000/boards");
      const boardsData = response.data.map((item) => {
        const parsedItem = JSON.parse(Object.keys(item)[0]);
        parsedItem.id = item.id;
        return parsedItem;
      });
      console.log(boardsData)
      boards.value = boardsData[currentBoardId - 1];
    };

    const deleteBoard = async (id) => {
      try {
        // JSON-Server API의 URL을 포함한 요청 주소를 생성합니다.
        const url = `http://localhost:3000/boards/${id}`;

        // 서버에 DELETE 요청을 보냅니다.
        await axios.delete(url);

        // 성공적으로 삭제되면 원하는 동작을 수행하도록 추가적인 로직을 구현합니다.
        console.log(`ID ${id}에 해당하는 데이터가 성공적으로 삭제되었습니다.`);
        alert("게시글이 삭제 되었습니다.");
        goBoardPage();
      } catch (error) {
        console.error('데이터 삭제 중 오류 발생:', error);
      }
    };

    fetchData();

    return {
      boards,
      goBoardPage,
      deleteBoard,
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
.BoardTitle {
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
.BoardInfo {
  font-size: 20px;
  margin-left: 150px;
}
.ContentArea {
  height: 400px;
}
.BoardContent {
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