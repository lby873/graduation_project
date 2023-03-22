<template>
  <div class="building">
    <div style="margin: 200px auto; background-color: #fff; width: 450px; height: 300px; padding: 20px; border-radius: 15px">
      <div style="margin: 10px 0 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-form :model="user" :rules="rules_login" ref="userLogin" label-width="80px" size="small" style="padding-right: 20px">
        <el-form-item prop="username" label="用户名">
          <el-input size="medium" prefix-icon="el-icon-user"
                    v-model="user.username" placeholder="请输入用户名">
          </el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input size="medium" prefix-icon="el-icon-lock" show-password
                    v-model="user.password" placeholder="请输入密码">
          </el-input>
        </el-form-item>
        <el-form-item label="用户身份" prop="identity">
          <el-select v-model="user.identity" placeholder="请选择用户身份" >
            <el-option v-for="item in identityList" :key="item.identity"
                       :label="item.identity" :value="item.identity" >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="warning" size="medium" @click="register">注册</el-button>
          <el-button type="primary" size="medium" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-dialog title="用户注册" :visible.sync="dialogFormVisible" width="30%" :append-to-body="true">
      <el-form :model="form" :rules="rules_register" ref="userRegister" label-width="auto" size="medium">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" show-password placeholder="请输入密码"></el-input>
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
      <div slot="footer" class="dialog-footer">
        <el-button size="medium" @click="cancelForm">取 消</el-button>
        <el-button size="medium" type="primary" @click="saveForm()">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {},
      identityList: [],
      dialogFormVisible: false,   //表单是否可见
      form: {
        username: "",
        password: "",
        nickname: "",
        phone: "",
        identity: '普通用户',
        organization:'无',
      },
      // 注册规则
      rules_register: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur'},
          {
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
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur'},
          {
            validator: function(rule, value, callback) {
              if (/^[0-9]*$/.test(value) == true) {     // 如果全是数字就报错
                callback(new Error("请输入中文或字母"));
              } else {          //校验通过
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        phone: [
          { required: true, message:'请输入手机号码',trigger: 'blur'} ,
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
      // 登录规则
      rules_login: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 20 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
        ],
        identity: [
          {required: true, message: '请选择用户身份', trigger: 'change'}
        ]
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load(){
      this.request.get("/user/identity").then(list =>{    // 查询身份数据
        this.identityList = list
      })
    },
    // 登录检验
    login() {
      this.$refs['userLogin'].validate((valid) => {
        if (valid) {      // 表单校验合法
          this.request.post("/user/login", this.user).then(res => {
            if(res.code == "1") {           // 用户名不存在
              this.$message.error("用户名不存在，请注册")
            } else if(res.code == "2") {           // 密码错误
              this.$message.error("密码错误")
            } else if(res.code == "3") {           // 用户身份错误
              this.$message.error("用户身份错误")
            } else {
              this.$message.success("恭喜您，登录成功")
              localStorage.setItem("userLogin", JSON.stringify(res.data))  // 存储用户信息到浏览器（把对象转为json存储）
              if (res.data.identity == "社团管理员"){   // 社团管理员，则跳转到发布活动页面
                this.$router.push("/addActivity")
              } else {                                // 普通用户、社团成员，跳转到活动首页
                this.$router.push("/home")
              }
            }
          })
        }// if(valid)
      });
    }, // login()

    // 注册检验
    register(){             //注册新用户
      this.dialogFormVisible = true
      this.form = {}
      this.form.identity = "普通用户"
      this.form.organization = "无"
    },

    saveForm(){
      this.$refs['userRegister'].validate((valid) => {
        if (valid) {      // 表单校验合法
          this.request.post("/user/register",this.form).then(res =>{
            if (res){    // 数据库没有相同的用户名
              this.$message.success("恭喜您，注册成功，请登录")
              this.dialogFormVisible = false
            } else {    // 存在相同用户名
              this.$message.error("用户名已存在，注册失败")
            }
          })
        }// if(valid)
      });
    },
    cancelForm(){
      this.dialogFormVisible = false
      this.form = {}
      this.form.identity = "普通用户"
    },

  }
}
</script>

<style scoped>
body{
  margin:0;
  padding:0;
  border:0
}
.building{
  background-image:url("@/assets/bg.jpg");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
  background-repeat: no-repeat;
}
</style>
