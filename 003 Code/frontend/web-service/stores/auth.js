// import { defineStore } from 'pinia';
//
// export const useAuthStore = defineStore({
//     id: 'auth',
//
//     state: () => ({
//         isLogin: false, // 기본적으로 로그인 상태를 false로 초기화
//         selectedMeetingId: null,
//     }),
//
//     getters: {
//         isLoggedIn: (state) => state.isLogin,
//     },
//
//     actions: {
//         setLoginState(isLoggedIn) {
//             this.isLogin = isLoggedIn;
//         },
//
//         setSelectedMeetingId(meetingId) {
//             this.selectedMeetingId = meetingId;
//         },
//
//         login() {
//             // 로그인 로직을 수행한 후
//             this.setLoginState(true);
//         },
//
//         logout() {
//             // 로그아웃 로직을 수행한 후
//             this.setLoginState(false);
//         },
//
//         checkLoginState() {
//             // Local Storage에서 액세스 토큰을 가져와서 로그인 상태를 확인
//             const accessToken = localStorage.getItem('access_token');
//             const isLoggedIn = accessToken !== null;
//             this.setLoginState(isLoggedIn);
//         },
//     }
// });

import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
    state: () => {
        return {
            isLogin: false,
        }
    },
    getters: {
        getIsLogin: (state) => state.isLogin
},
actions: {
    setLoginState(isLoggedIn) {
        this.isLogin = isLoggedIn
    }
}
})

if (import.meta.hot) {  //HMR
    import.meta.hot.accept(acceptHMRUpdate(useAuthStore, import.meta.hot))
}
