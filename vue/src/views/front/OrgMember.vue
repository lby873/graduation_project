<template>
  <div style="margin: 20px 40px"  class="table-wrapper">

<!--    <el-table :data="memberTableData" border header-cell-class-name="headerBg">-->
<!--      <el-table-column prop="userID" label="用户ID" align="center"></el-table-column>-->
<!--      <el-table-column prop="username" label="用户名" align="center"></el-table-column>-->
<!--      <el-table-column prop="nickname" label="昵称" align="center"></el-table-column>-->
<!--      <el-table-column prop="phone" label="手机号码" align="center"></el-table-column>-->
<!--      <el-table-column prop="identity" label="用户身份" align="center"></el-table-column>-->
<!--      <el-table-column prop="organization" label="所属社团" align="center"></el-table-column>-->
<!--      <el-table-column label="操作"  width="200" align="center" v-if="alterIdentityVisible">-->
<!--        &lt;!&ndash;v-if 设置是否可见&ndash;&gt;-->
<!--        <template slot-scope="scope">-->
<!--          <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='取消'-->
<!--                         icon="el-icon-info" icon-color="red" title="您确定将删除该成员吗？"-->
<!--                         @confirm="alterIdentity(scope.row.userID)">-->
<!--            <el-button type="danger" slot="reference">删除成员 <i class="el-icon-remove-outline"></i></el-button>-->
<!--          </el-popconfirm>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--    </el-table>-->

    <div>
      <el-row :gutter="40">
<!--        span规定一个col占据24份中的多少份.-->
        <el-col :span="8" v-for="item in memberTableData" :key="item.value" style="margin-top: 20px">
          <el-card shadow="hover" style="background-color: rgba(255, 255, 255, 0.1); color: white;font-size: 16px">
            <el-row>
              <div>
                <b style="font-size: 18px">{{ item.username }}</b>
                <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='取消' icon="el-icon-info"
                               icon-color="red" title="您确定将删除该成员吗？" @confirm="alterIdentity(item.userID)">
                  <el-button type="danger" slot="reference" style="float:right;font-size: 14px" v-if="alterIdentityVisible">
                    删除成员 <i class="el-icon-remove-outline"></i>
                  </el-button>
                </el-popconfirm>
              </div>
              <p style="margin-top: 10px;">用户ID：{{ item.userID }}</p>
              <p style="margin-top: 10px;">用户昵称：{{ item.nickname }}</p>
              <p style="margin-top: 10px;">用户手机号：{{ item.phone }}</p>
              <p style="margin-top: 10px;">用户身份：{{ item.identity }}</p>
              <p style="margin-top: 10px;">所属社团：{{ item.organization }}</p>
            </el-row>
          </el-card>
        </el-col>
      </el-row>
    </div>

  </div>
</template>

<script>
  export default {
    name: "orgMember",
    data(){
      return{
        userLogin: JSON.parse(localStorage.getItem("userLogin")),   //json转化为对象
        memberTableData: [],
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