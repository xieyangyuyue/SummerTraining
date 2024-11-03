// // 路由跳转
// import Vue from 'vue';
// import Router from 'vue-router';
// // import AboutUs from '../components/AboutUs.vue';
//
// Vue.use(Router);
//
// const routes = [
//     {
//         path: '/',
//         name: 'login',
//         component: () => import('../components/Login/Login.vue')
//     },
    // {
    //     path: '/about-us',
    //     name: 'AboutUs',
    //     component: AboutUs  // 使用导入的组件
    // },
    // {
    //     path: '/register',
    //     name: 'RegisterPage',
    //     component: () => import('../components/Login/RegisterPage.vue')
    // },
    // {
    //     path: '/Indextest',
    //     name: 'Indextest',
    //     component: () => import('../components/Indextest.vue'),
    //     children: [
    //         {
    //             path: 'Home',  // 注意：子路由的路径不需要以斜杠开头
    //             name: 'Home',
    //             meta: {
    //                 title: '首页'
    //             },
    //             component: () => import('../components/Home/Home.vue')
    //         },
    //         {
    //             path: 'edit-profile',
    //             name: 'EditProfile',
    //             component: () => import('../components/Home/EditProfile.vue')
    //         },
    //         {
    //             path: 'Admin',
    //             name: 'admin',
    //             meta: {
    //                 title: '管理员管理'
    //             },
    //             component: () => import('../components/admin/AdminManage.vue')
    //         },
    //         {
    //             path: 'User ',
    //             name: 'user',
    //             meta: {
    //                 title: '用户管理'
    //             },
    //             component: () => import('../components/user/UserManage.vue')
    //         },
    //         {
    //             path: 'Priority',
    //             name: 'priority',
    //             meta: {
    //                 title: '运输公司管理'
    //             },
    //             component: () => import('../components/priority/PriorityManage.vue')
    //         },
    //         {
    //             path: 'Trainingtype',
    //             name: 'trainingtype',
    //             meta: {
    //                 title: '运输方式管理'
    //             },
    //             component: () => import('../components/trainingtype/TrainingtypeManage.vue')
    //         },
    //         {
    //             path: 'Trainingorder',
    //             name: 'trainingorder',
    //             meta: {
    //                 title: '运输方式管理'
    //             },
    //             component: () => import('../components/trainingorder/TrainingorderManage.vue')
    //         },
    //         {
    //             path: 'Record',
    //             name: 'record',
    //             meta: {
    //                 title: '记录管理'
    //             },
    //             component: () => import('../components/record/RecordManage.vue')
    //         },
    //         {
    //             path: 'Delivery',
    //             name: 'delivery',
    //             meta: {
    //                 title: '配送管理'
    //             },
    //             component: () => import('../components/delivery/DeliveryManage.vue')
    //         },
    //         {
    //             path: 'Driver',
    //             name: 'driver',
    //             meta: {
    //                 title: '司机管理'
    //             },
    //             component: () => import('../components/driver/DriverManage.vue')
    //         }
    //     ]
    // }
];

const router = new Router({
    mode: 'history', // 使用 HTML5 History 模式
    routes
});

// 重置路由的功能
export function resetRouter() {
    router.matcher = new Router({
        mode: 'history',
        routes: []
    }).matcher;
}

export default router;
