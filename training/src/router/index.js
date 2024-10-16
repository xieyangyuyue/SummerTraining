//路由跳转
import { createRouter, createWebHistory } from 'vue-router';
import AboutUs from '../components/AboutUs.vue';




const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('../components/Login/Login.vue')

    },
    {
        path: '/about-us',
        name: 'AboutUs',
        component: AboutUs  // 使用导入的组件
    },
    {
        path: '/register',
        name: 'RegisterPage',
        component: () => import('../components/Login/RegisterPage.vue')
    },
    {
        path: '/Indextest',
        name: 'Indextest',
        component: () => import('../components/Indextest.vue'),
        children: [
            {
                path: '/Home',
                name: 'Home',
                meta: {
                    title: '首页'
                },
                component: () => import('../components/Home/Home.vue')
            },
            {
                path: '/edit-profile',
                name: 'EditProfile',
                component: () => import('../components/Home/EditProfile.vue')
            },
            {
                path: '/Admin',
                name: 'admin',
                meta: {
                    title: '管理员管理'
                },
                component: () => import('../components/admin/AdminManage.vue')
            },
            {
                path: '/User',
                name: 'user',
                meta: {
                    title: '用户管理'
                },
                component: () => import('../components/user/UserManage.vue')
            },
            {
                path: '/Priority',
                name: 'priority',
                meta: {
                    title: '运输公司管理'
                },
                component: () => import('../components/priority/PriorityManage.vue')
            },
            {
                path: '/Trainingtype',
                name: 'trainingtype',
                meta: {
                    title: '运输方式管理'
                },
                component: () => import('../components/trainingtype/TrainingtypeManage.vue')
            }, {
                path: '/Trainingorder',
                name: 'trainingorder',
                meta: {
                    title: '运输方式管理'
                },
                component: () => import('../components/trainingorder/TrainingorderManage.vue')
            },
            {
                path: '/Record',
                name: 'record',
                meta: {
                    title: '记录管理'
                },
                component: () => import('../components/record/RecordManage.vue')
            }
            , {
                path: '/Delivery',
                name: 'delivery',
                meta: {
                    title: '配送管理'
                },
                component: () => import('../components/delivery/DeliveryManage.vue')
            },
            {
                path: '/Driver',
                name: 'driver',
                meta: {
                    title: '司机管理'
                },
                component: () => import('../components/driver/DriverManage.vue')
            }
        ]
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export function resetRouter() {
    router.matcher = createRouter({
        module: 'history',
        routes: []
    }).matcher
}
// const router = new VueRouter({
//     module: 'history',
//     routes
// })

export default router;