<template>
  <div>
    <div style="margin: 10px 0 40px 0; text-align: center">
      <template>
        <el-input style="width: 200px;font-size: 13px" placeholder="请输入活动名称" suffix-icon="el-icon-search" v-model="activityName"></el-input>
        <el-input style="width: 200px;margin-left: 20px;font-size: 13px" placeholder="请输入社团名称" suffix-icon="el-icon-search" v-model="organizer"></el-input>
        <el-input style="width: 200px;margin-left: 20px;font-size: 13px" placeholder="请输入地点" suffix-icon="el-icon-search" v-model="address"></el-input>
      </template>
      <el-button style="margin-left: 20px;font-size: 13px" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 20px;font-size: 13px" type="warning" @click="reset">重置</el-button>
      <span style="margin: 5px 0 0 10px; font-size: 13px; color: white">一共查询到{{tableData.length}}条报名数据</span>
    </div>

    <div>
      <el-timeline v-for="item in tableData" :key="item.value" :reverse="true">
        <el-timeline-item style="margin: 10px 10px;" color="white" size="large">
          <el-card style="margin-right: 100px; background-color: rgba(255, 255, 255, 0); color: white;font-size: 16px">
            <div>
              <b style="font-size: 20px;">{{ item.activityName }} </b>
              <el-button type="danger" @click="cancelSign(item)" style="margin-left: 10px;font-size: 14px;float:right;"> 取消报名 </el-button>
            </div>
            <p style="margin-top: 10px">活动时间：{{ item.activityTime }}</p>
            <p style="margin-top: 10px">活动主办方：{{ item.organizer }}</p>
            <p style="margin-top: 10px">活动地点：{{ item.address }}</p>
            <el-collapse style="margin-top: 10px;">
              <el-collapse-item title="活动详情：" >
               <p style="color: white;font-size: 15px;line-height: 150%">{{item.detail}}</p>
              </el-collapse-item>
            </el-collapse>
          </el-card>

        </el-timeline-item>
      </el-timeline>
    </div>
  </div>
</template>

<script>
  export default {
    name: "SignUp",
    data(){
      return {
        tableData: [],
        activityName: '',
        organizer: '',
        address: '',
        pageShow: 0,       // 0已报名列表，1已参加列表
        signDTO:{
          activityID: '',
          userID: '',
          code:'1',       // 0报名、1取消报名
        },
        userLogin: JSON.parse(localStorage.getItem("userLogin")),   //json转化为对象
      }
    },
    created() {   // 请求分页查询数据
      this.load()
    },
    methods:{
      load(){
        // 用axios请求数据
        this.request.get("/sign/page",{
          params: {
            userID: this.userLogin.userID,
            activityName: this.activityName,
            organizer: this.organizer,
            address: this.address,
            pageShow: this.pageShow,
          }
        }).then(res => {
          this.tableData = res.data
        })

      },
      handleSizeChange(pageSize) {
        this.pageSize = pageSize
        this.load()
      },
      handleCurrentChange(pageNum) {
        this.pageNum = pageNum
        this.load()
      },
      reset(){
        this.activityName = ""
        this.organizer = ""
        this.address = ""
        this.load()
      },
      cancelSign(item){
        this.signDTO.activityID = item.activityID;
        this.signDTO.userID = this.userLogin.userID;
        this.request.post("/sign/save",this.signDTO).then(res =>{
          if (res){
            this.$message.success("取消报名成功")
            this.load()
          }else{
            this.$message.error("取消报名失败")
          }
        })
      },
    }
  }
</script>

<style scoped>
/deep/ .el-collapse-item__header{
  font-size: 16px;
  border: none;
  background-color: rgba(0, 0, 0, 0);
  color: white;
}
/deep/ .el-collapse-item__wrap{
  background-color: rgba(0, 0, 0, 0);
  color: white;
}
</style>