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
    isLogin: false, // 기본적으로 로그인 상태를 false로 초기화
    selectedMeetingId: null,
  },
  mutations: {
    SET_LOGIN_STATE(state, isLoggedIn) {
      state.isLogin = isLoggedIn;
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
      // Local Storage에서 액세스 토큰을 가져와서 로그인 상태를 확인
      const accessToken = localStorage.getItem('access_token');
      const isLoggedIn = accessToken !== null;
      commit('SET_LOGIN_STATE', isLoggedIn);
    },
  },
});

export default store;


// import { createStore } from 'vuex';
//
// const store = createStore({
//   state: {
//     // localStorage에서 로그인 상태를 가져와 초기 상태로 설정
//     isLogin: localStorage.getItem('isLogin') === 'true',
//     selectedMeetingId: null,
//   },
//   mutations: {
//     SET_LOGIN_STATE(state, isLoggedIn) {
//       state.isLogin = isLoggedIn;
//       // 로그인 상태를 localStorage에 저장
//       localStorage.setItem('isLogin', isLoggedIn.toString());
//     },
//     setSelectedMeetingId(state, meetingId) {
//       state.selectedMeetingId = meetingId;
//     },
//   },
//   actions: {
//     login({ commit }) {
//       // 로그인 로직을 수행한 후
//       commit('SET_LOGIN_STATE', true);
//     },
//     logout({ commit }) {
//       // 로그아웃 로직을 수행한 후
//       commit('SET_LOGIN_STATE', false);
//     },
//     checkLoginState({ commit }) {
//       // Local Storage에서 로그인 상태를 가져와서 Vuex 스토어에 저장
//       const isLoggedIn = localStorage.getItem('isLogin') === 'true';
//       commit('SET_LOGIN_STATE', isLoggedIn);
//     },
//   },
// });
//
// export default store;

