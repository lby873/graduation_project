import Vue from 'vue'
import VueRouter from 'vue-router'

import FrontManage from "@/views/front/FrontManage.vue";
import Home from "@/views/front/Home.vue";
import SignUp from "@/views/front/SignUp.vue";
import Participating from "@/views/front/Participating.vue";
import Login from "@/views/front/Login.vue";
import ActivityEnd from "@/views/front/ActivityEnd.vue";
import PersonMsg from "@/views/front/PersonMsg.vue";
import AddActivity from "@/views/front/AddActivity.vue";
import OrgSignPart from "@/views/front/OrgSignPart.vue";
import JoinOrgApply from "@/views/front/JoinOrgApply.vue";
import orgMember from "@/views/front/OrgMember.vue";

import UserAdmin from "@/views/UserAdmin.vue";
import AdminManage from "@/views/AdminManage.vue";
import ActivityAdmin from "@/views/ActivityAdmin.vue";
import SignPartAdmin from "@/views/SignPartAdmin.vue";
import OrganizationAdmin from "@/views/OrganizationAdmin.vue";



Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        component: FrontManage,     // 固定的框架
        redirect: "/login",
        children: [
            {path: 'home', name:'活动首页', component: Home},
            {path: 'sign', name:'已报名活动', component: SignUp},
            {path: 'part', name:'已参加活动', component: Participating},
            {path: 'person', name:'个人信息', component: PersonMsg},
            {path: 'end', name:'已结束活动', component: ActivityEnd},
            {path: 'addActivity', name:'发布活动', component: AddActivity},
            {path: 'orgSignPart', name:'报名参与人员', component: OrgSignPart},
            {path: 'joinOrgApply', name:'加入社团申请', component: JoinOrgApply},
            {path: 'orgMember', name:'社团成员', component: orgMember},
        ]
    },
    {
        path: '/admin/',
        component: AdminManage,     // 固定框架
        redirect: "/admin/userAdmin",
        children: [
            {path: 'userAdmin', name:'用户管理', component: UserAdmin},
            {path: 'activityAdmin', name:'活动事项管理', component: ActivityAdmin},
            {path: 'signPartAdmin', name:'活动报名参与人员', component: SignPartAdmin},
            {path: 'organizationAdmin', name:'社团管理', component: OrganizationAdmin},
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
});

router.beforeEach((to, from, next)=>{

    // 检查本地存储是否有已登录的用户信息
    let userLogin = JSON.parse(localStorage.getItem('userLogin'))
    // 如果用户访问登录页，则直接放行
    if (to.path === "/login") return next();
    // 如果用户未登录，则直接返回登陆页
    if (!userLogin) return next("/login")
    next();

})


export default router
