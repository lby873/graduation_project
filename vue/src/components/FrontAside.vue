<template>
  <el-menu class="el-menu-vertical-demo" style="min-height: 100%; overflow-x: hidden; position:fixed;"
           background-color="rgb(48, 65, 86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <!--导航栏内容-->
    <div style="height: 60px; line-height: 60px; text-align: center; margin-top: 10px">
      <img src="../assets/logo.png" alt="" style="width: 25px; position: relative; top: 5px; right: 5px">
      <b style="color: white; font-size: 24px" v-show="logoTextShow"> 社团活动报名系统</b>
    </div>
    <div>
      <el-menu-item index="/addActivity" v-show="addActivityVisible">
        <i class="el-icon-s-promotion"></i>
        <span slot="title"><b style="font-size: 18px;"> 发布活动</b></span>
      </el-menu-item>
      <el-menu-item index="/home" v-show="homeVisible">
        <i class="el-icon-s-home"></i>
        <span slot="title"><b style="font-size: 18px; "> 活动首页</b></span>
      </el-menu-item>
      <el-menu-item index="/sign" v-show="signVisible">
        <i class="el-icon-document"></i>
        <span slot="title"><b style="font-size: 18px;"> 已报名活动</b></span>
      </el-menu-item>
      <el-menu-item index="/part" v-show="partVisible">
        <i class="el-icon-setting"></i>
        <span slot="title"><b style="font-size: 18px;"> 已参加活动</b></span>
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
        addActivityVisible: false,
        homeVisible: true,
        signVisible: true,
        partVisible: true,
      }
    },
    created() {
      this.load()
    },
    methods:{
      load(){
        if (this.userLogin.identity === "社团管理员"){
          this.addActivityVisible = true;
          this.homeVisible = false;
          this.signVisible = false;
          this.partVisible = false;
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
  width: 250px;
}
</style>