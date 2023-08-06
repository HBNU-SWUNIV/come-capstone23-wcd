import { createApp } from 'vue'
import App from './App.vue'
import store from './vuex/store'
import router from './router/index'
import axios from 'axios';
import './/styles/global.css';

const app = createApp(App);
app.use(store);
app.use(router)

axios.defaults.baseURL = 'http://localhost:8000';

axios.interceptors.request.use(
    config => {
        const token = localStorage.getItem('access_token');
        if (token !== null && token !== undefined && token !== '') {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => Promise.reject(error)
);

axios.interceptors.response.use(
    function (response) {
        // 요청이 성공적으로 완료되면, 그대로 응답을 반환합니다.
        return response;
    },
    function (error) {
        if (error.response.status === 401) {
            // 401 에러가 발생하면, 로그인 페이지로 이동합니다.
            router.push({ name: 'LoginPage' });
        }

        // 그 외의 에러는 그대로 반환합니다.
        return Promise.reject(error);
    }
);

app.mount('#app');

store.dispatch('checkLoginState');