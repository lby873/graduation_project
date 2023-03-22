<template>
  <div>
<!--    前端对contentStyle和labelStyle报错，未解决    -->
    <el-descriptions class="margin-top" :column="1" size="large" border contentStyle="text-align:center;"
                     labelStyle="text-align:center; max-width:50px; font-weight:bold" >
      <template slot="title" >
        <span style="font-size: 24px;">个人信息</span>
      </template>
      <template slot="extra" >
        <el-button type="primary" size="medium" @click="drawerVisible = true">修改个人信息</el-button>
      </template>

      <el-descriptions-item>
        <template slot="label"><i class="el-icon-cpu"></i> 用户ID</template>
        {{ userLogin.userID }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label"><i class="el-icon-user"></i> 用户名</template>
        {{ userLogin.username }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label"><i class="el-icon-user-solid"></i> 用户昵称</template>
        {{ userLogin.nickname }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label"><i class="el-icon-mobile-phone"></i> 手机号</template>
        {{ userLogin.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label"><i class="el-icon-location-outline"></i> 用户身份</template>
        {{ userLogin.identity }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label"><i class="el-icon-edit"></i> 用户密码</template>
        <el-button size="mini" type="danger" @click="pwDialogVisible = true" style="font-size: 14px;">修改密码</el-button>
      </el-descriptions-item>
      <el-descriptions-item v-if="orgVisible">
        <template slot="label"><i class="el-icon-school"></i> 所在社团组织</template>
        <el-button type="primary" style="text-align: center;font-size: 14px;" @click="orgDialogVisible=true">{{ userLogin.organization }}</el-button>
      </el-descriptions-item>

    </el-descriptions>

<!--    抽屉个人信息表单-->
    <el-drawer :before-close="handleClose" :visible.sync="drawerVisible" direction="rtl" size="35%">
      <template slot="title" >
        <b style="font-size: 20px;">修改个人信息</b>
      </template>
      <div class="demo-drawer__content" style="padding: 0 30px">
        <el-form :model="form" :rules="rules" ref="user" label-width="auto" size="medium">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="form.nickname" placeholder="请输入昵称"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入手机号"></el-input>
          </el-form-item>
          <el-form-item label="用户身份">
            <el-input v-model="form.identity" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="所在社团组织">
            <el-input v-model="form.organization" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="是否退出所在社团" v-if="quitOrgVisible">
            <el-radio-group v-model="radioQuit">
              <el-radio label="1" @change="quitOrg" border> 是 </el-radio>
              <el-radio label="0" style="margin-left: 5px" @change="quitOrg" border> 否 </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>
      <div class="demo-drawer__footer" style="align-items: center; display: flex; justify-content:center">
        <el-button size="medium" @click="handleClose" style="width: 44%">取 消</el-button>
        <el-button size="medium" type="primary" @click="dialogVisible = true" style="width: 44%">确 定</el-button>
      </div>

    </el-drawer>

<!--    新密码提交表单-->
    <el-dialog title="修改密码" :visible.sync="pwDialogVisible" width="30%" center>
      <el-form :model="pwForm" :rules="rulesPw" ref="pwForm" label-width="auto" size="medium" >
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input v-model="pwForm.oldPassword" show-password placeholder="请输入旧密码"/>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="pwForm.newPassword" show-password placeholder="请输入新密码"/>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword">
          <el-input v-model="pwForm.checkPassword" show-password placeholder="请再次输入密码"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button size="medium" @click="cancelPwForm" style="width: 47%">取 消</el-button>
          <el-button size="medium" type="primary" @click="savePwForm" style="width: 47%">确 定</el-button>
      </span>
    </el-dialog>

<!--    个人信息提交确认框-->
    <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" center>
      <div style="font-size: 16px;text-align: center">您确认要修改信息吗?</div>
      <span slot="footer" class="dialog-footer">
          <el-button size="medium" @click="dialogVisible = false">取 消</el-button>
          <el-button size="medium" type="primary" @click="saveForm">确 定</el-button>
      </span>
    </el-dialog>

<!--    社团简介表-->
    <el-dialog title="社团信息" :visible.sync="orgDialogVisible" width="65%" center>
      <el-table :data="orgTableData" border stripe header-cell-class-name="headerBg">
        <el-table-column prop="orgID" label="社团ID" width="80" align="center"></el-table-column>
        <el-table-column prop="orgName" label="名称" width="100" align="center"></el-table-column>
        <el-table-column prop="orgSummary" label="简介"></el-table-column>
        <el-table-column prop="orgCreatedDate" label="创建时间" width="100" align="center"></el-table-column>
        <el-table-column prop="orgAdminID" label="管理员ID" width="100" align="center"></el-table-column>
        <el-table-column label="操作" width="100" align="center" v-if="alterOrgDetailVisible">
          <template slot-scope="scope">
            <el-button type="success" @click="orgFormVisible = true" >修改<i class="el-icon-edit"></i></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

<!--    修改社团信息表单-->
    <el-dialog title="修改社团信息" :visible.sync="orgFormVisible" width="30%" center>
      <el-form :model="orgForm" :rules="rulesOrg" ref="orgForm" label-width="auto" size="small">
        <el-form-item label="社团名称" prop="orgName">
          <el-input v-model="orgForm.orgName" placeholder="请输入社团名称" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="社团简介" prop="orgSummary">
          <el-input v-model="orgForm.orgSummary" type="textarea" :autosize="{minRows:2, maxRows:4}" placeholder="请输入社团概要"></el-input>
        </el-form-item>
        <el-form-item label="社团创建时间" prop="orgCreatedDate">
          <el-date-picker v-model="orgForm.orgCreatedDate" type="date" placeholder="选择日期" style="width: 100%"
                          :picker-options="pickerOptions" value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="社团管理员ID">
          <el-input v-model="orgForm.orgAdminID" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="medium" @click="cancelOrgForm" style="width: 47%">取 消</el-button>
        <el-button size="medium" type="primary" @click="saveOrgForm" style="width: 47%">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    name: "PersonMsg",
    data(){
      const checkChinese = (rule, value, callback) => {
        if (/^[0-9]*$/.test(value) == true) {       // 如果全是数字就报错
          callback(new Error("请输入中文或字母或数字的组合"));
        } else {          //校验通过
          callback();
        }
      };
      const validatorPw = (rule, value, callback) => {
        if (/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{3,20}$/.test(value) == false) {
          callback(new Error("请输入3-20位，数字和字母组合"));
        } else {          //校验通过
          callback();
        }
      };
      const checkPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.pwForm.newPassword) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        userLogin: JSON.parse(localStorage.getItem("userLogin")),   //json转化为对象
        memberTableData: [],
        orgTableData: {},
        orgVisible: true,          // 个人信息中，社团一行
        orgDialogVisible:false,     // 简介框
        dialogVisible: false,      // 个人信息提交确认框
        drawerVisible: false,      // 个人信息表单
        pwDialogVisible: false,    // 密码修改对话框
        orgFormVisible: false,     // 修改社团信息框
        alterOrgDetailVisible: false,    // 修改社团信息按钮
        quitOrgVisible: false,    // 个人信息表单中，退出社团一行
        radioQuit: '0',
        oldOrganization: '',
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          }
        },
        orgForm: {},
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
          checkPassword:'',
        },
        // 修改个人信息规则
        rules: {
          username: [
            { required: true, min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
          ],
          nickname: [
            { required: true, validator: checkChinese, trigger: "blur"}],
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
        },
        // 修改密码规则
        rulesPw:{
          oldPassword: [
            { required: true, validator: validatorPw, trigger: "blur" }
          ],
          newPassword: [
            { required: true, validator: validatorPw, trigger: "blur" }
          ],
          checkPassword:[
            { required: true, validator: checkPassword, trigger: "blur" }
          ],
        },
        // 修改社团信息规则
        rulesOrg: {
          orgCreatedDate: [ {required: true, message: '请选择日期', trigger: 'blur'} ],
          orgName: [
            {required: true,  message: '请输入社团名称', trigger: 'blur'},
            {validator: checkChinese, trigger: "blur"}
          ],
          orgSummary: [
            {required: true, message: '请输入社团概要', trigger: 'blur'},
            {validator: checkChinese, trigger: "blur"}
          ],
        },

      }
    },
    created() {
      this.load()
    },
    methods:{
      load(){
        this.form = this.userLogin            // 抽屉内容初始化
        this.userPwd.userID = this.userLogin.userID
        this.userPwd.username = this.userLogin.username
        this.userPwd.identity = this.userLogin.identity
        this.oldOrganization = this.userLogin.organization    // 保存原社团组织

        if (this.userLogin.identity === "普通用户"){    // 判断用户身份
          this.orgVisible = false;                    // 个人信息中，社团一行不可见
        } else if (this.userLogin.identity === "社团管理员"){
          this.alterOrgDetailVisible = true;          // 修改社团信息按钮可见
        } else {
          this.quitOrgVisible = true;
        }

        // 获取社团信息
        if (this.userLogin.organization !== "无"){  // 避免出现查询结果为 undefined
          this.request.get("/org/findOrgMsg",{
            params: {
              orgName: this.userLogin.organization
            }
          }).then(res => {
            this.orgTableData = res.data    // 把社团信息放到社团简介表
            this.orgForm = res.data[0]         // 把社团信息放进社团信息修改框
          })
        }

      },

      handleClose() {   // 关闭抽屉
        this.drawerVisible = false
        window.location.reload()      // 必须刷新页面
      },

      saveForm(){     // 提交抽屉个人信息表单
        this.$refs['user'].validate((valid) => {
          if (valid) {      // 表单校验合法
            this.request.post("/user/save",this.form).then(res =>{
              if (res){    // 用户名不重复，或没有修改
                this.$message.success("恭喜您，修改成功")
                localStorage.setItem("userLogin", JSON.stringify(this.form))  // 重新存储用户信息到浏览器
                this.drawerVisible = false
                this.dialogVisible = false
                window.location.reload()      // 必须刷新页面
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
              if (res.code == "2"){     // 旧密码验证不正确
                this.$message.error("旧密码错误，修改失败")
              } else {    // 旧密码验证通过
                this.userPwd.password = this.pwForm.newPassword     // 提交新密码
                this.request.post("/user/save",this.userPwd).then(res =>{
                  if (res){
                    this.$message.success("恭喜您，修改成功")
                    this.pwDialogVisible = false
                    this.pwForm={}
                  }
                })
              }
            })
          }// if(valid)
        });
      },

      cancelPwForm(){
        this.pwDialogVisible = false
        this.pwForm={}
      },

      cancelOrgForm(){
        this.orgForm = {}
        this.load()
        this.orgFormVisible = false
      },

      saveOrgForm(){    // 提交修改社团信息表单
        this.$refs['orgForm'].validate((valid) => {
          if (valid){
            this.request.post("/org/save",this.orgForm).then(res =>{
              if (res == "0"){
                this.$message.success("修改成功")
                this.orgFormVisible = false
              }else{
                this.$message.error("社团名称重复")
              }
            })
          }
        });
      },
      quitOrg(){     // 更新表单内容
        if (this.radioQuit == "1"){
          console.log(this.radioQuit)
          this.form.identity = "普通用户"
          this.form.organization = "无"
        } else {
          console.log(this.oldOrganization)
          this.form.identity = "社团成员"
          this.form.organization = this.oldOrganization
        }
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