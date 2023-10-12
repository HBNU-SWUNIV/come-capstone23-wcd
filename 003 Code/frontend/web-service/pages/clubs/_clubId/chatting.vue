<template>
  <div>
    <div
      style="
        background-color: rgb(243, 94, 49);
        height: 65px;
        text-align: center;
        display: flex;
        align-items: center;
        justify-content: center;
      "
    >
      <h3>채팅방</h3>
    </div>
    <div style="height: 600px"></div>
    <div style="height: 50px; background-color: gray"></div>
    <div style="height: 110px; padding: 5px">
      <textarea
        v-model="message"
        placeholder="메시지를 입력하세요"
        @keydown.enter.shift="addNewLine"
        style="
          flex: 1;
          padding: 10px;
          border-radius: 5px;
          height: 100%;
          width: 100%;
          resize: none;
        "
      ></textarea>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      message: "",
    };
  },
  layout(context) {
    return "chatting";
  },
  methods: {
    sendMessage() {
      if (this.message.trim() !== "") {
        // 메시지 전송 로직을 추가하세요
        console.log("전송 메시지:", this.message);
        // 메시지 전송 후 입력창 비우기
        this.message = "";
      }
    },
    addNewLine() {
      // 엔터 키가 눌릴 때 줄 바꿈을 처리합니다.
      this.message += "\n";
    },
  },
};
</script>
