export default function ({ $axios, store, redirect }) {
  let isRefreshing = false; // 토큰 갱신 중 여부를 나타내는 변수
  let subscribers = []; // 대기 중인 요청을 저장하는 배열

  function onAccessTokenFetched(newToken) {
    // 대기 중인 요청들에게 새로운 토큰을 적용
    subscribers.forEach((callback) => callback(newToken));
    subscribers = [];
  }

  $axios.onError((error) => {
    const code = parseInt(error.response && error.response.status);

    if (code === 401) {
      const refresh_token = sessionStorage.getItem('refresh_token');
      if (refresh_token) {
        if (!isRefreshing) {
          isRefreshing = true;
          return $axios
            .post('/user-service/regenerateToken', { refresh_token })
            .then((response) => {
              if (response.data.access_token) {
                store.commit('setAccessToken', response.data.access_token);
                sessionStorage.setItem('refresh_token', response.data.refresh_token);
                onAccessTokenFetched(response.data.access_token);
              } else {
                redirect('/login');
              }
            })
            .catch(() => {
              redirect('/login');
            })
            .finally(() => {
              isRefreshing = false;
            });
        } else {
          // 토큰 재발급 중인 경우 대기열에 추가
          return new Promise((resolve) => {
            subscribers.push((newToken) => {
              error.config.headers['Authorization'] = `Bearer ${newToken}`;
              resolve($axios(error.config));
            });
          });
        }
      } else {
        redirect('/login');
      }
    } else if (code === 500) {
      redirect('/login');
    }

    return Promise.reject(error);
  });
}