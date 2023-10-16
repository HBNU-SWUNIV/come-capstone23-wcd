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
        @keyup.enter="sendMessage"
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
      clubId: null,
    };
  },
  layout(context) {
    return "chatting";
  },
  methods: {
   
  },
  created() {
    this.clubId = this.$route.params.clubId;
  },
};
</script>
