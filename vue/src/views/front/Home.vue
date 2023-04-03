<template>
  <div>
    <div style="margin: 20px 0; display: flex;justify-content: center;align-items:center;">
      <template>
        <el-input style="width: 200px;font-size: 13px" placeholder="请输入活动名称" suffix-icon="el-icon-search" v-model="activityName"></el-input>
        <el-input style="width: 200px;margin-left: 20px;font-size: 13px" placeholder="请输入社团名称" suffix-icon="el-icon-search" v-model="organizer"></el-input>
        <el-input style="width: 200px;margin-left: 20px;font-size: 13px" placeholder="请输入地点" suffix-icon="el-icon-search" v-model="address"></el-input>
      </template>
      <el-button style="margin-left: 20px;font-size: 13px" type="primary" @click="load" >搜索</el-button>
      <el-button style="margin-left: 20px;font-size: 13px" type="warning" @click="reset">重置</el-button>
      <span style="margin: 5px 0 0 10px; font-size: 13px; color: white">一共查询到{{tableData.length}}条活动数据</span>
    </div>



<!--    <el-table :data="tableData" border stripe header-cell-class-name="headerBg">-->
<!--      <el-table-column prop="activityID" label="活动ID" width="80" align="center"></el-table-column>-->
<!--      <el-table-column prop="time" label="活动时间" width="100" align="center"></el-table-column>-->
<!--      <el-table-column prop="name" label="活动名称" width="120" align="center"></el-table-column>-->
<!--      <el-table-column prop="organizer" label="主办方社团" width="100" align="center"></el-table-column>-->
<!--      <el-table-column prop="address" label="活动地址" width="100" align="center"></el-table-column>-->
<!--      <el-table-column prop="detail" label="活动详情"></el-table-column>-->
<!--      <el-table-column label="操作"  width="150" align="center">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button type="success" @click="sign(scope.row)">报名<i class="el-icon-edit"></i></el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--    </el-table>-->

    <template>
      <div style="margin: 60px 100px 0 100px">
        <el-carousel :interval="6000" height="450px">
          <el-carousel-item v-for="item in tableData" :key="item.value">
            <el-row style="font-size: 16px; text-align: center; margin: 30px 30px; color: white">
              <b style="font-size: 24px">{{ item.name }}</b>
              <p style="margin-top: 10px">活动时间：{{ item.time }}</p>
              <p style="margin-top: 5px">活动主办方：{{ item.organizer }}</p>
              <p style="margin-top: 5px">活动地点：{{ item.address }}</p>
              <p style="margin: 20px;text-align: left;font-size: 17px; line-height: 150%">活动详情：{{ item.detail }}</p>
              <el-button @click="sign(item)" style="font-size: 18px;width: 150px;border-radius: 15px;background-color: rgba(255, 255, 255, 0);color: red"> 点击报名 </el-button>
            </el-row>
          </el-carousel-item>
        </el-carousel>
      </div>
    </template>


<!--    <div>-->
<!--      <el-row :gutter="40">-->
<!--        <el-col :span="8" v-for="item in tableData" :key="item" style="margin-top: 10px">-->
<!--          <el-card shadow="hover" >-->
<!--            <el-row style="font-size: 15px">-->
<!--              <b style="font-size: 20px">{{ item.name }}</b>-->
<!--              <p style="margin-top: 10px">活动时间：{{ item.time }}</p>-->
<!--              <p style="margin-top: 10px">活动主办方：{{ item.organizer }}</p>-->
<!--              <p style="margin-top: 10px">活动地点：{{ item.address }}</p>-->
<!--              <el-collapse style="margin-top: 10px">-->
<!--                <el-collapse-item title="活动详情" >-->
<!--                  <div>{{item.detail}}</div>-->
<!--                </el-collapse-item>-->
<!--              </el-collapse>-->
<!--              <el-button type="success" @click="sign(item)"  style="margin-top: 10px"> 报 名 </el-button>-->
<!--            </el-row>-->
<!--          </el-card>-->
<!--        </el-col>-->
<!--      </el-row>-->
<!--    </div>-->


  </div>
</template>

<script>
  export default {
    name: "Home",
    data(){
      return {
        userLogin: JSON.parse(localStorage.getItem("userLogin")),   //json转化为对象
        tableData: [],
        activityName: '',
        organizer: '',
        address: '',
        endStatus: '活动进行中',
        signDTO:{
          activityID: '',
          activityTime:'',
          activityName:'',
          organizer:'',
          address:'',
          detail:'',
          userID: '',
          code:'0',       // 0报名、1取消报名
        },
      }
    },
    created() {   // 请求分页查询数据
      this.load()
    },
    methods:{
      load(){
        // 用axios请求数据
        this.request.get("/sign/activityPage",{
          params: {
            userID: this.userLogin.userID,
            activityName: this.activityName,
            organizer: this.organizer,
            address: this.address,
            endStatus: this.endStatus
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
      sign(item){
        this.signDTO.activityID = item.activityID;
        this.signDTO.activityTime = item.time;
        this.signDTO.activityName = item.name;
        this.signDTO.organizer = item.organizer;
        this.signDTO.address = item.address;
        this.signDTO.detail = item.detail;
        this.signDTO.userID = this.userLogin.userID;
        this.request.post("/sign/save",this.signDTO).then(res =>{
          if (res){
            this.$message.success("报名成功")
            this.load()
          }else{
            this.$message.error("报名失败")
          }
        })
      },
    }
  }
</script>

<style>
.el-carousel__item el-row{
  opacity: 0.7;
}
.el-carousel__item:nth-child(2n) {
  width: 100%;
  /*background-color: #99a9bf;*/
}

.el-carousel__item:nth-child(2n+1) {
  width: 100%;
  /*background-color: #d3dce6;*/
  background-color: rgba(255, 255, 255, 0.1);
}
</style>