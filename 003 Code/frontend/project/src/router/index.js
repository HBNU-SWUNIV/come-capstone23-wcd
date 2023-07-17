import { createRouter, createWebHistory } from 'vue-router'
// 네비게이션
import PageHome from '../main_pages/PageHome'
import PageNotice from '../main_pages/PageNotice'
import PageSchedule from '../main_pages/PageSchedule'
import PageBoard from '../main_pages/PageBoard'
import PageCam from '../main_pages/PageCam'
import PageLogin from '../main_pages/PageLogin'
import PageSignup from '../main_pages/PageSignup'
// 게시판
import BoardWrite from '../board_pages/BoardWrite.vue'
import BoardDetail from '../board_pages/BoardDetail.vue'
// 모임
import ClubCreate from '../club_pages/ClubCreate'
import ClubSearch from '../club_pages/ClubSearch'

const routes = [
    // 네비게이션
    {
        path: '/',
        name: 'PageHome',
        component: PageHome,
    },
    {
        path: '/notice',
        name: 'PageNotice',
        component: PageNotice,
    },
    {
        path: '/schedule',
        name: 'PageSchedule',
        component: PageSchedule,
    },
    {
        path: '/board',
        name: 'PageBoard',
        component: PageBoard,
    },
    {
        path: '/cam',
        name: 'PageCam',
        component: PageCam,
    },
    {
        path: '/login',
        name: 'PageLogin',
        component: PageLogin,
    },
    {
        path: '/signup',
        name: 'PageSignup',
        component: PageSignup,
    },
    // 게시판
    {
        path: '/board/write',
        name: 'BoardWrite',
        component: BoardWrite,
    },
    {
        path: '/board/detail',
        name: 'BoardDetail',
        component: BoardDetail
    },
    // 모임
    {
        path: '/clubCreate',
        name: 'ClubCreate',
        component: ClubCreate
    },
    {
        path: '/clubSearch',
        name: 'ClubSearch',
        component: ClubSearch
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
  })

export default router