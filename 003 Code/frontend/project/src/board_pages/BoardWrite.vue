<template>
  <div class="item align-self-center">
    <div class="board-contents">
      <input type="text" class="form-control" placeholder="제목" v-model="boardTitle" id="boardTitle"/>
      <input type="text" class="form-control" placeholder="작성자" v-model="boardWriter" id="boardWriter"/>
    </div>
    <div class="board-contents">
      <textarea class="form-control" placeholder="내용을 입력하세요." v-model="boardContents" id="boardContents"></textarea>
    </div>
    <div class="d-flex flex-row-reverse mt-3">
      <button type="button" class="btn" id="board_save_btn" @click="saveBoard">저장</button>
      <button type="button" class="btn" id="board_cancel_btn">취소</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

const data = () => {
  const boardTitle = document.getElementById('boardTitle').value;
  const boardWriter = document.getElementById('boardWriter').value;
  const boardContents = document.getElementById('boardContents').value;

  return {
    boardTitle: boardTitle,
    boardWriter: boardWriter,
    boardContents: boardContents,
  }
}

export default {

  setup() {

    const saveBoard = () => {
      axios.post(' http://localhost:3000/boards', data())
      .then(res => {
    	  console.log(res);
      })
      .then(err => {
    	  console.log(err);
      });
    };

    return {
      saveBoard,
    }
  }
}
</script>

<style>
#board_save_btn {
  background-color: green;
  color: white;
}
#board_cancel_btn {
  background-color: red;
  color: white;
}
</style>