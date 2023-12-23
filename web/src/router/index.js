import { createRouter, createWebHistory } from 'vue-router'
import store from '../store/index'

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

router.beforeEach((to, from, next) => {

  let flag = 1;
  const jwt_token = localStorage.getItem("jwt_token");
  
  if(jwt_token){
      store.commit("updateToken", jwt_token);
      store.dispatch("getinfo", {
          success() {
              // router.push({ name: "home"});
          },
          error() {
            alert("token无效,请重新登录！");
            router.push({ name: "user_account_login"});
          }
      })
  } else {
    flag = 2;
  }

  if(to.meta.requestAuth && !store.state.user.is_login){
    if (flag === 1) {
      next();
    } else {
      alert("先登录！");
      next({name: "user_account_login"});
    }
  }else{
    next();
  }
})
 
export default router
