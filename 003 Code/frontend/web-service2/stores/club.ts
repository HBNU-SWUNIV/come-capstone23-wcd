import { defineStore } from 'pinia'

export const useClubStore = defineStore('clubStore', {
    state: () => ({
        clubs: []
    }),
    actions: {
        // You can add more actions here...
    },
});