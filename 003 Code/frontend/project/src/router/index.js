import { createRouter, createWebHistory } from 'vue-router';
import PageHome from '../main_pages/PageHome';
import PageNotice from '../main_pages/PageNotice';
import PageSchedule from '../main_pages/PageSchedule';
import PageBoard from '../main_pages/PageBoard';
import PageCam from '../main_pages/PageCam';
import PageLogin from '../main_pages/PageLogin';
import PageSignup from '../main_pages/PageSignup';

const router = createRouter({
    history: createWebHistory(),
    routes: [
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
    ]
});

export default router;