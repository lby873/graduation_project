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
        <router-view />
      </el-main>
    </el-container>

  </el-container>
</template>

<script>
import Aside from "@/components/BackAside.vue";
import Header from "@/components/BackHeader.vue";
  export default {
    name: 'Home',
    data() {
      return {
        collapseBtnClass: 'el-icon-s-fold',
        isCollapse: false,
        sideWidth: 250,
        logoTextShow: true,
      };
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
          this.sideWidth = 250
          this.collapseBtnClass = 'el-icon-s-fold'
          this.logoTextShow = true
        }
      }
    }
  }
</script>
