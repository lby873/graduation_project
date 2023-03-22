<template>
  <div>
    <el-table :data="memberTableData" border stripe header-cell-class-name="headerBg">
      <el-table-column prop="userID" label="用户ID" align="center"></el-table-column>
      <el-table-column prop="username" label="用户名" align="center"></el-table-column>
      <el-table-column prop="nickname" label="昵称" align="center"></el-table-column>
      <el-table-column prop="phone" label="手机号码" align="center"></el-table-column>
      <el-table-column prop="identity" label="用户身份" align="center"></el-table-column>
      <el-table-column prop="organization" label="所属社团" align="center"></el-table-column>
      <el-table-column label="操作"  width="200" align="center" v-if="alterIdentityVisible">
        <!--v-if 设置是否可见-->
        <template slot-scope="scope">
          <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='取消'
                         icon="el-icon-info" icon-color="red" title="您确定将删除该成员吗？"
                         @confirm="alterIdentity(scope.row.userID)">
            <el-button type="danger" slot="reference">删除成员 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  export default {
    name: "orgMember",
    data(){
      return{
        userLogin: JSON.parse(localStorage.getItem("userLogin")),   //json转化为对象
        memberTableData: '',
        alterIdentityVisible: false,
        orgMemberCode: 1,       // 1表示社团成员要包含管理员，0表示不包含
      }
    },
    created() {
      this.load()
    },
    methods:{
      load(){
        // 获取成员信息列表
        if (this.userLogin.identity === "社团管理员"){
          this.alterIdentityVisible = true
          this.orgMemberCode = 0
        }
        this.request.get("/user/findMember",{
          params: {
            organization: this.userLogin.organization,
            orgMemberCode: this.orgMemberCode,
          }
        }).then(res => {
          this.memberTableData = res.data
        })
      },

      alterIdentity(userID){
        this.request.post("/user/alterMemberIdentity/" + userID).then(res => {
          if (res) {
            this.$message.success("删除成员成功")
            this.load()
            // window.location.reload()      // 刷新页面
          }
        })
      }
    }
  }
</script>

<style scoped>
.margin-top{
  width: 50%;
  padding: 10px;
  margin: 50px auto;
  font-size: 15px;
}
</style>