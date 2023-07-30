import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../components/HomePage'
import LoginPage from '../login_pages/LoginPage'
import SignupPage from '../login_pages/SignupPage'
import CreateClubPage from '../pages/CreateClubPage'
import SearchClubPage from '../pages/SearchClubPage'
import ClubHomePage from '../club_pages/ClubHomePage'
import PageHome from '../club_pages/PageHome'
import NoticePage from '../club_pages/NoticePage'
import SchedulePage from '../club_pages/SchedulePage'
import BoardPage from '../club_pages/BoardPage'
import CamPage from '../club_pages/CamPage'
import VirtualPage from '../club_pages/VirtualPage'
import CreateNoticePage from '../pages/CreateNoticePage'
import CreateBoardPage from '../pages/CreateBoardPage'

const routes = [
    {
        path: '/',
        name: 'HomePage',
        component: HomePage,
    },
    {
        path: '/login',
        name: 'LoginPage',
        component: LoginPage,
    },
    {
        path: '/signup',
        name: 'SignupPage',
        component: SignupPage,
    },
    {
        path: '/club-create',
        name: 'CreateClubPage',
        component: CreateClubPage,
    },
    {
        path: '/club-search',
        name: 'SearchClubPage',
        component: SearchClubPage,
    },
    {
        path: '/:id',
        name: 'ClubHomePage',
        component: ClubHomePage,
        children: [
            {
                path: '/:id/home',
                name: 'PageHome',
                component: PageHome,
            },
            {
                path: '/:id/notice',
                name: 'NoticePage',
                component: NoticePage,
            },
            {
                path: '/:id/schedule',
                name: 'SchedulePage',
                component: SchedulePage,
            },
            {
                path: '/:id/board',
                name: 'BoardPage',
                component: BoardPage,
            },
            {
                path: '/:id/cam',
                name: 'CamPage',
                component: CamPage,
            },
            {
                path: '/:id/virtual',
                name: 'VirtualPage',
                component: VirtualPage,
            },
            {
                path: '/:id/notice-create',
                name: 'CreateNoticePage',
                component: CreateNoticePage,
            },
            {
                path: '/:id/board-create',
                name: 'CreateBoardPage',
                component: CreateBoardPage,
            },
        ]
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router