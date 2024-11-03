// main.js

import Vue from 'vue' // 从 Vue 中导入 Vue 实例
import ElementUI from 'element-ui' // 引入 Element UI 组件库
import 'element-ui/lib/theme-chalk/index.css' // 引入 Element UI 的 CSS 样式
import App from './App.vue' // 引入主组件
import './assets/global.css' // 引入全局样式
import axios from 'axios' // 引入 Axios
// import router from './router' // 引入路由
// import store from './store' // 引入 Vuex 状态管理

// 设置全局 Axios 的基础 URL
axios.defaults.baseURL = 'http://111.170.11.144:13400'

// 将 Axios 绑定到 Vue 的原型，以便在组件中通过 this.$http 访问
Vue.prototype.$http = axios

// 注册 Element UI 组件
Vue.use(ElementUI, { size: 'small' }) // 设置全局组件的默认尺寸为小（'small'）

// 创建和挂载 Vue 实例
new Vue({
    // router, // 注入路由
    // store, // 注入 Vuex
    render: h => h(App), // 渲染主组件 App
}).$mount('#app') // 挂载到 ID 为 app 的元素上
