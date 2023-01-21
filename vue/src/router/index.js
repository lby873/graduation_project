import Vue from 'vue'
import VueRouter from 'vue-router'

import FrontManage from "@/views/front/FrontManage.vue";
import Home from "@/views/front/Home.vue";
import SignUp from "@/views/front/SignUp.vue";
import Participating from "@/views/front/Participating.vue";
import Login from "@/views/front/Login.vue";
import Person from "@/views/front/Person.vue";

import UserAdmin from "@/views/UserAdmin.vue";
import AdminManage from "@/views/AdminManage.vue";
import ActivityAdmin from "@/views/ActivityAdmin.vue";


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        component: FrontManage,
        redirect: "/home",
        children: [
            {path: 'home', name:'活动首页', component: Home},
            {path: 'sign', name:'已报名活动', component: SignUp},
            {path: 'part', name:'已参加活动', component: Participating},
            {path: 'person', name:'个人信息', component: Person},
        ]
    },
    {
        path: '/admin/',
        component: AdminManage,
        redirect: "/admin/userAdmin",
        children: [
            {path: 'userAdmin', name:'用户管理', component: UserAdmin},
            {path: 'activityAdmin', name:'活动事项管理', component: ActivityAdmin},
        ]
    },
    {
        path: '/login',
        component: Login
    }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
