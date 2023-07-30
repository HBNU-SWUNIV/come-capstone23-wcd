<template>
  <div class="NoticeCreatePage">
    <div class="NoticeCreateWrap">
      <div class="NoticeCreateTitle">공지사항 작성</div>
      <form class="NoticeCreateForm" @submit.prevent="NoticeCreateSubmit">
        <div class="NoticeCreateBox">
          <input
            type="text"
            class="form-control"
            placeholder="제목"
            v-model="title"
            style="font-size: 25px; width: 800px"
            required
          />

          <textarea
            type="text"
            class="form-control"
            placeholder="내용"
            v-model="content"
            style="font-size: 25px; width: 800px; height: 300px"
            required
          ></textarea>
        </div>

        <div
          class="NoticeCreateSubmitBox d-flex flex-row justify-content-end mt-5"
          style="width: 800px"
        >
          <button
            class="btn btn-cancel text-center mr-3"
            style="background-color: red; width: 100px"
          >
            취소
          </button>
          <button
            class="btn btn-block text-center"
            type="submit"
            style="width: 100px; background-color: green"
          >
            게시
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
  
  <script>
import { ref } from "vue";
import axios from "axios";
import router from "../router/index";

export default {
  setup() {
    const title = ref("");
    const content = ref("");

    // 클럽아이디 추가해야함
    const NoticeCreateSubmit = async () => {
      const currentTime = new Date();
      const NoticeData = {
        title: title.value,
        content: content.value,
        createAt: currentTime,
      };
      try {
        if (title.value === "") {
          alert("제목을 입력하세요.");
          return;
        }
        if (content.value === "") {
          alert("내용 입력하세요.");
          return;
        }
        await axios
          .post("http://localhost:3000/notices", JSON.stringify(NoticeData))
          .then((res) => {
            console.log(res);
            alert("공지사항이 생성되었습니다.");

            router.push({
              name: "NoticePage",
            });
          });
      } catch (err) {
        alert("다시 시도해주세요.");
        console.log(err);
      }
    };
    return {
      title,
      content,
      NoticeCreateSubmit,
    };
  },
};
</script>
  
  <style>
.NoticeCreatePage {
  display: flex;
  justify-content: center;
  height: 100%;
  width: 100%;
}
.NoticeCreateWrap {
  height: 400px;
  width: 1000px;
  margin-top: 15%;
}
.NoticeCreateTitle {
  font-size: 60px;
  text-align: center;
}
.NoticeCreateForm {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.NoticeCreateBox {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.NoticeCreateSubmitBox {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.form-control {
  height: 50px;
  width: 500px;
  margin-top: 30px;
}
</style>