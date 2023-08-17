import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../pages/HomePage'
import LoginPage from '../pages/LoginPage'
import SignupPage from '../pages/SignupPage'
import ClubCreatePage from '../pages/ClubCreatePage'
import ClubSearchPage from '../pages/ClubSearchPage'
import ClubHomePage from '../pages/clubs/ClubHomePage'
import ClubNoticePage from '../pages/clubs/ClubNoticePage'
import ClubSchedulePage from '../pages/clubs/ClubSchedulePage'
import ClubBoardPage from '../pages/clubs/ClubBoardPage'
import ClubVideoCallPage from '../pages/clubs/ClubVideoCallPage'
import ClubVirtualPage from '../pages/clubs/ClubVirtualPage'
import ClubChatPage from '../pages/clubs/ClubChatPage'
import CreateNoticePage from '../pages/CreateNoticePage'
import CreateBoardPage from '../pages/CreateBoardPage'
import CreateSchedulePage from '../pages/CreateSchedulePage'
import NoticeDetail from '../pages/NoticeDetail'
import BoardDetail from '../pages/BoardDetail'

import store from '@/vuex/store'

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
        name: 'ClubCreatePage',
        component: ClubCreatePage,
    },
    {
        path: '/club-search',
        name: 'ClubSearchPage',
        component: ClubSearchPage,
    },
    {
        path: '/:id',
        name: 'ClubHomePage',
        component: ClubHomePage,
    },
    {
        path: '/:id/notice',
        name: 'ClubNoticePage',
        component: ClubNoticePage,
    },
    {
        path: '/:id/schedule',
        name: 'ClubSchedulePage',
        component: ClubSchedulePage,
    },
    {
        path: '/:id/board',
        name: 'ClubBoardPage',
        component: ClubBoardPage,
    },
    {
        path: '/:id/video-call',
        name: 'ClubVideoCallPage',
        component: ClubVideoCallPage,
    },
    {
        path: '/:id/virtual',
        name: 'ClubVirtualPage',
        component: ClubVirtualPage,
    },
    {
        path: '/:id/chat',
        name: 'ClubChatPage',
        component: ClubChatPage,
    },
    {
        path: '/:id/notice-create',
        name: 'CreateNoticePage',
        component: CreateNoticePage,
    },
    {
        path: '/clubs/:clubId/board-create',
        name: 'CreateBoardPage',
        component: CreateBoardPage,
    },
    {
        path: '/:id/schedule-create/:year/:month/:day',
        name: 'CreateSchedulePage',
        component: CreateSchedulePage,
    },
    {
        path: '/:id/notice-detail/:num',
        name: 'NoticeDetail',
        component: NoticeDetail,
    },
    {
        path: '/clubs/:clubId/board-detail/:boardId',
        name: 'BoardDetail',
        component: BoardDetail,
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

router.beforeEach((to, from, next) => {
    // 선택한 모임 ID를 가져옴
    const selectedMeetingId = to.params.id;
  
    // 선택한 모임 ID가 저장되어 있으면 해당 모임 홈페이지로 이동하기 전에 새로고침
    if (selectedMeetingId && selectedMeetingId === store.state.selectedMeetingId) {
      const currentPath = to.path;
      // 라우터가 현재와 같은 경로로 이동하면 새로고침
      if (to.path === currentPath) {
        next({ path: '/empty' }); // 임의의 경로로 이동한 뒤
        next({ path: currentPath }); // 다시 원래 경로로 이동하여 새로고침
      } else {
        next(); // 이동하지 않음
      }
    } else {
      next(); // 이동하지 않음
    }
  });
  
export default router