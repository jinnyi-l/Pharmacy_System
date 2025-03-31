/**
 *  重写ElementUI的Message
 * 1、添加一个消息提示窗数量限制、最多3个，超出之后先关闭最开始的一个
 * 2、添加一个蓝色的自定义消息提示窗参数为 blue 用法与'error'、'success'、'info'、'warning'类似
 */
 import { Message } from 'element-ui'
 const instances = []
 const newMessage = options => {
   if (options.type === 'blue') {
     let obj = {
       customClass: 'c_customClass_blue',
       iconClass: 'iconfont iconinfo c_iconClass_info'
     }
     options = Object.assign(obj, options)
   }
   if (instances.length <= 2) {
     instances.push(Message(options))
   } else {
     instances[0].close()
     instances.shift()
     instances.push(Message(options))
   }
 }
 const messageList = ['error', 'success', 'info', 'warning', 'blue']
 messageList.forEach(type => {
   newMessage[type] = options => {
     if (typeof options === 'string') {
       options = {
         message: options
       }
     }
     options.type = type
     return newMessage(options)
   }
 })
 export const message = newMessage
 