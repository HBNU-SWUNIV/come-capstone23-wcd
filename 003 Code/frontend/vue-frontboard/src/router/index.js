import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'

const routes = [
  {
    path: '/',
    name: 'PageHome',
    component: PageHome
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/PageLogin.vue')
  },
  {
    path: '/signup',
    name: 'signup',
    component: () => import('../views/PageSignup.vue')
  },
  {
    path: '/notice',
    name: 'notice',
    component: () => import('../views/PageNotice.vue')
  },{
    path: '/schedule',
    name: 'schedule',
    component: () => import('../views/PageSchedule.vue')
  },
  {
    path: '/board',
    name: 'board',
    component: () => import('../views/PageBoard.vue')
  },
  {
    path: '/cam',
    name: 'cam',
    component: () => import('../views/PageCam.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router