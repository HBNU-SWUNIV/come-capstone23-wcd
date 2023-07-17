<template>
  <div>
    <div class="item align-self-center" id="search_area">
      <div class="d-flex flex-row justify-content-center">
        <select class="form-select" id="board_filter">
          <option selected>필터</option>
          <option value="title">제목</option>
          <option value="writer">작성자</option>
          <option value="date">날짜</option>
        </select>

        <input type="text" class="search-input" id="board_find" />

        <button class="btn" type="submit" id="board_search" @click="getBoard">
          검색
        </button>
      </div>
    </div>

    <div class="row-vh d-flex flex-row align-items-center" id="board_area">
      <div style="margin-left: 200px">NO.</div>
      <div style="margin-left: 400px">제목</div>
      <div style="margin-left: 400px">작성자</div>
      <div style="margin-left: 150px">날짜</div>
    </div>

    <div id="contents_area">
      <div id="board_table_area">
        <table class="w3-table-all" id="table">
          <tbody>
            <tr v-for="(row, idx) in list" :key="idx">
              <td id="board_num">{{ row.id }}</td>
              <td id="board_title">
                <a v-on:click="fnView(`${row.id}`)">{{ row.boardTitle }}</a>
              </td>
              <td id="board_writer">{{ row.boardWriter }}</td>
              <td id="board_time">{{ row.boardDate }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div id="board_page_area">
        <div
          class="pagination w3-bar w3-padding-16 w3-small"
          v-if="paging.total_list_cnt > 0"
        >
          <span class="pg">
            <a
              href="javascript:;"
              @click="fnPage(1)"
              class="first w3-button w3-bar-item w3-border"
              >&lt;&lt;</a
            >
            <a
              href="javascript:;"
              v-if="paging.start_page > 10"
              @click="fnPage(`${paging.start_page - 1}`)"
              class="prev w3-button w3-bar-item w3-border"
              >&lt;</a
            >
            <template v-for="(n, index) in paginavigation()">
              <template v-if="paging.page == n">
                <strong
                  class="w3-button w3-bar-item w3-border w3-green"
                  :key="index"
                  >{{ n }}</strong
                >
              </template>
              <template v-else>
                <a
                  class="w3-button w3-bar-item w3-border"
                  href="javascript:;"
                  @click="fnPage(`${n}`)"
                  :key="index"
                  >{{ n }}</a
                >
              </template>
            </template>
            <a
              href="javascript:;"
              v-if="paging.total_page_cnt > paging.end_page"
              @click="fnPage(`${paging.end_page + 1}`)"
              class="next w3-button w3-bar-item w3-border"
              >&gt;</a
            >
            <a
              href="javascript:;"
              @click="fnPage(`${paging.total_page_cnt}`)"
              class="last w3-button w3-bar-item w3-border"
              >&gt;&gt;</a
            >
          </span>
        </div>

        <button
          type="button"
          class="btn"
          @click="boardCreate"
          id="boardCreateBtn"
        >
          등록
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    //변수생성
    return {
      requestBody: {}, //리스트 페이지 데이터전송
      list: {}, //리스트 데이터
      no: "", //게시판 숫자처리
      paging: {
        block: 0,
        end_page: 0,
        next_block: 0,
        page: 0,
        page_size: 0,
        prev_block: 0,
        start_index: 0,
        start_page: 0,
        total_block_cnt: 0,
        total_list_cnt: 0,
        total_page_cnt: 0,
      }, //페이징 데이터
      page: this.$route.query.page ? this.$route.query.page : 1,
      size: this.$route.query.size ? this.$route.query.size : 10,
      keyword: this.$route.query.keyword,
      paginavigation: function () {
        //페이징 처리 for문 커스텀
        let pageNumber = []; //;
        let start_page = this.paging.start_page;
        let end_page = this.paging.end_page;
        for (let i = start_page; i <= end_page; i++) pageNumber.push(i);
        return pageNumber;
      },
    };
  },
  mounted() {
    this.fnGetList();
  },
  methods: {
    fnGetList() {
      this.requestBody = {
        // 데이터 전송
        keyword: this.keyword,
        page: this.page,
        size: this.size,
      };

      this.$axios
        .get("http://localhost:3000/boards", {
          params: this.requestBody,
          headers: {},
        })
        .then((res) => {
          // if (res.data.result_code === "OK") {
          this.list = res.data; //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
          // this.paging = res.data.pagination;
          // this.no = this.paging.total_list_cnt - ((this.paging.page - 1) * this.paging.page_size);
          // }
        })
        .catch((err) => {
          if (err.message.indexOf("Network Error") > -1) {
            alert("네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.");
          }
        });
    },
    fnView(idx) {
      this.requestBody.idx = idx;
      this.$router.push({
        path: "/board/detail",
        query: this.requestBody,
      });
    },
    fnWrite() {
      this.$router.push({
        path: "/boards/write",
      });
    },
    fnPage(n) {
      if (this.page !== n) {
        this.page = n;
        this.fnGetList();
      }
    },
  },
};
</script>

<script setup>
import axios from "axios";
import { useRouter } from "vue-router";
const router = useRouter();

const boardCreate = () => {
  router.push({
    path: "/board/write",
  });
};

const getBoard = () => {
  axios.get("http://localhost:3000/boards").then((res) => {
    console.log(res.data);
  });
};
</script>

<style>
table {
  color: white;
  font-size: 24px;
  width: 1300px;
  margin-left: 200px;
}
#search_area {
  width: 1500px;
  height: 100px;
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
  height: 860px;
}
#board_filter {
  width: 95px;
  height: 40px;
  text-align: center;
  background-color: #383838;
  color: white;
  border: none;
  border-radius: 0.25rem;
  margin-top: 30px;
}
#board_search {
  width: 95px;
  height: 40px;
  text-align: center;
  background-color: #383838;
  color: white;
  border: none;
  border-radius: 0.25rem;
  margin-top: 30px;
}
#board_find {
  width: 640px;
  height: 40px;
  border-radius: 0.25rem;
  margin-top: 30px;
}
#boardCreateBtn {
  position: absolute;
  height: 40px;
  width: 60px;
  left: 1350px;
  top: 900px;
  background-color: bisque;
  color: black;
}
#board_table_area {
  width: 1500px;
  height: 700px;
}
#board_page_area {
  width: 1500px;
  height: 160px;
}
#board_num {
  width: 150px;
  height: 70px;
}
#board_title {
  width: 700px;
  height: 70px;
}
#board_writer {
  width: 180px;
  height: 70px;
}
</style>