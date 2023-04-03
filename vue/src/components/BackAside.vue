<template>
  <el-menu class="el-menu-vertical-demo" style="min-height: 100%;overflow-x: hidden; position:fixed;background: none;"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <!--导航栏内容-->
    <div style="height: 60px; line-height: 60px; text-align: center; margin-top: 10px;">
      <img src="../assets/logo.png" alt="" style="width: 40px; position: relative; top: 10px;">
      <b style="color: white; font-size: 28px;" v-show="logoTextShow"> 后台管理系统</b>
    </div>
    <el-menu-item index="/admin/userAdmin">
      <i class="el-icon-s-custom" ></i>
      <span slot="title"><b style="font-size: 18px; "> 用户管理</b></span>
    </el-menu-item>

    <el-submenu index="">
      <template slot="title">
        <i class="el-icon-setting"></i>
        <span slot="title"><b style="font-size: 18px"> 活动管理</b></span>
      </template>
      <el-menu-item index="/admin/activityAdmin" style="text-align: center">
        <i class="el-icon-tickets"></i><b style="font-size: 15px">活动事项</b>
      </el-menu-item>
      <el-menu-item index="/admin/signPartAdmin" style="text-align: center">
        <i class="el-icon-user"></i><b style="font-size: 15px">活动报名参与人员</b>
      </el-menu-item>
    </el-submenu>

    <el-menu-item index="/admin/organizationAdmin">
      <i class="el-icon-office-building" ></i>
      <span slot="title"><b style="font-size: 18px; "> 社团管理</b></span>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: "BackAside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  }
}
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 250px;
}
/deep/.el-submenu .el-menu{
  background: none;
}
</style>