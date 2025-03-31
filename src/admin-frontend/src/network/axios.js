import axios from 'axios'
import Element from "element-ui";
import store from "../store";
import router from "../router";

axios.defaults.baseURL = "http://localhost:9000/"

// // 前置拦截
// axios.interceptors.request.use(
//   config => {
//     return config
//   }
// )

// axios.interceptors.response.use(
//   response => {
//     let res = response.data

//     // console.log(res);

//     if (res.code === 200) {
//       return response
//     } else {
//       Element.Message.error(res.msg)

//       return Promise.reject(res.msg)
//     }
//   },
//   error => {
//     // console.log(error);

//     if (error.response.data) {
//       Element.Message.error(error.response.data.msg)
//     }

//     if (error.response.status === 401) {
//       store.commit("REMOVE_INFO")
//       router.push("/admin/login")
//     }


//     return Promise.reject(error)
//   }
// )
