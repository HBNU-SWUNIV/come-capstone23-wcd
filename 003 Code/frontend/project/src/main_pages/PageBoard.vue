<template>
  <div class="item align-self-center" id="search_area">
    <div class="d-flex flex-row justify-content-center">
      
      <select class="form-select" id="board_filter">
        <option selected>필터</option>
        <option value="title">제목</option>
        <option value="writer">작성자</option>
        <option value="date">날짜</option>
      </select>

      <input
        type="text"
        class="form-control"
        id="board_find"
      />

      <button class="btn" type="submit" id="board_search">검색</button>

    </div>
  </div>

  <div class="row-vh d-flex flex-row align-items-center" id="board_area">
    <div style="margin-left: 200px;"> NO. </div>
    <div style="margin-left: 400px;"> 제목 </div>
    <div style="margin-left: 400px;"> 작성자 </div>
    <div style="margin-left: 150px;"> 날짜 </div>
  </div>

  <div id="contents_area">
    <button type="button" class="btn" @click="boardCreate" id="boardCreateBtn">등록</button>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
const router = useRouter();

const boardCreate = () => {
  router.push({
    path: "/board/write",
  });
};
</script>

<style>
#search_area {
  width: 1500px;
  height: 140px;
}
#board_area {
  width: 1500px;
  height: 40px;
  background-color: #383838;
  color: white;
  font-size: 18px;
}
#contents_area {
  width: 1500px;
  height: 1000px;
}
#board_filter {
  width: 95px;
  height: 40px;
  text-align: center;
  background-color: #383838;
  color: white;
  border: none;
  border-radius: 0.25rem;
  margin-top: 40px;
}
#board_search {
  width: 95px;
  height: 40px;
  text-align: center;
  background-color: #383838;
  color: white;
  border: none;
  border-radius: 0.25rem;
  margin-top: 40px;
}
#board_find {
  width: 640px;
  height: 40px;
}
#boardCreateBtn {
  position: absolute;
  height: 40px;
  width: 60px;
  left: 1350px;
  top: 900px;
  background-color: bisque;
  color:black;
}
</style>