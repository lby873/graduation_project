<template>
  <div style="display: flex;justify-content: center;align-items:center;height: 90%;">
    <el-col :sm="12" :lg="6" style="width: 100%">
      <el-result icon="error">
        <b slot="title" style="color: white;font-size: 30px">错误提示 404!</b>
        <p slot="subTitle" style="color: white;font-size: 20px;">对不起，您访问的网页到外太空去了!</p>
      </el-result>
    </el-col>
  </div>
</template>

<script>
export default {
  name: "Result_404"
}
</script>

<style scoped>
.el-result__title{
  color: red;
}


</style>