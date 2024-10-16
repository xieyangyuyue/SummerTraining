import { createStore } from 'vuex';
// 创建一个新的 store 实例
export default createStore({
    state() {
        return {
            menu: [],
        }
    },
    mutations: {
        setUser(state, user) {
            state.user = user;
        },
        setMenu(state, menuList) {
            state.menu = menuList

        }
    },

    getters: {
        getMenu(state) {
            return state.menu
        }
    }
});


