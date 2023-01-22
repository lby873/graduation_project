<template>
  <div>
<!--    前端对contentStyle和labelStyle报错，未解决    -->
    <el-descriptions class="margin-top" :column="2" size="large" border contentStyle="text-align:center;"
                     labelStyle="text-align:center;max-width: 100px;font-weight:bold" >
      <template slot="title" >
        <span style="font-size: 24px;">个人信息</span>
      </template>
      <template slot="extra" >
        <el-button type="primary" size="medium" @click="drawerVisible = true">修改</el-button>
      </template>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-cpu"></i> 用户ID
        </template>
        {{ userLogin.userID }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i> 用户名
        </template>
        {{ userLogin.username }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user-solid"></i> 用户昵称
        </template>
        {{ userLogin.nickname }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i> 手机号
        </template>
        {{ userLogin.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i> 用户身份
        </template>
        {{ userLogin.identity }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-edit"></i> 用户密码
        </template>
        <el-button size="mini" @click="pwDialogVisible = true"><b style="font-size: 14px;">点击修改密码</b></el-button>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-school"></i> 所在社团组织
        </template>
        <el-tag size="medium"><b style="font-size: 14px;">{{ userLogin.organization }}</b></el-tag>
      </el-descriptions-item>


    </el-descriptions>

    <el-drawer :before-close="handleClose" :visible.sync="drawerVisible" direction="rtl" size="35%">
      <template slot="title" >
        <b style="font-size: 20px;">修改个人信息</b>
      </template>
      <div class="demo-drawer__content" style="padding: 0 30px">
        <el-form :model="form" :rules="rules" ref="user" label-width="auto" size="medium">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username"></el-input>
          </el-form-item>
<!--          <el-form-item label="密码" prop="password">-->
<!--            <el-input v-model="form.password" show-password></el-input>-->
<!--          </el-form-item>-->
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="form.nickname"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="form.phone"></el-input>
          </el-form-item>
          <el-form-item label="用户身份">
            <el-input v-model="form.identity" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="所在社团组织">
            <el-input v-model="form.organization" :disabled="true"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="demo-drawer__footer" style="align-items: center; display: flex; justify-content:center">
        <el-button size="medium" @click="handleClose" style="width: 44%">取 消</el-button>
        <el-button size="medium" type="primary" @click="dialogVisible = true" style="width: 44%">确 定</el-button>
      </div>

    </el-drawer>

<!--    提交修改密码表单-->
    <el-dialog title="修改密码" :visible.sync="pwDialogVisible" width="30%" center>
      <el-form :model="pwForm" :rules="rulesPw" ref="pwForm" label-width="auto" size="medium" >
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input v-model="pwForm.oldPassword" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="pwForm.newPassword" show-password />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button size="medium" @click="pwDialogVisible = false" style="width: 47%">取 消</el-button>
          <el-button size="medium" type="primary" @click="savePwForm" style="width: 47%">确 定</el-button>
      </span>
    </el-dialog>

<!--    提交修改信息确认框-->
    <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" center>
      <div style="font-size: 16px;text-align: center">您确认要修改信息吗?</div>
      <span slot="footer" class="dialog-footer">
          <el-button size="medium" @click="dialogVisible = false">取 消</el-button>
          <el-button size="medium" type="primary" @click="saveForm">确 定</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>
  export default {
    name: "Person",
    data(){
      return {
        userLogin: JSON.parse(localStorage.getItem("userLogin")),   //json转化为对象
        dialogVisible: false,
        drawerVisible: false,
        pwDialogVisible: false,    // 密码修改对话框
        userPwd:{         // 实现旧密码验证，新密码提交修改
          userID: '',
          username: '',
          password: '',
          identity:'',
        },
        form: {
          userID:'',
          username: '',
          nickname: '',
          phone: '',
          identity: '',
          organization: '',
        },
        pwForm:{
          oldPassword: '',
          newPassword: '',
        },
        // 修改个人信息规则
        rules: {
          username: [
            {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
          ],
          nickname: [
            {
              validator: function(rule, value, callback) {
                if (/^[0-9]*$/.test(value) == true) {       // 如果全是数字就报错
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
        },
        // 修改密码规则
        rulesPw:{
          oldPassword: [
            {
              required: true,
              validator: function(rule, value, callback) {
                if (/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{3,20}$/.test(value) == false) {
                  callback(new Error("请输入3-20位，数字和字母组合"));
                } else {          //校验通过
                  callback();
                }
              },
              trigger: "blur"
            }
          ],
          newPassword: [
            {
              required: true,
              validator: function(rule, value, callback) {
                if (/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{3,20}$/.test(value) == false) {
                  callback(new Error("请输入3-20位，数字和字母组合"));
                } else {          //校验通过
                  callback();
                }
              },
              trigger: "blur"
            }
          ],
        }

      }
    },
    created() {
      this.load()
    },
    methods:{
      load(){
        this.form = this.userLogin    // 抽屉的内容
        this.userPwd.userID = this.userLogin.userID
        this.userPwd.username = this.userLogin.username
      },
      handleClose() {   // 关闭抽屉
        this.drawerVisible = false
        window.location.reload()      // 刷新页面
      },

      saveForm(){     // 提交抽屉表单
        this.$refs['user'].validate((valid) => {
          if (valid) {      // 表单校验合法
            this.request.post("/user/save",this.form).then(res =>{
              if (res){    // 用户名不重复，或没有修改
                this.$message.success("恭喜您，修改成功")
                localStorage.setItem("userLogin", JSON.stringify(this.form))  // 重新存储用户信息到浏览器
                this.drawerVisible = false
                this.dialogVisible = false
                this.load()
              } else {    // 存在相同用户名
                this.$message.error("用户名已存在，修改失败")
                this.dialogVisible = false
              }
            })
          }// if(valid)
        });
      },

      savePwForm(){     // 提交密码表单
        this.$refs['pwForm'].validate((valid) => {
          if (valid) {      // 表单校验合法
            this.userPwd.password = this.pwForm.oldPassword
            this.request.post("/user/login",this.userPwd).then(res =>{   // 验证账号密码是否正确
              if (res !== null){     // 旧密码验证正确
                this.userPwd.password = this.pwForm.newPassword     // 提交新密码
                this.request.post("/user/save",this.userPwd).then(res =>{
                  if (res){
                    this.$message.success("恭喜您，修改成功")
                    this.pwDialogVisible = false
                  }
                })
              } else {    // 旧密码验证不通过
                this.$message.error("旧密码错误，修改失败")
              }
            })
          }// if(valid)
        });
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