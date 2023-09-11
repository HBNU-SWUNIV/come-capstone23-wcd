<template>
  <v-app class="login-page">
    <h1 style="padding: 20px">WCD CAPSTONE</h1>
    <div class="centered-content">
      <Nuxt />
    </div>
  </v-app>
</template>

<style scoped>
.login-page {
  /* background-color: #a46843; */
  background-image: url("https://cdn.pixabay.com/photo/2023/06/14/02/21/stairs-8062145_640.jpg");
}
.centered-content {
  display: flex;
  justify-content: center; /* 수평 가운데 정렬 */
  align-items: center; /* 수직 가운데 정렬 */
}
</style>