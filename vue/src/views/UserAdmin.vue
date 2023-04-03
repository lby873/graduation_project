<template>
  <div style="margin: 0 10px">
    <div style="margin: 10px 0 15px 0;display: flex;justify-content: center;align-items:center;">
      <template>
        <el-input style="width: 180px;font-size: 13px" placeholder="请输入用户名" suffix-icon="el-icon-search" v-model="user_name"></el-input>
        <el-input style="width: 180px; margin-left: 10px;font-size: 13px" placeholder="请输入昵称" suffix-icon="el-icon-search" v-model="nick_name"></el-input>
        <el-input style="width: 180px; margin-left: 10px;font-size: 13px" placeholder="请输入手机号" suffix-icon="el-icon-search" v-model="phone_number"></el-input>
        <el-select style="width: 180px; margin-left: 10px;font-size: 13px" placeholder="请选择用户身份" suffix-icon="el-icon-search" v-model="identitySelect">
          <el-option v-for="item in identityList" :key="item.identity" :label="item.identity"
                     :value="item.identity" >
          </el-option>
        </el-select>
        <el-select style="width: 200px; margin-left: 10px;font-size: 13px" placeholder="请选择社团" suffix-icon="el-icon-search" v-model="orgSelect">
          <el-option v-for="item in orgList" :key="item.organization" :label="item.organization"
                     :value="item.organization" >
          </el-option>
        </el-select>
      </template>
      <el-button style="margin-left: 10px;font-size: 13px" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 10px;font-size: 13px" type="warning" @click="reset">重置</el-button>
    </div>
    <div>
      <el-button type="primary" @click="handleAdd" style="margin-bottom: 20px;font-size: 13px">
        新增用户 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
    </div>

<!--    用户数据表-->
    <div class="table-wrapper">
      <el-table :data="tableData" border stripe header-cell-class-name="headerBg" >
        <el-table-column prop="userID" sortable label="用户ID" width="100" align="center"></el-table-column>
        <el-table-column prop="username" label="用户名" align="center"></el-table-column>
        <el-table-column prop="nickname" label="昵称" align="center"></el-table-column>
        <el-table-column prop="phone" label="手机号码" align="center"></el-table-column>
        <el-table-column prop="identity" label="用户身份" align="center"></el-table-column>
        <el-table-column prop="organization" label="所属社团" align="center"></el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button type="success" @click="handleEdit(scope.row)">修改<i class="el-icon-edit"></i></el-button>
            <el-popconfirm class="ml-5" confirm-button-text='确定删除' cancel-button-text='取消'
                           icon="el-icon-info" icon-color="red" title="您确定删除这条数据吗？" @confirm="del(scope.row)">
              <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>

<!--    分页行 -->
    <div style="padding: 10px 0;">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15,20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          style="background-color: rgba(255,255,255,0.7);"
      >
      </el-pagination>
    </div>

<!--    用户新增、修改，同个表单-->
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" center>
      <el-form :model="form" :rules="rules" ref="userForm" label-width="auto" size="small">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" :rules="[{required: pwRequired, message: '请输入密码', trigger: 'blur'}]">
          <el-input v-model="form.password" show-password placeholder="请输入密码（若无需修改，则不填）"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入昵称"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="用户身份" prop="identity">
          <el-select v-model="form.identity" placeholder="请选择用户身份"  :disabled="identityDisabled">
            <el-option v-for="item in identityList" :key="item.identity" :label="item.identity"
                       :value="item.identity">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属组织" prop="organization">
          <el-select v-model="form.organization" :disabled="orgDisabled" placeholder="请选择社团组织">
            <el-option v-for="item in orgList" :key="item.organization" :label="item.organization"
                       :value="item.organization" >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="medium"  @click="cancelForm" style="width: 47%">取 消</el-button>
        <el-button size="medium" type="primary" @click="saveForm()" style="width: 47%">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "UserAdmin",
  data() {
    const checkOrg = (rule, value, callback) => {
      if (value === '无' &&　this.form.identity !== "普通用户") {
        callback(new Error('请选择社团组织'));
      } else if (value !== "无" && this.form.identity === "普通用户"){
        callback(new Error('普通用户应选择 “无”'));
      } else{
        callback();
      }
    };
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      user_name: "",
      nick_name: "",
      phone_number: "",
      identitySelect: "",
      orgSelect: "",
      pwRequired: true,
      orgDisabled: false,       // 组织选择框可用
      identityDisabled: false,  // 身份选择框可用
      dialogFormVisible: false,   //表单不可见
      identityList: [],
      orgList: [],
      form: {
        userID: "",
        username: "",
        password:"",
        nickname: "",
        phone: "",
        identity: "",
        organization: "",
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
        ],
        password: [
          {
            required: true,
            validator: function(rule, value, callback) {
              if (value == null){     // 用户不更改密码
                callback();
              }else if (/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{3,20}$/.test(value) == false) {   // 用户更改密码
                callback(new Error("请输入3-20位，数字和字母组合"));
              } else {          //校验通过
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        nickname: [
          {
            required: true,
            validator: function(rule, value, callback) {
              if (/^[0-9]*$/.test(value) == true) {
                callback(new Error("请输入中文或字母"));
              } else {          //校验通过
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        phone: [
          {
            required: true,
            validator: function(rule, value, callback) {
              if (/^1[3|4|5|7|8][0-9]{9}$/.test(value) == false) {
                callback(new Error("请输入正确的手机号码"));
              } else {          //校验通过
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        identity: [ {required: true, message: '请选择用户身份', trigger: 'change'} ],
        organization: [ {required: true, validator: checkOrg, trigger: 'change'} ],
      }
    }
  },
  created() {
    this.load()   // 请求查询数据
  },
  methods:{
    load(){
      this.request.get("/user/page", {     //分页查询数据
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.user_name,
          nickname: this.nick_name,
          phone: this.phone_number,
          identity: this.identitySelect,
          organization: this.orgSelect,
        }
      }).then(res => {
        this.tableData = res.data
        this.total = res.total-1
      })
      this.request.get("/user/identity").then(res =>{  // 查询用户身份数据
        res = res.filter(item => item.identity !== "系统管理员")   // 过滤
        this.identityList = res
      })
      this.request.get("/user/org").then(res =>{    // 查询社团
        res = res.filter(item => item.organization !== "")
        this.orgList = res
      })
    },
    reset(){
      this.user_name = ""
      this.nick_name = ""
      this.phone_number = ""
      this.orgSelect = ""
      this.identitySelect = ""
      this.load()
    },
    del(row) {
      this.request.delete("/user/" + row.userID).then(res => {
        if (res) {
          this.$message.success("删除成功")
          this.load()
          // window.location.reload()      // 刷新页面
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    saveForm() {              //新增数据或修改更新
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          this.request.post("/user/save", this.form).then(res =>{
            if (res){
              this.$message.success("保存成功")
              this.dialogFormVisible = false
              this.load()
            } else {
              this.$message.error("保存失败，用户名重复")
              return false;
            }
          })
        }
      });
    },
    cancelForm(){
      this.load()
      this.dialogFormVisible = false
      this.form = []
      this.orgDisabled = false
      this.load()
    },
    handleAdd(){      //添加新用户
      this.dialogFormVisible = true
      this.form = {}
      this.pwRequired = true
      this.orgDisabled = false
      this.identityDisabled = false
    },
    handleEdit(row){
      this.dialogFormVisible = true
      this.form = row
      this.pwRequired = false
      if (row.identity === "社团管理员"){
        console.log(row.identity)
        this.orgDisabled = true
        this.identityDisabled = true
      }else{
        this.orgDisabled = false
        this.identityDisabled = false
      }
    },

    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    }
  }
}
</script>

<style scoped>
.headerBg {
  background: #eee!important;
}
/deep/.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 250px;
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