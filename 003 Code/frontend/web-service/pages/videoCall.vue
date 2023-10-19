<template>
  <div id="main" style="text-align: center;">
    <h1>Join a video session</h1>
    <form @submit.prevent="joinSession">
      <p>
        <label>Session:</label>
        <input type="text" v-model="sessionName" required>
      </p>
      <p>
        <label>User:</label>
        <input type="text" v-model="participantName">
      </p>
      <p>
        <input type="submit" value="JOIN">
      </p>
    </form>

    <openvidu-webcomponent ref="webComponent" style="height: 100%;"></openvidu-webcomponent>
  </div>
</template>

<script src="/static/openvidu-webcomponent-2.28.0.js"></script>
<script>
import $ from 'jquery'; // Assuming you're using jQuery

export default {
  data() {
    return {
      sessionName: 'SessionA',
      participantName: 'User1',
      APPLICATION_SERVER_URL: 'https://wcd.kro.kr/api/openvidu/',
    };
  },
  mounted() {
    const webComponent = this.$refs.webComponent;

    webComponent.addEventListener('onSessionCreated', (event) => {
      // ... your code ...
    });
    // ... other event listeners ...

    this.hideForm();
  },
  methods: {
    async joinSession() {
      const tokens = await Promise.all([
        this.getToken(this.sessionName),
        this.getToken(this.sessionName)
      ]);
      this.$refs.webComponent.tokens = {
        webcam: tokens[0],
        screen: tokens[1]
      };
    },
    hideForm() {
      this.$refs.main.style.display = 'none';
    },
    async getToken(mySessionId) {
      const sessionId = await this.createSession(mySessionId);
      return this.createToken(sessionId);
    },
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        $.ajax({
          type: "POST",
          url: this.APPLICATION_SERVER_URL + "sessions",
          data: JSON.stringify({ customSessionId: sessionId }),
          headers: { "Content-Type": "application/json" },
          success: response => resolve(response),
          error: error => reject(error)
        });
      });
    },
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        $.ajax({
          type: 'POST',
          url: this.APPLICATION_SERVER_URL + 'sessions/' + sessionId + '/connections',
          data: JSON.stringify({}),
          headers: { "Content-Type": "application/json" },
          success: response => resolve(response),
          error: error => reject(error)
        });
      });
    }
  }
}
</script>

<style src="/static/openvidu-webcomponent-2.28.0.css"></style>
