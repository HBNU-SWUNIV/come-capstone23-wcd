<template>
  <div class="modal-overlay">
    <div class="modal">
        <v-row>
              <v-col cols="4">
                <!-- 년도 선택 목록 -->
                <v-select
                  v-model="selectedYear"
                  :items="years"
                  label="년도"
                  @input="validateYear"
                ></v-select>
              </v-col>
              <v-col cols="4">
                <!-- 월 선택 목록 -->
                <v-select
                  v-model="selectedMonth"
                  :items="months"
                  label="월"
                  @change="updateDays"
                  @input="validateMonth"
                ></v-select>
              </v-col>
              <v-col cols="4">
                <!-- 일 선택 목록 -->
                <v-select
                  v-model="selectedDay"
                  :items="days"
                  label="일"
                  @input="validateDay"
                ></v-select>
              </v-col>
            </v-row>
      <button>Go Home</button>
    </div>
    <div class="close" @click="$emit('close-modal')">
        <v-icon>mdi-close</v-icon>
    </div>
  </div>
</template>
  
  <script>
export default {};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  background-color: #000000da;
  color:rgb(0, 0, 0);
  z-index: 9999;
}

.modal {
  text-align: center;
  background-color: rgb(41, 41, 41);
  height: 500px;
  width: 500px;
  margin-top: 10%;
  padding: 60px;
  border-radius: 20px;
}
.close {
  margin: 10% 0 0 16px;
  cursor: pointer;
}

h6 {
  font-weight: 500;
  font-size: 28px;
  margin: 20px 0;
}

p {
  font-size: 16px;
  margin: 20px 0;
}

button {
  background-color: #ac003e;
  width: 150px;
  height: 40px;
  color: white;
  font-size: 14px;
  border-radius: 16px;
  margin-top: 50px;
}
</style>