<template>
    <v-app class="signup-page">
      <h1 style="padding:20px;">WCD CAPSTONE</h1>
      <div class="centered-content">
        <Nuxt />
      </div>
    </v-app>
  </template>
  
  <style scoped>
  .signup-page{
      background-color: #a46843;
  }
  .centered-content {
    display: flex;
    justify-content: center; /* 수평 가운데 정렬 */
    align-items: center; /* 수직 가운데 정렬 */
    height: 100vh; /* 화면 높이만큼 요소의 높이를 설정 (전체 화면) */
  }
  </style>