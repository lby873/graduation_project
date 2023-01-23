<template>
  <div style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px; display: flex">
    <div style="flex: 1; font-size: 20px">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
    </div>
    <el-dropdown style="width: 250px; cursor: pointer;">
      <div style="text-align:right;font-size: 16px">
        <span><b>欢迎您，{{userLogin.nickname}}</b></span><i class="el-icon-arrow-down" style="margin-left: 10px"></i>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 90px; margin-right: 10px">
        <el-dropdown-item style="font-size: 14px; padding: 5px 10px;text-align: center">
          <b @click="logout">退出</b>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>
</template>

<script>
export default {
  name: "FrontHeader",
  data() {
    return {
      userLogin: JSON.parse(localStorage.getItem("userLogin")),   //json转化为对象
    }
  },
  methods:{
    logout() {
      this.$router.push("/login")
      localStorage.removeItem("userLogin")
      this.$message.success("退出成功")
    },
    person(){
      this.$router.push("/person")
    }
  },
  props: {
    collapseBtnClass: String,
    collapse: function (){},
  }
}
</script>

<style scoped>

</style>