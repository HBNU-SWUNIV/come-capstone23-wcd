// store/index.js

import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    access_token: null,
  },
  mutations: {
    setAccessToken(state, access_token) {
      state.access_token = access_token;
    },
  },
  actions: {
    // 필요한 경우 다른 액션 추가
  },
  modules: {
    // 필요한 경우 모듈 추가
  },
});

export default function () {
  return store;
}
