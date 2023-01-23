<template>
  <div style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px; display: flex">
    <div style="flex: 1; font-size: 20px">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
    </div>
    <div style="text-align:right; width: 150px; cursor: pointer;">
      <span><b style="font-size: 16px">欢迎您，超级管理员</b></span>
    </div>
  </div>
</template>

<script>
export default {
  name: "BackHeader",
  data() {
    return {

    }
  },
  methods:{

  },
  props: {
    collapseBtnClass: String,
    collapse: function (){},
  }
}
</script>

<style scoped>

</style>