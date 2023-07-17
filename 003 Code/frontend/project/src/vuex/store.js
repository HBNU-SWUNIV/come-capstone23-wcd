import { createStore } from "vuex"
import getters from "./getters"
import mutation from "./mutation"
import actions from "./actions"

export default createStore({
    state: {
        user: null,
        isLogin: false,
    },
    mutation,
    getters,
    actions,
})