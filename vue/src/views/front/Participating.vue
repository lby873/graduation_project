<template>
  <div>
    <div style="margin: 20px 0; text-align: center">
      <template>
        <el-input style="width: 200px;font-size: 13px" placeholder="请输入活动名称" suffix-icon="el-icon-search" v-model="activityName"></el-input>
        <el-input style="width: 200px;margin-left: 20px;font-size: 13px" placeholder="请输入社团名称" suffix-icon="el-icon-search" v-model="organizer"></el-input>
        <el-input style="width: 200px;margin-left: 20px;font-size: 13px" placeholder="请输入地点" suffix-icon="el-icon-search" v-model="address"></el-input>
      </template>
      <el-button style="margin-left: 20px;font-size: 13px" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 20px;font-size: 13px" type="warning" @click="reset">重置</el-button>
    </div>

    <div>
      <div style="color: white;margin-top: 20px">
        <b style="font-size: 22px">已参加的活动</b>
      </div>
      <el-row :gutter="40" style="margin: 10px 20px">
        <el-col :span="8" v-for="item in partData" :key="item.value" style="margin-top: 10px">
          <el-card shadow="hover" style="background-color: rgba(255, 255, 255, 0); color: white;font-size: 16px">
            <el-row>
              <b style="font-size: 20px">{{ item.activityName }}</b>
              <p style="margin-top: 10px">活动时间：{{ item.activityTime }}</p>
              <p style="margin-top: 10px">活动主办方：{{ item.organizer }}</p>
              <p style="margin-top: 10px">活动地点：{{ item.address }}</p>
              <p v-if="item.status == '已参加'" style="margin-top: 10px;color: blue"><b>用户状态：{{ item.status }}</b></p>
              <p v-else style="margin-top: 10px;color: red"><b>用户状态：{{ item.status }}</b></p>
              <el-collapse style="margin-top: 10px">
                <el-collapse-item title="活动详情：" >
                  <p style="color: white;font-size: 15px;line-height: 200%">{{item.detail}}</p>
                </el-collapse-item>
              </el-collapse>
            </el-row>
          </el-card>
        </el-col>
      </el-row>

<!--      分割线-->
      <el-divider></el-divider>

      <div style="color: white;margin-top: 40px">
        <b style="font-size: 22px">已结束的活动</b>
      </div>
      <el-row :gutter="40" style="margin: 10px 20px">
        <el-col :span="8" v-for="item in endData" :key="item.value" style="margin-top: 10px">
          <el-card shadow="hover"  style="background-color: rgba(255, 255, 255, 0); color: white;font-size: 16px">
            <el-row style="font-size: 15px">
              <b style="font-size: 20px">{{ item.name }}</b>
              <p style="margin-top: 10px">活动时间：{{ item.time }}</p>
              <p style="margin-top: 10px">活动主办方：{{ item.organizer }}</p>
              <p style="margin-top: 10px">活动地点：{{ item.address }}</p>
              <p style="margin-top: 10px;color: yellow"><b>活动状态：{{ item.endStatus }}</b></p>
              <el-collapse style="margin-top: 10px">
                <el-collapse-item title="活动详情：" >
                  <p style="color: white;font-size: 15px;line-height: 200%">{{item.detail}}</p>
                </el-collapse-item>
              </el-collapse>
            </el-row>
          </el-card>
        </el-col>
      </el-row>

    </div>

  </div>
</template>

<script>
  export default {
    name: "Participating",
    data(){
      return {
        partData: [],
        endData: [],
        activityName: '',
        organizer: '',
        address: '',
        pageShow: 1,       // 0已报名列表，1已参加列表
        endStatus: '活动已结束',
        signDTO:{
          activityID: '',
          userID: '',
        },
        userLogin: JSON.parse(localStorage.getItem("userLogin")),   //json转化为对象
      }
    },
    created() {
      this.load()
    },
    methods:{
      load(){
        // 请求用户已参加的活动数据
        this.request.get("/sign/page",{
          params: {
            userID: this.userLogin.userID,
            activityName: this.activityName,
            organizer: this.organizer,
            address: this.address,
            pageShow: this.pageShow,
          }
        }).then(res => {
          this.partData = res.data
        })

        // 请求已结束的活动数据
        this.request.get("/activity/activityEndPage",{
          params: {
            activityName: this.activityName,
            organizer: this.organizer,
            address: this.address,
            endStatus: this.endStatus
          }
        }).then(res => {
          this.endData = res.data
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