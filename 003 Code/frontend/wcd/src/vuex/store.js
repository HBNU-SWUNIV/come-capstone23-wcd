// import {createStore} from "vuex"

// export default createStore({
//   state: {
//     isLogin: false,
//   },
//   mutations:{
//     changeLoginState(state){
//         return (state.isLogin = !state.isLogin)
//     }
//   },

// })
// store/index.js

// store/index.js

import { createStore } from 'vuex';

const store = createStore({
  state: {
    isLogin: localStorage.getItem('isLogin') === 'true' || false,
    selectedMeetingId: null,
  },
  mutations: {
    SET_LOGIN_STATE(state, isLoggedIn) {
      state.isLogin = isLoggedIn;
      localStorage.setItem('isLogin', isLoggedIn.toString());
    },
    setSelectedMeetingId(state, meetingId) {
      state.selectedMeetingId = meetingId;
    },
  },
  actions: {
    login({ commit }) {
      // 로그인 로직을 수행한 후
      commit('SET_LOGIN_STATE', true);
    },
    logout({ commit }) {
      // 로그아웃 로직을 수행한 후
      commit('SET_LOGIN_STATE', false);
    },
    checkLoginState({ commit }) {
      // Local Storage에서 로그인 상태를 가져와서 Vuex 스토어에 저장
      const isLoggedIn = localStorage.getItem('isLogin') === 'true';
      commit('SET_LOGIN_STATE', isLoggedIn);
    },
  },
});

export default store;

