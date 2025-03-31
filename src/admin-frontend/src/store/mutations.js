const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
    localStorage.setItem("token", token)
  },
  SET_USERINFO: (state, userInfo) => {
    state.userInfo = userInfo
    sessionStorage.setItem("userInfo", JSON.stringify(userInfo))
    console.log("SET_USERINFO完成")
  },
  REMOVE_INFO: (state) => {
    state.token = ""
    state.userInfo = {}
    localStorage.setItem("token", "")
    sessionStorage.setItem("userInfo", JSON.stringify({}))
  },
  // SET_DEFAULTOPENS: (state, values) => {
  //   state.defaultOpens = [values]
  //   console.log(state.defaultOpens)
  //   sessionStorage.setItem("defaultOpens", values)
  // }
}

export default mutations;
