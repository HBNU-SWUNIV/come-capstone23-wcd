export default {
    getUserId: state => state.loginId,
    getErrorState: state => state.errorState,
    getIsAuth: state => state.isAuth,
    loggedIn(state) {
      return !!state.user
    }
  }