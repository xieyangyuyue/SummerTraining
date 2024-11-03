import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

// 创建一个新的 store 实例
export default new Vuex.Store({
    state: {
        menu: [],
        user: null // 添加 user 状态
    },
    mutations: {
        setUser (state, user) {
            state.user = user;
        },
        setMenu(state, menuList) {
            state.menu = menuList;
        }
    },
    getters: {
        getMenu(state) {
            return state.menu;
        }
    }
});
