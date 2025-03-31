import Vue from 'vue'
import VueRouter from 'vue-router'

import routes from './routes'

Vue.use(VueRouter)


const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,

  scrollBehavior(to, from, savedPosition) {
    // return 期望滚动到哪个的位置
    return { x: 0, y: 0 }
  }
})

router.beforeEach((to, form, next) => {
  document.title = "药店后台- " + to.meta.title;
  next()
})

// 没有的页面就跳转到404
router.beforeEach((to, from, next) => {
  if (to.matched.length === 0) {
    next({ path: "/404" })
  } else {
    next()
  }
})
export default router
