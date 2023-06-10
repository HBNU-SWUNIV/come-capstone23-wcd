<template>
  <div id="board_detail_all">
    <div id="board_contents_area">
      <div id="brd_title">{{ boardTitle }}</div>
      <br />
      <div id="brd_writer">{{ boardWriter }}</div>
      <div id="brd_date">{{ boardDate + " " + " " + boardTime }}</div>
      <br />
      <div id="brd_contents">{{ boardContents }}</div>
    </div>

    <div>
      <button
        type="button"
        class="btn"
        id="boardUpdateBtn"
        v-on:click="fnUpdate"
      >
        수정
      </button>
      <button
        type="button"
        class="btn"
        id="boardDeleteBtn"
        v-on:click="fnDelete"
      >
        삭제
      </button>
      <button type="button" class="btn" id="boardListBtn" v-on:click="fnList">
        목록
      </button>
    </div>
  </div>
</template>
  
  <script>
export default {
  data() {
    //변수생성
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,

      boardTitle: "",
      boardWriter: "",
      boardContents: "",
      boardDate: "",
      boardTime: "",
    };
  },
  mounted() {
    this.fnGetView();
  },
  methods: {
    fnGetView() {
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
          if (err.message.indexOf("Network Error") > -1) {
            alert("네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.");
          }
        });
    },
    fnList() {
      delete this.requestBody.idx;
      this.$router.push({
        path: "/board",
        query: this.requestBody,
      });
    },
    fnUpdate() {
      this.$router.push({
        path: "/board/write",
        query: this.requestBody,
      });
    },
    fnDelete() {
      if (!confirm("삭제하시겠습니까?")) return;

      this.$axios
        .delete("http://localhost:3000/boards/" + this.idx, {})
        .then(() => {
          alert("삭제되었습니다.");
          this.fnList();
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style>
#board_detail_all {
  width: 1500px;
  height: 1000px;
}
#board_contents_area {
  width: 1500px;
  height: 850px;
}
#brd_title {
  width: 1000px;
  margin-top: 150px;
  margin-left: 250px;
  font-size: 60px;
  color: white;
}
#brd_writer {
  width: 1000px;
  margin-left: 250px;
  font-size: 25px;
  color: white;
}
#brd_date {
  width: 1000px;
  margin-left: 250px;
  font-size: 20px;
  color: white;
}
#brd_contents {
  width: 1000px;
  margin-left: 250px;
  font-size: 35px;
  color: white;
}
#boardUpdateBtn {
  position: absolute;
  height: 40px;
  width: 60px;
  left: 1210px;
  top: 900px;
  background-color: blue;
  color: white;
}
#boardDeleteBtn {
  position: absolute;
  height: 40px;
  width: 60px;
  left: 1280px;
  top: 900px;
  background-color: red;
  color: white;
}
#boardListBtn {
  position: absolute;
  height: 40px;
  width: 60px;
  left: 1350px;
  top: 900px;
  background-color: bisque;
  color: black;
}
</style>