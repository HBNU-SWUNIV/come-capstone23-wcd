<template style="display: flex;
            justify-content: center;height: 100%; width: 100%;">
  <div id="main" style="text-align: center; width: 100%; height: 100%">
    <openvidu-webcomponent
      ref="webComponent"
      toolbar-captions-button="false"
      toolbar-background-effects-button="false"
      toolbar-recording-button="false"
      toolbar-broadcasting-button="false"
      toolbar-activities-panel-button="false"
      toolbar-display-logo="false"
      toolbar-display-session-name="false"
      stream-resolution="'320x240'"
      style="height: 100%; width: 100%; display: block!important;">
    </openvidu-webcomponent>
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
      sessionName: this.$route.params.clubId,
      participantName: sessionStorage.getItem('user_id'),
      APPLICATION_SERVER_URL: 'https://wcd.kro.kr/api/openvidu/',
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
        this.joinSession();
      });

      session.on('exception', (exception) => {
        console.error(exception);
      });
    });

    this.getUserName();
    this.joinSession();
  },
  methods: {
    async getUserName() {
      const access_token = this.$store.state.access_token;
      const config = {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${access_token}`,
        },
      };

      const userId = sessionStorage.getItem("user_id");

      await this.$axios
        .get(`/user-service/user/${userId}/userName`, config)
        .then((res) => {
          this.webComponent.participantName= res.data;
        });
    },
    async joinSession() {
      const tokens = await Promise.all([
        this.getToken(this.sessionName),
        this.getToken(this.sessionName)
      ]);
      this.webComponent.tokens = {
        webcam: tokens[0],
        screen: tokens[1]
      };
    },
    async getToken(mySessionId) {
      const sessionId = await this.createSession(mySessionId);
      return this.createToken(sessionId);
    },
    createSession(sessionId) {
      const access_token = this.$store.state.access_token;
      const config = {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${access_token}`,
        },
      };

      return new Promise((resolve, reject) => {
        $.ajax({
          type: "POST",
          url: this.APPLICATION_SERVER_URL + "sessions",
          config: config,
          data: JSON.stringify({ customSessionId: sessionId }),
          headers: { "Content-Type": "application/json" },
          success: response => resolve(response),
          error: error => reject(error)
        });
      });
    },
    createToken(sessionId) {
      const access_token = this.$store.state.access_token;
      const config = {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${access_token}`,
        },
      };

      return new Promise((resolve, reject) => {
        $.ajax({
          type: 'POST',
          url: this.APPLICATION_SERVER_URL + 'sessions/' + sessionId + '/connections',
          config: config,
          data: JSON.stringify({}),
          headers: { "Content-Type": "application/json" },
          success: response => resolve(response),
          error: error => reject(error)
        });
      });
    },
  },
  beforeDestroy() {
    this.webComponent.disconnect();
    this.webComponent.tokens = null;
  }
}
</script>
