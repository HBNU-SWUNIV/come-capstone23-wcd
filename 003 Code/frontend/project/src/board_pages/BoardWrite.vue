<template>
  <div class="item align-self-center" @submit.prevent="onSubmit">
    <div class="board-contents">
      <input
        type="text"
        class="form-control"
        placeholder="제목"
        v-model="boardTitle"
        id="boardTitle"
      />
      <input
        type="text"
        class="form-control"
        placeholder="작성자"
        v-model="boardWriter"
        id="boardWriter"
      />
    </div>
    <div class="board-contents">
      <textarea
        class="form-control"
        placeholder="내용을 입력하세요."
        v-model="boardContents"
        id="boardContents"
      ></textarea>
    </div>
    <div class="d-flex flex-row-reverse mt-3">
      <button type="button" class="btn" id="board_save_btn" @click="fnSave">
        저장
      </button>
      <button
        type="button"
        class="btn"
        id="board_cancel_btn"
        @click="cancelBoard"
      >
        취소
      </button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    //변수생성
    var today = new Date();
    var year = today.getFullYear();
    var month = ("0" + (today.getMonth() + 1)).slice(-2);
    var day = ("0" + today.getDate()).slice(-2);
    var dateString = year + "-" + month + "-" + day;

    var hours = ("0" + today.getHours()).slice(-2);
    var minutes = ("0" + today.getMinutes()).slice(-2);
    var seconds = ("0" + today.getSeconds()).slice(-2);
    var timeString = hours + ":" + minutes + ":" + seconds;

    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,

      boardTitle: "",
      boardWriter: "",
      boardContents: "",
      boardDate: dateString,
      boardTime: timeString,
    };
  },
  mounted() {
    this.fnGetView();
  },
  methods: {
    fnGetView() {
      if (this.idx !== undefined) {
        this.$axios
          .get("http://localhost:3000/boards/" + this.idx, {
            params: this.requestBody,
          })
          .then((res) => {
            this.boardTitle = res.data.boardTitle;
            this.boardWriter = res.data.boardWriter;
            this.boardContents = res.data.boardContents;
            this.boardDate = res.data.boardDate;
            this.boardTime = res.data.boardTime;
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    fnList() {
      delete this.requestBody.idx;
      this.$router.push({
        path: "/board",
        query: this.requestBody,
      });
    },
    fnView(idx) {
      this.requestBody.idx = idx;
      this.$router.push({
        path: "/boards/detail",
        query: this.requestBody,
      });
    },
    fnSave() {
      let apiUrl = "http://localhost:3000/boards";
      this.form = {
        idx: this.idx,
        boardTitle: this.boardTitle,
        boardWriter: this.boardWriter,
        boardContents: this.boardContents,
        boardDate: this.boardDate,
        boardTime: this.boardTime,
      };
      this.$axios
        .post(apiUrl, this.form)
        .then((res) => {
          console.log(res);
        })
        .then((err) => {
          console.log(err);
        });
      this.$router.push({
        path: "/board",
        query: this.requestBody,
      });

      // if (this.idx === undefined) {
      //   //INSERT
      //   this.$axios
      //     .post(apiUrl, this.form)
      //     .then((res) => {
      //       alert("글이 저장되었습니다.");
      //       this.fnView(res.data.idx);
      //     })
      //     .catch((err) => {
      //       if (err.message.indexOf("Network Error") > -1) {
      //         alert(
      //           "네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요."
      //         );
      //       }
      //     });
      // } else {
      //   //UPDATE
      //   this.$axios
      //     .patch(apiUrl, this.form)
      //     .then((res) => {
      //       alert("글이 저장되었습니다.");
      //       this.fnView(res.data.idx);
      //     })
      //     .catch((err) => {
      //       if (err.message.indexOf("Network Error") > -1) {
      //         alert(
      //           "네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요."
      //         );
      //       }
      //     });
      // }
    },
  },
};
</script>
<!-- <script>
import axios from "axios";
import { useRouter } from "vue-router";

const data = () => {
  const boardTitle = document.getElementById("boardTitle").value;
  const boardWriter = document.getElementById("boardWriter").value;
  const boardContents = document.getElementById("boardContents").value;

  var today = new Date();
  var year = today.getFullYear();
  var month = ("0" + (today.getMonth() + 1)).slice(-2);
  var day = ("0" + today.getDate()).slice(-2);
  var dateString = year + "-" + month + "-" + day;

  var hours = ("0" + today.getHours()).slice(-2);
  var minutes = ("0" + today.getMinutes()).slice(-2);
  var seconds = ("0" + today.getSeconds()).slice(-2);
  var timeString = hours + ":" + minutes + ":" + seconds;

  return {
    boardTitle: boardTitle,
    boardWriter: boardWriter,
    boardContents: boardContents,
    boardDate: dateString,
    boardTime: timeString,
  };
};

export default {
  setup() {
    const router = useRouter();

    const saveBoard = () => {
      axios
        .post("http://localhost:3000/boards", data())
        .then((res) => {
          console.log(res);
        })
        .then((err) => {
          console.log(err);
        });
      router.push({
        path: "/board",
      });
    };

    const cancelBoard = () => {
      router.push({
        path: "/board",
      });
    };

    return {
      saveBoard,
      cancelBoard,
    };
  },
};
</script> -->

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