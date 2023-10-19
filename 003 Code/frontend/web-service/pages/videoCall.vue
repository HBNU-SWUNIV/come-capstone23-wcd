<template style="display: flex;
            justify-content: center;height: 100%; width: 100%;">
  <div id="main" style="text-align: center; width: 100%">
    <form @submit.prevent="joinSession" v-if="showForm">
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

    <openvidu-webcomponent ref="webComponent" style="height: 100%; width: 100%;"></openvidu-webcomponent>
  </div>
</template>

<script>
import $ from 'jquery'; // Assuming you're using jQuery

export default {
  head() {
    return {
      script: [
        {src: '/openvidu-webcomponent-2.28.0.js'}
      ],
      link: [
        { rel: 'stylesheet', href: '/openvidu-webcomponent-2.28.0.css' }
      ]
    }
  },
  data() {
    return {
      sessionName: 'SessionA',
      participantName: 'User1',
      APPLICATION_SERVER_URL: 'https://wcd.kro.kr/api/openvidu/',
      showForm: true,
      webComponent: null,
    };
  },
  mounted() {
    this.webComponent = this.$refs.webComponent;

    this.webComponent.addEventListener('onSessionCreated', (event) => {
      const session = event.detail;

      session.on('connectionCreated', (e) => {
        console.log("connectionCreated", e);
      });

      session.on('streamDestroyed', (e) => {
        console.log("streamDestroyed", e);
      });

      session.on('streamCreated', (e) => {
        console.log("streamCreated", e);
      });

      session.on('sessionDisconnected', (event) => {
        console.warn("sessionDisconnected event");
        this.showForm = true;
        this.webComponent.style.display = 'none';  // This line still directly manipulates the DOM
      });

      session.on('exception', (exception) => {
        console.error(exception);
      });
    });
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
      console.log("dasda");
      this.showForm = false;
      this.webComponent.style.display = 'block';
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
