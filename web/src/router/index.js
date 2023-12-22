import { createRouter, createWebHistory } from 'vue-router'
// import store from '../store/index'

const routes = [
  {
    path:"/",
    name:"home",
    redirect:"/calendar/",
    meta: {
      requestAuth: true,
    }
  },
  {
    path:"/calendar/",
    name: "calendar_index",
    component: () => import('../views/index/CalView'),
    meta: {
      requestAuth: true,
    }
  },
  {
    path:"/pk/",
    name: "Calendar",
    component: () => import('../views/Calendar.vue'),
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/login/",
    name: "user_account_login",
    component: () => import('@/views/user/UserAccountLoginView'),
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/user/register/",
    name: "user_account_register",
    component: () => import('@/views/user/UserAccountRegisterView'),
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/404/",
    name: "404",
    component: () => import('@/views/error/NotFound.vue'),
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404/"
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
