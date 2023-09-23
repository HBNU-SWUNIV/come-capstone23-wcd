export default function ({ $axios, store, redirect }) {
    $axios.onError((error) => {
        const code = parseInt(error.response && error.response.status);
        if (code === 401) {
            // 401 오류가 발생하면 세션 스토리지에서 refresh_token을 가져옴
            const refresh_token = sessionStorage.getItem('refresh_token');
            if (refresh_token) {
                // refresh_token이 있으면 백엔드의 refresh_token 엔드포인트를 호출하여 새로운 access_token을 가져옴
                // API 요청을 재시도하기 전에 access_token을 업데이트
                return $axios
                    .post('/user-service/regenerateToken', { refresh_token: refresh_token })
                    .then((response) => {
                        if (response.data.access_token) {
                            // 성공적으로 access_token을 업데이트하면 Vuex에 저장
                            store.commit('setAccessToken', response.data.access_token);
                            // 다시 원래의 요청을 시도 (error.config는 이전 요청 정보를 포함)
                            return $axios.request(error.config);
                        } else {
                            // access_token을 얻을 수 없으면 로그인 페이지로 리다이렉트
                            redirect('/login');
                        }
                    })
                    .catch(() => {
                        // refresh_token을 사용하여 access_token을 업데이트하지 못하면 로그인 페이지로 리다이렉트
                        redirect('/login');
                    });
            } else {
                // refresh_token이 없으면 로그인 페이지로 리다이렉트
                redirect('/login');
            }
        }
        return Promise.reject(error);
    });
}