<template>
  <div style="width: 100%; padding: 40px">
    <h1 style="text-align: center; margin-bottom: 20px">채팅방</h1>
    <div
      class="drawer-content"
      style="
        height: 400px;
        margin-bottom: 15px;
        border: 1px solid #ccc;
        padding: 10px;
        overflow-y: auto;
      "
    >
      <p
        v-for="testmessage in testMessages"
        :key="testmessage.id"
        :style="{
          textAlign:
            parseInt(testmessage.senderId) === parseInt(user_id)
              ? 'right'
              : 'left',
          marginBottom: '8px',
        }"
      >
        <span
          :style="{
            fontWeight:
              parseInt(testmessage.senderId) === parseInt(user_id)
                ? 'bold'
                : 'normal',
          }"
          >{{ testmessage.sender }}:</span
        >
        {{ testmessage.message }}
        <span style="color: #777; font-size: 11px;"
          >{{ formatDate(testmessage.sendTime) }}</span
        >
      </p>
      <p
        v-for="message in messages"
        :key="message.id"
        style="color: black; margin-bottom: 8px"
      >
        <span style="font-weight: bold">{{ message.sender }}:</span>
        {{ message.message }}
        <span style="color: #777; font-size: 11px;">{{ formatDate(message.sendTime) }}</span>
      </p>
    </div>
    <div
      style="
        height: 100px;
        padding: 5px;
        border: 1px solid #ccc;
        border-top: none;
      "
    >
      <textarea
        v-model="message"
        placeholder="메시지를 입력하세요"
        @keyup.enter="sendMessage"
        style="
          flex: 1;
          padding: 10px;
          border: 1px solid white;
          border-radius: 5px;
          height: 100%;
          width: 100%;
          resize: none;
          color: black;
        "
      ></textarea>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "stompjs";

export default {
  data() {
    return {
      clubId: null,
      message: "",
      messages: [],
      stompClient: null,
      testMessages: [],
      userName: null,
      user_id: "",
    };
  },
  layout(context) {
    return "chatting";
  },
  methods: {
    connect() {
      const socket = new SockJS(
        `${process.env.API_BASE_URL}/chatting-service/ws`
      );
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect({}, this.onConnected, this.onError);
    },
    onConnected() {
      this.stompClient.subscribe(
        `/topic/${this.clubId}`,
        this.onMessageReceived
      );
    },
    onError(error) {
      console.error(error);
    },
    onMessageReceived(payload) {
      const parseMessage = JSON.parse(payload.body);
      this.messages.push(parseMessage);
    },
    sendMessage() {
      if (!this.message.trim()) {
        return;
      }

      let chat = {
        clubId: this.clubId,
        senderId: sessionStorage.getItem("user_id"),
        message: this.message,
      };

      if (this.stompClient) {
        this.stompClient.send("/app/chat/send", {}, JSON.stringify(chat));

        this.message = "";
      } else {
        console.error("WebSocket connection is not established.");
      }
    },

    async getMessage() {
      try {
        const access_token = this.$store.state.access_token;
        const config = {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
          },
          params: {
            clubId: this.clubId,
            page: 0,
            size: 10,
          },
        };
        await this.$axios
          .get(`${process.env.API_BASE_URL}/chatting-service/chat`, config)
          .then((res) => {
            console.log(res.data.content);
            this.testMessages = res.data.content.reverse();
            console.log(this.testMessages);
          });
      } catch (err) {
        console.log(err);
      }
    },
    formatDate(dateTimeString) {
      const date = new Date(dateTimeString);
      const year = date.getFullYear().toString().slice(2);
      const month = (date.getMonth() + 1).toString().padStart(2, "0");
      const day = date.getDate().toString().padStart(2, "0");
      const hours = date.getHours().toString().padStart(2, "0");
      const minutes = date.getMinutes().toString().padStart(2, "0");

      return `${year}.${month}.${day} ${hours}:${minutes}`;
    },
  },
  async created() {
    this.user_id = sessionStorage.getItem("user_id");
    this.clubId = this.$route.params.clubId;
    this.connect();
    this.getMessage();
  },
};
</script>

<style scoped>
/* 여기에 스타일 추가 가능 */
.drawer-content p {
  background-color: #f9f9f9;
  padding: 8px;
  border-radius: 8px;
}
</style>
