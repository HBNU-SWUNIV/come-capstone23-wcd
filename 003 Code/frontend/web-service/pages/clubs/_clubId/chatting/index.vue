<template>
  <div style="width:100%; padding:40px;">
    <h1 style="text-align: center; margin-bottom: 20px;">채팅방</h1>
    <div class="drawer-content" style="height:400px; margin-bottom: 15px;">
      <p v-for="testmessage in testMessages" :key="testmessage.id">
        {{ testmessage.sender }}: {{ testmessage.message }} [{{
          testmessage.sendTime
        }}]
      </p>
      <p v-for="message in messages" :key="message.id" style="color: black">
        {{ message.sender }}: {{ message.message }} [{{ message.sendTime }}]
      </p>
    </div>
    <div style="height: 50px; background-color: rgb(255, 255, 255)">
      <v-btn>알림 활성화</v-btn>
    </div>
    <div style="height: 100px; padding: 5px">
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
          color:black;
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
      clubName: null,
      userName: null,
    };
  },
  layout(context) {
    return "chatting";
  },
  methods: {
    connect() {
      const socket = new SockJS(`${process.env.API_BASE_URL}/chatting-service/ws`);
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
          .get(`/chatting-service/chat`, config)
          .then((res) => {
            console.log(res);
            console.log(res.data.content);
            this.testMessages = res.data.content.reverse();
            console.log(this.testMessages);
          });
      } catch (err) {
        console.error("err", err);
      }
    },
    async getClubInfo() {
      try {
        const access_token = this.$store.state.access_token;
        const config = {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${access_token}`,
          },
        };
        await this.$axios
          .get(`/club-service/clubs/${this.$route.params.clubId}`, config)
          .then((res) => {
            console.log(res);
            this.clubName = res.data.clubName;
          });
      } catch (err) {
        console.log(err);
      }
    },
  },
  async created() {
    this.clubId = this.$route.params.clubId;
    this.getClubInfo();
    this.connect();
    this.getMessage();
  },
};
</script>
