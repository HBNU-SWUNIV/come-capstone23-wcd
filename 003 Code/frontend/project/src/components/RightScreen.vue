<template>
  <div id="right_screen">
    <div id="empty_area"></div>

    <div class="d-flex flex-row justify-content-around" id="nav_area">
      <button id="side">
        <img id="chat_room" src="../assets/icon/whitechat.png" alt="chat_room" />
      </button>
      <button id="side">
        <img id="voice_room" src="../assets/icon/whiteperson.png" alt="voice_room" />
      </button>
      <button id="side">
        <img id="cam_room" src="../assets/icon/whitecam.png" alt="cam_room" />
      </button>
    </div>

    <div id="room_area"></div>
  </div>
</template>

<script>
</script>

<style scoped>
#right_screen {
  position: absolute;
  width: 300px;
  height: 1080px;
  left: 1620px;
  top: 0px;
  background-color: #383838;
}
#empty_area {
  width: 300px;
  height: 80px;
}
#nav_area {
  width: 300px;
  height: 50px;
}
#side {
  width: 80px;
  height: 50px;
  background-color: #232324;
  border: none;
  border-radius: 15%;
}
#chat_room {
  max-width: 30px;
  max-height: 30px;
  border-radius: 20%;
}
#voice_room {
  max-width: 30px;
  max-height: 30px;
  border-radius: 20%;
}
#cam_room {
  max-width: 30px;
  max-height: 30px;
  border-radius: 20%;
}
</style>