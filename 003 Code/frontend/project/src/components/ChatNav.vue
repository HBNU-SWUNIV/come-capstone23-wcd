<template>
  <div class="ChatBar">
    <div v-if="$store.state.isLogin">
      <div class="EmptyArea"></div>
      <div class="ChatRoomNav">
        <button class="BackBtn">
          <img class="ChatIcon" src="../assets/whitechat.png" />
        </button>
        <button class="BackBtn">
          <img class="ChatIcon" src="../assets/whitevoice.png" />
        </button>
        <button class="BackBtn">
          <img class="ChatIcon" src="../assets/whitecam.png" />
        </button>
      </div>
      <div class="ChatRoomScreen">chatroom</div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
.ChatBar {
  height: 100%;
  width: 300px;
}
.EmptyArea {
  height: 80px;
  width: 100%;
}
.ChatRoomNav {
  display: flex;
  justify-content: space-around;
}
.ChatRoomScreen {
  height: 950px;
  width: 100%;
}
.BackBtn {
  width: 80px;
  height: 50px;
  border-radius: 15px;
  background: #272727;
  border: none;
}
.ChatIcon {
  height: 30px;
  width: 30px;
}
</style>