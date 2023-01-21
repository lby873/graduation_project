<template>
  <el-menu class="el-menu-vertical-demo" style="min-height: 100%;overflow-x: hidden; position:fixed;"
           background-color="rgb(48, 65, 86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <!--导航栏内容-->
    <div style="height: 60px; line-height: 60px; text-align: center;">
      <img src="../assets/logo.png" alt="" style="width: 25px; position: relative; top: 5px; right: 5px">
      <b style="color: white; font-size: 22px" v-show="logoTextShow">社团活动报名系统</b>
    </div>
    <div>
      <el-menu-item index="/home">
        <i class="el-icon-menu"></i>
        <span slot="title"><b style="font-size: 17px; ">活动首页</b></span>
      </el-menu-item>
      <el-menu-item index="/sign">
        <i class="el-icon-document"></i>
        <span slot="title"><b style="font-size: 17px;">已报名活动</b></span>
      </el-menu-item>
      <el-menu-item index="/part">
        <i class="el-icon-setting"></i>
        <span slot="title"><b style="font-size: 17px;">已参加活动</b></span>
      </el-menu-item>
      <el-menu-item index="/person">
        <i class="el-icon-s-custom"></i>
        <span slot="title"><b style="font-size: 17px;">个人信息</b></span>
      </el-menu-item>
<!--      <el-menu-item index="/person" v-show="false">-->
<!--        <i class="el-icon-s-custom"></i>-->
<!--        <span slot="title"><b style="font-size: 17px;">个人信息</b></span>-->
<!--      </el-menu-item>-->
    </div>
  </el-menu>
</template>

<script>
  export default {
    name: "Aside",
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
</style>