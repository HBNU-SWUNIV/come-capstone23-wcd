<!-- 로그인 되어있을때 채팅 네비게이션 띄움
  로그인 되어있지 않을때 채팅 네비게이션 없음 -->
  <template>
  <div class="ChatBar">
    <div v-if="$store.state.isLogin">
      <div class="EmptyArea"></div>
      <div class="ChatRoomNav">
        <button class="BackBtn">
          <img
            class="ChatIcon"
            src="https://cdn-icons-png.flaticon.com/512/552/552444.png"
          />
        </button>
        <button class="BackBtn">
          <img
            class="ChatIcon"
            src="https://cdn-icons-png.flaticon.com/512/4340/4340547.png"
          />
        </button>
        <button class="BackBtn">
          <img
            class="ChatIcon"
            src="https://cdn-icons-png.flaticon.com/512/3293/3293476.png"
          />
        </button>
      </div>
      <div class="ChatRoomScreen">
        <div class="ChatRoomWrap">
          <div class="d-flex flex-row justify-content-center" style="width:110px;">
            <img
              class="ChatRoomProfile"
              src="https://cdn-icons-png.flaticon.com/512/1256/1256650.png"
            />
          </div>
          <div class="d-flex flex-column">
            <div class="d-flex flex-row">
              <label style="display: flex; flex-direction: column; justify-content: center; font-size: 20px; margin:0px;">test1</label>
              <label class="ml-3" style="display: flex; flex-direction: column; justify-content: center; font-size: 15px; margin:0px; color:rgb(161, 158, 158);">5</label>
            </div>
            <label style="margin:0px;">test1</label>
          </div>
        </div>
      </div>
      <div class="ChatCreateScreen">
        <button class="button" style="background: #272727; border: none">
          <img
            class="addIcon"
            src="https://cdn-icons-png.flaticon.com/512/10977/10977488.png"
          />
        </button>
      </div>
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
  height: 850px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.ChatCreateScreen {
  height: 100px;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
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
.addIcon {
  height: 50px;
  width: 50px;
}
.ChatRoomProfile {
  height: 60px;
  width: 60px;
}
.ChatRoomWrap {
  height: 60px;
  width:300px;
  display: flex;
  flex-direction: row;
}
</style>