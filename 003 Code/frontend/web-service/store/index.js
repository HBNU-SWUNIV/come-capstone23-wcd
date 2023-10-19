// store/index.js

export const state = () => ({
  access_token: null,
  push_token: null,
});

export const mutations = {
  setAccessToken(state, access_token) {
    state.access_token = access_token;
  },
  setPushToken(state, push_token){
    state.push_token = push_token
  }
};

export const actions = {
  // 필요한 경우 다른 액션 추가
};

export const getters = {
  accessToken: state => state.access_token,
  pushToken: state => state.push_token,
};