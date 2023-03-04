<template>
  <div>
    <div v-if="userApplyVisible">
      <el-popconfirm confirm-button-text='确定' cancel-button-text='取消' icon="el-icon-info"
                     icon-color="red" title="您确定取消申请吗？" @confirm="cancelApply()" v-show="cancelJoin">
        <el-button type="danger" slot="reference" style="float:right; margin-bottom: 10px">
          取消申请加入 {{this.joinOrganization}}
        </el-button>
      </el-popconfirm>

      <el-table :data="tableData" border stripe header-cell-class-name="headerBg">
        <el-table-column prop="orgID" label="社团ID" width="60" align="center"></el-table-column>
        <el-table-column prop="orgName" label="社团名称" width="100" align="center"></el-table-column>
        <el-table-column prop="orgCreatedDate" label="社团创建时间" width="100" align="center"></el-table-column>
        <el-table-column prop="orgSummary" label="社团概要"></el-table-column>
        <el-table-column label="操作"  width="200" align="center">
          <template slot-scope="scope">
            <el-button type="success" @click="apply(scope.row)" :disabled="joinBtn">申请加入 <i class="el-icon-edit"></i></el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页行 -->
      <div style="padding: 10px 0">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[3, 5, 10, 15]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>
    </div>

    <div v-if="orgAdminVisible">

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
      pageSize: 5,
      joinBtn: false,     // 申请加入按钮
      cancelJoin: false,  // 取消申请加入按钮
      joinOrganization: '',   // 已经申请加入的社团
      userApplyVisible: true, // 用户加入社团申请div
      orgAdminVisible: false, // 管理员审批申请div
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

      } else {    // 普通用户登录
        this.request.get("/org/page",{
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize
          }
        }).then(res => {
          this.tableData = res.data
          this.total = res.total
        })

        this.request.get("/joinOrg/",{    // 查询是否已经有申请加入的记录
          params: {
            username: this.userLogin.username,
            organization: ''
          }
        }).then(res =>{
          if (res.data.length > 0){      // 已申请加入
            this.joinBtn = true     // 设置申请加入按钮不可用
            this.cancelJoin = true  // 设置取消加入按钮可见
            this.joinOrganization = res.data[0].joinOrganization
          } else {                  // 未申请加入
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

    apply(row){
      this.joinOrgForm.organization = row.orgName
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
          this.request.delete("/joinOrg/" + row.userID)
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
</style>