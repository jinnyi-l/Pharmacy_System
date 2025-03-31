import Vue from 'vue'
import Vuex from 'vuex'

import mutations from './mutations.js'
import getters from './getters.js'
import actions from './actions.js'
import modules from './modules.js'

Vue.use(Vuex)

const state = {
  token: "",
  userInfo: JSON.parse(sessionStorage.getItem("userInfo")),
  focusMode: false,
  // 图片的前缀url
  imgBaseUrl: "http://localhost:9000/",
  frontPageBaseUrl: "http://localhost:7028/",
  //默认打开的菜单
  // defaultOpens: [sessionStorage.getItem("defaultOpens")]
}

export default new Vuex.Store({
  state,
  mutations,
  getters,
  actions,
  modules
})
