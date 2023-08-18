import { defineStore } from 'pinia';
import { persistedState } from "#imports";

export const useAuthStore = defineStore('authStore', {
    state: () => ({
        userId: null as BigInt | null,
        accessToken: null as string | null,
        refreshToken: null as string | null,
    }),
    getters: {
        getUserId(): BigInt | null {
            return this.userId;
        },
        getAccessToken(): string | null {
            return this.accessToken;
        },
        getRefreshToken(): string | null {
            return this.refreshToken;
        }
    },
    actions: {
        setUserId(id: BigInt | null) {
            this.userId = id;
        },
        setAccessToken(token: string | null) {
            this.accessToken = token;
        },
        setRefreshToken(token: string | null) {
            this.refreshToken = token;
        }
    },
    persist: true,
});
