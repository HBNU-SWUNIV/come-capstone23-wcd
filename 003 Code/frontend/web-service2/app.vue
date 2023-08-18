<template>
  <v-app id="inspire">
    <router-view></router-view>
  </v-app>
</template>

<script>
import axios from "axios";
import { useAuthStore } from "@/stores/auth";

export default {
  setup() {
    const authStore = useAuthStore();

    // axios의 기본 URL 설정
    const baseURL = "http://localhost:8000";
    axios.defaults.baseURL = baseURL;

    axios.interceptors.request.use(config => {
      if (authStore.getAccessToken) { // Note the .value when accessing ref variables
        config.headers.Authorization = `Bearer ${authStore.getAccessToken}`;
      }
      return config;
    });

    axios.interceptors.response.use(
    response => response,
    async error => {
      const originalRequest = error.config;

      if (error.response.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true;

        try {
          const refreshToken = authStore.getRefreshToken;

          if (!refreshToken) {
            navigateTo('/login');
            return Promise.reject(error);
          }

          const res = await axios.post('/user-service/regenerateToken',
              {
            "refresh_token": refreshToken
          }); // refreshToken을 사용하여 새로운 accessToken을 요청하는 API endpoint

          if (res.status === 200) { // HTTP 상태 코드는 API에 따라 다를 수 있습니다.
            const {access_token, refresh_token} = res.data;
            authStore.setAccessToken(access_token);
            authStore.setRefreshToken(refresh_token);
            originalRequest.headers.Authorization = 'Bearer ' + access_token;

            return axios(originalRequest);  // 원래의 요청을 재시도
          }
        } catch (err) {
          console.error('Token refresh failed:', err);
          navigateTo('/login');
          return Promise.reject(error);
        }
      }

      return Promise.reject(error);
    });
  }
}
</script>