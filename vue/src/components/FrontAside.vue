<template>
  <el-menu class="el-menu-vertical-demo" style="min-height: 100%; overflow-x: hidden; position:fixed;background: none"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <!--导航栏内容-->
    <div style="height: 60px; line-height: 60px; text-align: center; margin-top: 10px">
      <img src="../assets/logo.png" alt="" style="width: 40px; position: relative; top: 10px;">
      <b style="color: white; font-size: 28px" v-show="logoTextShow"> 社团活动系统</b>
    </div>
    <div>
      <el-menu-item index="/addActivity" v-show="orgAdminVisible">
        <i class="el-icon-s-promotion"></i>
        <span slot="title"><b style="font-size: 18px;"> 发布活动</b></span>
      </el-menu-item>
      <el-menu-item index="/orgSignPart" v-show="orgAdminVisible">
        <i class="el-icon-document"></i>
        <span slot="title"><b style="font-size: 18px;"> 报名参与人员</b></span>
      </el-menu-item>
      <el-menu-item index="/home" v-show="userVisible">
        <i class="el-icon-s-home"></i>
        <span slot="title"><b style="font-size: 18px; "> 活动首页</b></span>
      </el-menu-item>
      <el-menu-item index="/sign" v-show="userVisible">
        <i class="el-icon-document"></i>
        <span slot="title"><b style="font-size: 18px;"> 已报名活动</b></span>
      </el-menu-item>
      <el-menu-item index="/part" v-show="userVisible">
        <i class="el-icon-setting"></i>
        <span slot="title"><b style="font-size: 18px;"> 以往活动</b></span>
      </el-menu-item>
      <el-menu-item index="/orgMember" v-show="memberVisible">
        <i class="el-icon-user"></i>
        <span slot="title"><b style="font-size: 18px;"> 社团成员</b></span>
      </el-menu-item>
      <el-menu-item index="/joinOrgApply" v-show="joinOrgVisible">
        <i class="el-icon-add-location"></i>
        <span slot="title"><b style="font-size: 18px;"> 加入社团申请</b></span>
      </el-menu-item>
      <el-menu-item index="/person" >
        <i class="el-icon-s-custom"></i>
        <span slot="title"><b style="font-size: 18px;"> 个人信息</b></span>
      </el-menu-item>

    </div>
  </el-menu>
</template>

<script>

  export default {
    name: "FrontAside",
    data(){
      return{
        userLogin: JSON.parse(localStorage.getItem("userLogin")),   //json转化为对象
        orgAdminVisible: false,
        userVisible: true,
        memberVisible: true,  // 社团成员页面（仅普通用户不可见）
        joinOrgVisible: false // 申请加入社团页面（仅社团成员不可见）
      }
    },
    created() {
      this.load()
    },
    methods:{
      load(){
        if (this.userLogin.identity === "普通用户"){
          this.memberVisible = false
          this.joinOrgVisible = true
        }else if (this.userLogin.identity === "社团管理员"){
          this.orgAdminVisible = true
          this.userVisible = false
          this.joinOrgVisible = true
        }
      }
    },
    props: {
      isCollapse: Boolean,
      logoTextShow: Boolean
    }
  }
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 270px;
}
</style>