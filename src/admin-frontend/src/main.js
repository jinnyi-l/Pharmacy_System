import Vue from 'vue'
import App from './App.vue'

// router 路由跳转
import router from './router'

// vueX 状态共享
import store from './store'

// axios 发送请求
import axios from 'axios'
import './network/axios'
Vue.prototype.$axios = axios

// element-ui 全局
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

// 重写message方法，限制个数
import { message } from '@/utils/resetMessage';
Vue.prototype.$message = message;
import "@/assets/less/message.less"

import 'default-passive-events'



Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
