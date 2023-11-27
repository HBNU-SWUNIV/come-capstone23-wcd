<template>
  <v-app class="login-page">
    <h1 style="padding: 20px">WCD CAPSTONE</h1>
    <div class="centered-content" style="height:100%;">
      <Nuxt />
    </div>
  </v-app>
</template>

<style scoped>
.login-page {
  background-blend-mode: overlay; /* 필요한 경우 배경 이미지와 조합 */
}
.centered-content {
  display: flex;
  justify-content: center; /* 수평 가운데 정렬 */
  align-items: center;
}
</style>
