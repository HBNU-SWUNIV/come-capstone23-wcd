<template>
  <div>
    <div class="MyCamArea">
      <video class="My-Cam-Screen" ref="localVideo" autoplay></video>
    </div>
    <div class="CamWrap">
      <div class="CamArea">
        <div class="Cam-Screen"></div>
        <div class="Cam-Screen"></div>
        <div class="Cam-Screen"></div>
        <div class="Cam-Screen"></div>
      </div>
      <div class="CamArea">
        <div class="Cam-Screen"></div>
        <div class="Cam-Screen"></div>
        <div class="Cam-Screen"></div>
        <div class="Cam-Screen"></div>
      </div>
    </div>
  </div>
</template>

<script>
import SimplePeer from "simple-peer";

export default {
  mounted() {
    // getUserMedia로 카메라 스트림 가져오기
    navigator.mediaDevices
      .getUserMedia({ video: true, audio: true })
      .then((stream) => {
        // 비디오 요소에 스트림 연결
        this.$refs.localVideo.srcObject = stream;

        // WebRTC를 위한 SimplePeer 객체 생성
        const peer = new SimplePeer({ initiator: true, stream });

        // 원격 피어와 연결 성공 시
        peer.on("signal", (data) => {
          console.log("SIGNAL", JSON.stringify(data));

          // 상대방과 연결을 위한 시그널 데이터를 전송하는 로직 구현
        });

        // 원격 피어로부터 스트림을 받아서 비디오 요소에 표시
        peer.on("stream", (remoteStream) => {
          this.$refs.remoteVideo.srcObject = remoteStream;
        });
      })
      .catch((error) => {
        console.error("Error accessing media devices:", error);
      });
  },
};
</script>

<style>
.MyCamArea {
  height: 350px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}
.My-Cam-Screen {
  height: 300px;
}
.CamArea {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
.CamWrap {
  height: 600px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.Cam-Screen {
  background-color: white;
  height: 240px;
  width: 320px;
}
</style>