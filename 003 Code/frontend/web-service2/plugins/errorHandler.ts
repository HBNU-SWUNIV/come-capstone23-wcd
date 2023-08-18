import { defineNuxtPlugin } from '#app'

// export default defineNuxtPlugin((nuxtApp) => {
//   nuxtApp.vueApp.config.errorHandler = (error, context) => {
//     console.error("test", error);
//
//
//
//   }
// });

export default defineNuxtPlugin(nuxtApp => {
  nuxtApp.hook('vue:error', (err) => {
    if (err === 401) {
      // 401 에러가 발생했을 때 로그인 페이지로 리디렉션
      navigateTo('/login')
    }
    // 다른 에러는 그대로 전달
    return Promise.reject(err)
  })
})
