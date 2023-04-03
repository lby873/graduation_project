<template>
  <div id="app">
    <router-view v-if="isShow"/>
  </div>
</template>


<script>
export default {
  name: 'App',
  provide(){
    return{
      reload:this.reload
    }
  },
  data(){
    return{
      isShow: true
    }
  },
  methods:{
    // 刷新页面不闪白
    reload(){
      this.isShow=false;
      this.$nextTick(()=>{
        this.isShow=true
      })
    }
  }
}
</script>
