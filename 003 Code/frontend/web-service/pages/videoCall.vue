<template>
  <div>
    <!-- Form to connect to a video-session -->
    <div id="main" ref="main" style="text-align: center;">
      <h1>Join a video session</h1>
      <form @submit.prevent="joinSession" style="padding: 80px; margin: auto">
        <p>
          <label>Session:</label>
          <input type="text" v-model="sessionName" required>
        </p>
        <p>
          <label>User:</label>
          <input type="text" v-model="user">
        </p>
        <p>
          <input type="submit" value="JOIN">
        </p>
      </form>
    </div>

    <!-- OpenVidu Web Component -->
    <openvidu-webcomponent ref="openviduWebcomponent" style="height: 100%;"></openvidu-webcomponent>
  </div>
</template>

<script>
export default {
  head: {
    link: [
      { rel: 'stylesheet', href: '/openvidu-webcomponent-2.28.0.css' }
    ]
  },
  data() {
    return {
      sessionName: 'SessionA',
      user: 'User1',
      webComponent: null,
      APPLICATION_SERVER_URL: "https://wcd.kro.kr/api/openvidu/"
    };
  },
  mounted() {
    this.webComponent = this.$refs.openviduWebcomponent;

    // Register your event listeners here
    this.webComponent.addEventListener('onSessionCreated', this.handleSessionCreated);
    // ... (other event listeners) ...
  },
  methods: {
    async joinSession() {
      const sessionName = this.sessionName;
      const participantName = this.user;

      const promiseResults = await Promise.all([this.getToken(sessionName), this.getToken(sessionName)]);
      const tokens = { webcam: promiseResults[0], screen: promiseResults[1] };

      this.hideForm();

      // Displaying webcomponent
      this.webComponent.style.display = 'block';

      // Setup other properties on webComponent as needed...
      // ...

      this.webComponent.tokens = tokens;
    },
    handleSessionCreated(event) {
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
        this.hideForm();
        this.webComponent.style.display = 'none';
      });

      session.on('exception', (exception) => {
        console.error(exception);
      });

      // ... (other session events) ...
    },
    hideForm() {
      this.$refs.main.style.display = 'none';
    },
    async getToken(mySessionId) {
      try {
        const sessionId = await this.createSession(mySessionId);
        return await this.createToken(sessionId);
      } catch (error) {
        console.error("Error getting token:", error);
        throw error;
      }
    },
    async createSession(sessionId) {
      try {
        const response = await this.$axios.$post(this.APPLICATION_SERVER_URL + "sessions", {
          customSessionId: sessionId
        });
        return response.id; // The sessionId
      } catch (error) {
        console.error("Error creating session:", error);
        throw error;
      }
    },
    async createToken(sessionId) {
      try {
        const response = await this.$axios.$post(this.APPLICATION_SERVER_URL + 'sessions/' + sessionId + '/connections', {});
        return response.token; // The token
      } catch (error) {
        console.error("Error creating token:", error);
        throw error;
      }
    }
  }
}
</script>

<style scoped>
/* If you have component-specific styles, place them here */
</style>
