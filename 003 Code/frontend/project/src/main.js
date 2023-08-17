import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './vuex/store'

createApp(App)
.use(router)
.use(store)
.mount('#app')