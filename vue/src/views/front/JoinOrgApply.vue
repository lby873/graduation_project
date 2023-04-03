<template>
  <div>

    <!--    普通用户申请-->
    <div v-if="userApplyVisible">
      <div v-show="cancelJoin">
        <el-popconfirm confirm-button-text='确定' cancel-button-text='取消' icon="el-icon-info"
                       icon-color="red" title="您确定取消申请吗？" @confirm="cancelApply()" >
          <el-button type="danger" slot="reference" style="margin-bottom: 10px;font-size: 14px">
            取消申请加入 {{this.joinOrganization}}
          </el-button>
        </el-popconfirm>
      </div>

      <div style="margin-top: 10px">
        <el-row :gutter="40">
          <el-col :span="8" v-for="item in tableData" :key="item.value" style="margin-top: 10px">
            <el-card shadow="hover" style="background-color: rgba(255, 255, 255, 0); color: white;font-size: 16px">
              <el-row>
                <div>
                  <b style="font-size: 20px">{{ item.orgName }}</b>
                  <el-button type="success" @click="apply(item)" :disabled="joinBtn" style="float:right;font-size: 14px"> 申请加入 </el-button>
                </div>
                <p style="margin-top: 10px">创立时间：{{ item.orgCreatedDate }}</p>
                <p style="margin-top: 10px;line-height: 200%">社团概要：{{ item.orgSummary }}</p>
              </el-row>
            </el-card>
          </el-col>
        </el-row>
      </div>

    </div>

    <!--    社团管理员审核-->
    <div v-if="orgAdminVisible" class="table-wrapper" style="margin-top: 20px">
      <el-table :data="tableData" border stripe header-cell-class-name="headerBg">
        <el-table-column prop="userID" label="用户ID" width="100" align="center"></el-table-column>
        <el-table-column prop="username" label="用户名" align="center"></el-table-column>
        <el-table-column prop="userNickname" label="用户昵称" align="center"></el-table-column>
        <el-table-column prop="userPhone" label="用户手机号" align="center"></el-table-column>
        <el-table-column prop="userIdentity" label="用户身份" align="center"></el-table-column>
        <el-table-column label="操作"  width="200" align="center">
          <template slot-scope="scope">
            <el-button type="success" @click="agreeApply(scope.row)">同意</el-button>
            <el-popconfirm confirm-button-text='确定' cancel-button-text='取消' icon="el-icon-info"
                           icon-color="red" title="您确定拒绝该申请吗？" @confirm="refuseApply(scope.row)">
              <el-button type="danger" slot="reference" style="margin-left: 10px">拒绝</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>



  </div>
</template>

<script>
export default {
  name: "JoinOrg",
  data(){
    return {
      userLogin: JSON.parse(localStorage.getItem("userLogin")),   //json转化为对象
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 999999,
      joinBtn: false,     // 申请加入按钮
      cancelJoin: false,  // 取消申请加入按钮
      joinOrganization: '',   // 已申请加入的社团名称
      userApplyVisible: true, // 用户加入社团申请的div
      orgAdminVisible: false, // 管理员审批申请的div
      joinOrgForm: {          // 申请加入社团的表单
        userID:'',
        username:'',
        nickname:'',
        phone:'',
        identity:'',
        organization:'',
      },
    }
  },
  created() {   // 请求分页查询数据
    this.load()
  },
  methods:{
    load(){
      this.joinOrgForm = this.userLogin

      // 判断用户身份，显示不同div
      if (this.userLogin.identity === "社团管理员"){    // 社团管理员登录
        this.userApplyVisible = false
        this.orgAdminVisible = true

        // 用axios请求数据
        this.request.get("/joinOrg/",{    // 查询是否已经有申请加入的记录
          params: {
            username: '',
            organization: this.userLogin.organization
          }
        }).then(res => {
          this.tableData = res.data
        })

      } else {        // 普通用户登录
        this.request.get("/org/page",{
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        }).then(res => {
          this.tableData = res.data
        })

        this.request.get("/joinOrg/",{    // 查询是否已经有申请加入的记录
          params: {
            username: this.userLogin.username,
            organization: ''
          }
        }).then(res =>{
          if (res.data.length > 0){      // 已有申请记录
            this.joinBtn = true     // 设置申请加入按钮不可用
            this.cancelJoin = true  // 设置取消加入按钮可见
            this.joinOrganization = res.data[0].joinOrganization
          } else {                  // 没有申请记录
            this.joinBtn = false     // 设置申请加入按钮可用
            this.cancelJoin = false  // 设置取消加入按钮不可见
          }
        })
      } // 普通用户页面的初始化
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },

    apply(item){
      this.joinOrgForm.organization = item.orgName
      this.request.post("/joinOrg/applyToJoin",this.joinOrgForm).then(res => {
        if (res){
          // this.joinBtn = true     // 设置申请加入按钮不可用
          // this.cancelJoin = true  // 设置取消加入按钮可用
          this.load()
          this.$message.success("申请成功")
        }
      })
    },

    cancelApply(){
      this.request.delete("/joinOrg/" + this.userLogin.userID).then(res =>{
        if (res){
          // this.joinBtn = false     // 设置申请加入按钮可用
          // this.cancelJoin = false  // 设置取消加入按钮不可见
          this.load()
          this.$message.success("已成功取消")
        }
      })
    },

    refuseApply(row){
      this.request.delete("/joinOrg/" + row.userID).then(res =>{
        if (res){
          this.$message.success("已成功拒绝该申请")
          this.load()
        }
      })
    },

    agreeApply(row){
      this.request.post("/user/alterUserToMember/"+row.userID,this.userLogin.organization).then(res => {
        if (res) {
          this.$message.success("已同意该申请")
          this.request.delete("/joinOrg/" + row.userID)   // 申请表删除该记录
          window.location.reload()      // 必须刷新页面
        }
      })

    }

  }
}
</script>


<style scoped>
.headerBg {
  background: #eee!important;
}
/**表格背景透明 */
/*透明化整体*/
.table-wrapper /deep/.el-table, .el-table__expanded-cell {
  background-color: transparent !important;
}
/*透明化行、单元格,删除表头下横线*/
.table-wrapper /deep/ tr, .table-wrapper /deep/ th, .table-wrapper /deep/ td {
  background: #1439391c !important;
  color:#fff;
  line-height: 30px;
  font-size: 15px;
  /*    border-bottom: 0px; //删除表头下横线*/
}
/*//hover时样式*/
.table-wrapper /deep/  .el-table tbody tr:hover>td {
  background-color: #367f7f78 !important
}
/*// 表格内容(有用)*/
.table-wrapper /deep/ .el-table__row {
  background: #1439391c !important;
  color: #46d4ff;
}
/**表格背景透明end */
</style>