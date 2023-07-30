import {createStore} from "vuex"

export default createStore({
  state: {
    isLogin: true,
  },
  mutations:{
    changeLoginState(state){
        return (state.isLogin = !state.isLogin)
    }
  },

})