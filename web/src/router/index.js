import { createRouter, createWebHistory } from 'vue-router'
// import store from '../store/index'
// import CalView from '../views/index/CalView.vue' 

const routes = [
  {
    path:"/calendar",
    name: "Calendar",
    component: () => import('../views/Calendar.vue')
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
