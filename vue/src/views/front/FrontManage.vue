<template>
  <el-container style="min-height: 100vh">

    <!--左侧导航栏-->
    <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35%); ">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"/>
    </el-aside>

    <!--右边主体部分-->
    <el-container>
      <el-header>
        <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"/>
      </el-header>

      <el-main>
<!--        表示当前页面的子路由会在 <router-view /> 里面展示-->
        <router-view />
      </el-main>
    </el-container>

    <!--背景视频-->
<!--    <div class="videoContainer">-->
<!--      <video class="fullscreenVideo" id="bgVid" playsinline="" autoplay="" muted="" loop="">-->
<!--        <source src="../../assets/bgVideo.mp4" type="video/mp4">-->
<!--      </video>-->
<!--    </div>-->

  </el-container>
</template>

<script>
import Aside from "@/components/FrontAside.vue";
import Header from "@/components/FrontHeader.vue";
  export default {
    name: 'Home',
    data() {
      return {
        collapseBtnClass: 'el-icon-s-fold',
        isCollapse: false,
        sideWidth: 270,
        logoTextShow: true,
        drawer: false,
      }
    },
    components:{
      Aside,
      Header
    },
    methods: {
      collapse() {               // 点击收缩按钮触发
        this.isCollapse = !this.isCollapse
        if (this.isCollapse) {  // 收缩
          this.sideWidth = 64
          this.collapseBtnClass = 'el-icon-s-unfold'
          this.logoTextShow = false
        } else {                // 展开
          this.sideWidth = 270
          this.collapseBtnClass = 'el-icon-s-fold'
          this.logoTextShow = true
        }
      },
    }
  }
</script>

<style>
.el-main {
  /*设置右侧 main 高度*/
  height: calc(100vh - 60px);
}



</style>