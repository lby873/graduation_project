<template>
  <div>
<!--    前端对contentStyle和labelStyle报错，未解决    -->
    <el-descriptions class="margin-top" :column="1" size="large" border contentStyle="text-align:center;"
                     labelStyle="text-align:center; max-width:50px; font-weight:bold" >
      <template slot="title" >
        <span style="font-size: 24px;">个人信息</span>
      </template>
      <template slot="extra" >
        <el-button type="primary" size="medium" @click="memberDialogVisible = true" style="font-size: 14px;" v-show="memberBtnVisible">
          查看社团成员
        </el-button>
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
        <el-button size="mini" type="primary" @click="pwDialogVisible = true" style="font-size: 14px;">修改密码</el-button>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label"><i class="el-icon-school"></i> 所在社团组织</template>
        <el-tag size="medium"><b style="font-size: 16px;">{{ userLogin.organization }}</b></el-tag>
        <el-button type="primary" style="margin-left: 10px;" v-show="sumBtnVisible" @click="orgDialogVisible=true">简介</el-button>
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
            <el-input v-model="form.phone" placeholder="请输入电话"></el-input>
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

<!--    新密码提交表单-->
    <el-dialog title="修改密码" :visible.sync="pwDialogVisible" width="30%" center>
      <el-form :model="pwForm" :rules="rulesPw" ref="pwForm" label-width="auto" size="medium" >
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input v-model="pwForm.oldPassword" show-password placeholder="请输入旧密码"/>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="pwForm.newPassword" show-password placeholder="请输入新密码"/>
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

<!--    社团成员列表-->
    <el-dialog title="社团成员信息" :visible.sync="memberDialogVisible" width="65%" center>
      <el-table :data="memberTableData" border stripe header-cell-class-name="headerBg">
        <el-table-column prop="username" label="用户名" ></el-table-column>
        <el-table-column prop="nickname" label="昵称"></el-table-column>
        <el-table-column prop="phone" label="手机号码"></el-table-column>
        <el-table-column prop="identity" label="用户身份"></el-table-column>
        <el-table-column prop="organization" label="所属社团"></el-table-column>
<!--        <el-table-column label="操作"  width="200" align="center" v-if="alterIdentityVisible">-->
<!--          &lt;!&ndash;v-if 设置是否可见&ndash;&gt;-->
<!--          <template slot-scope="scope">-->
<!--            <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='取消'-->
<!--                           icon="el-icon-info" icon-color="red" title="您确定将该成员修改为普通用户吗？"-->
<!--                           @confirm="alterIdentity(scope.row.userID)">-->
<!--              <el-button type="danger" slot="reference">修改为普通用户<i class="el-icon-remove-outline"></i></el-button>-->
<!--            </el-popconfirm>-->
<!--          </template>-->
<!--        </el-table-column>-->
      </el-table>
    </el-dialog>

<!--    社团简介表-->
    <el-dialog title="社团信息" :visible.sync="orgDialogVisible" width="65%" center>
      <el-table :data="orgTableData" border stripe header-cell-class-name="headerBg">
        <el-table-column prop="orgID" label="ID" width="60" align="center"></el-table-column>
        <el-table-column prop="orgName" label="名称" width="100" align="center"></el-table-column>
        <el-table-column prop="orgSummary" label="简介"></el-table-column>
        <el-table-column prop="orgCreatedDate" label="创建时间" width="100" align="center"></el-table-column>
        <el-table-column prop="orgAdminID" label="管理员ID" width="100" align="center"></el-table-column>
        <el-table-column prop="orgAdminName" label="管理员用户名" width="100" align="center"></el-table-column>
        <el-table-column label="操作" width="100" align="center">
          <template slot-scope="scope">
            <el-button type="success" @click="orgFormVisible = true" >修改<i class="el-icon-edit"></i></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

<!--    修改社团信息表单-->
    <el-dialog title="修改社团信息" :visible.sync="orgFormVisible" width="30%" center>
      <el-form :model="orgForm" :rules="rulesOrg" ref="orgForm" label-width="auto" size="small">
        <el-form-item label="社团创建时间" prop="orgCreatedDate">
          <el-date-picker v-model="orgForm.orgCreatedDate" type="date" placeholder="选择日期" style="width: 100%"
                          :picker-options="pickerOptions" value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="社团名称" prop="orgName">
          <el-input v-model="orgForm.orgName" placeholder="请输入社团名称" ></el-input>
        </el-form-item>
        <el-form-item label="社团概要" prop="orgSummary">
          <el-input v-model="orgForm.orgSummary" type="textarea" :autosize="{minRows:2, maxRows:4}" placeholder="请输入社团概要"></el-input>
        </el-form-item>
        <el-form-item label="社团管理员ID" prop="orgAdminID">
          <el-input v-model="orgForm.orgAdminID" placeholder="请输入社团管理员ID"></el-input>
        </el-form-item>
        <el-form-item label="社团管理员用户名" prop="orgAdminName">
          <el-input v-model="orgForm.orgAdminName"  placeholder="请输入社团管理员用户名"></el-input>
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
      return {
        userLogin: JSON.parse(localStorage.getItem("userLogin")),   //json转化为对象
        memberTableData: [],
        orgTableData: {},
        sumBtnVisible: false,       // 简介按钮
        orgDialogVisible:false,     // 简介框
        memberBtnVisible: true,      // 社团成员按钮
        memberDialogVisible: false,   // 社团成员框
        dialogVisible: false,      // 个人信息提交确认框
        drawerVisible: false,      // 个人信息表单
        pwDialogVisible: false,    // 密码修改对话框
        orgFormVisible: false,     // 修改社团信息框
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
          orgAdminID: [ {required: true, message: '请输入社团管理员ID', trigger: 'blur'} ],
          orgAdminName: [
            {required: true, message: '请输入社团管理员用户名', trigger: 'blur'},
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
        this.form = this.userLogin    // 抽屉内容初始化
        this.userPwd.userID = this.userLogin.userID
        this.userPwd.username = this.userLogin.username
        this.userPwd.identity = this.userLogin.identity

        if (this.userLogin.identity === "普通用户"){    // 判断用户身份，两个按钮是否可见
          this.memberBtnVisible = false;
          this.sumBtnVisible = false;
        } else {
          this.memberBtnVisible = true;
          this.sumBtnVisible = true;
        }

        // 获取成员信息列表
        this.request.get("/user/findMember",{
          params: {
            organization: this.userLogin.organization,
          }
        }).then(res => {
          this.memberTableData = res.data
        })

        // 获取社团信息
        if (this.userLogin.organization !== "无"){  // 避免出现查询结果为 undefined
          this.request.get("/org/findOrgMsg",{
            params: {
              orgName: this.userLogin.organization
            }
          }).then(res => {
            this.orgTableData = res.data    // 把社团信息放到社团简介表
            this.orgForm = res.data[0]      // 把社团信息放进社团信息修改框
          })
        }

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
              if (res.userID == null){     // 旧密码验证不正确
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
            this.request.post("/org/save",this.form).then(res =>{
              if (res){
                this.$message.success("修改成功")
                this.orgFormVisible = false
              }
            })
          }
        });
      },

      alterIdentity(userID){
        this.request.post("/user/alterIdentity/" + userID).then(res => {
          if (res) {
            this.$message.success("修改成功")
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