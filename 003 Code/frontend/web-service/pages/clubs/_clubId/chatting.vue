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
    <div style="height: 600px">
      <ul>
        <li v-for="testmessage in testMessages" :key="testmessage.id" style="color:black;">
          {{ testmessage.senderId }}: {{ testmessage.message }} [{{ testmessage.sendTime }}]
        </li>
        <li v-for="message in messages" :key="message.id" style="color:black;">
          {{ message.sender }}: {{ message.message }} [{{ message.sendTime }}]
        </li>
      </ul>
    </div>
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
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

export default {
  data() {
    return {
      clubId: null,
      message: "",
      messages: [],
      stompClient: null,
      testMessages: [],
    };
  },
  layout(context) {
    return "chatting";
  },
  methods: {
    connect() {
      const socket = new SockJS("http://211.115.222.246:8777/ws");
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect({}, this.onConnected, this.onError);
    },
    onConnected(){
      this.stompClient.subscribe(`/topic/${this.clubId}`, this.onMessageReceived)
    },
    onError(error){
      console.error(error)
    },
    onMessageReceived(payload){
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
    async getMessage(){
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
          .get(
            `http://211.115.222.246:8777/chat`,
            config
          )
          .then((res) => {
            console.log(res.data.content);
            this.testMessages = res.data.content.reverse();
            console.log(this.testMessages)
          });
      } catch (err) {
        console.error("err", err);
      }
    }
  },
  created() {
    this.clubId = this.$route.params.clubId;
    this.connect();
    this.getMessage()
  },
};
</script>
