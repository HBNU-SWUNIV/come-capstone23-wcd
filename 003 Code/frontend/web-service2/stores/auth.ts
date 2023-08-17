import { defineStore } from 'pinia'

export const useAuthStore = defineStore('authStore', {
    state: () => ({
        isLogin: false,
        userId: null,
        accessToken: null,
        refreshToken: null
    }),
    actions: {
        setIsLogin(value : boolean) {
            this.isLogin = value;
        }
        // You can add more actions here...
    }
});
