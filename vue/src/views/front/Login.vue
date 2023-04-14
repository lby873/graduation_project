<template>
  <div class="building" style="display: flex;justify-content: center;align-items:center;">
    <div class="form" v-show="loginFormVisible">
      <div style="margin: 10px 0 20px 0; font-size: 24px;display: flex;justify-content: center;align-items:center;">
        <img src="../../assets/logo.png" style="width: 40px; margin-right: 10px">
        <b>欢 迎 登 录</b>
      </div>
      <el-form :model="user" :rules="rules_login" ref="userLogin" label-width="100px" size="medium" style="padding-right: 20px">
        <el-form-item prop="username">
          <span slot="label" style="color: #eeeeee">用户名</span>
          <el-input size="medium" prefix-icon="el-icon-user-solid"
                    v-model="user.username" placeholder="请输入用户名">
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <span slot="label" style="color: #eeeeee"  class="label_input">密码</span>
          <el-input size="medium" prefix-icon="el-icon-lock" show-password
                    v-model="user.password" placeholder="请输入密码">
          </el-input>
        </el-form-item>
        <el-form-item  prop="identity">
          <span slot="label" style="color: #eeeeee">用户身份</span>
          <el-select v-model="user.identity" placeholder="请选择用户身份">
            <el-option v-for="item in identityList" :key="item.identity"
                       :label="item.identity" :value="item.identity" >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" style="margin-top: 30px;">
        <el-link target="_blank" style="float:left;margin: 15px 0 0 25px;font-size: 13px;color: #cccccc" @click="forgetPw">忘记密码</el-link>
        <el-button class="btn" style="margin: 0 15px;float:right;" size="large" @click="register">注 册</el-button>
        <el-button class="btn" style="float:right;" size="large" @click="login">登 录</el-button>
      </div>
    </div>

<!--    注册页面-->
    <div class="form" width="30%" style="height: 380px" v-show="registerFormVisible">
      <div style="margin: 10px 0 20px 0; text-align: center; font-size: 24px"><b>用 户 注 册</b></div>
      <el-form :model="form" :rules="rules_register" ref="userRegister" label-width="80px" size="medium" style="padding-right: 20px">
        <el-form-item prop="username">
          <span slot="label" style="color: #eeeeee">用户名</span>
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="el-icon-user-solid"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <span slot="label" style="color: #eeeeee" >密码</span>
          <el-input v-model="form.password" show-password placeholder="请输入密码" prefix-icon="el-icon-lock" ></el-input>
        </el-form-item>
        <el-form-item prop="nickname">
          <span slot="label" style="color: #eeeeee">昵称</span>
          <el-input v-model="form.nickname" placeholder="请输入昵称" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item prop="phone">
          <span slot="label" style="color: #eeeeee">电话</span>
          <el-input v-model="form.phone" placeholder="请输入电话" prefix-icon="el-icon-phone-outline"></el-input>
        </el-form-item>
<!--        <el-form-item>-->
<!--          <span slot="label" style="color: #eeeeee">用户身份</span>-->
<!--          <el-input v-model="form.identity" :disabled="true"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item>-->
<!--          <span slot="label" style="color: #eeeeee">社团组织</span>-->
<!--          <el-input v-model="form.organization" :disabled="true"></el-input>-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" style="margin-top: 10px;">
        <el-button class="btn" style="margin-left: 20%;" size="large" @click="cancelForm">取 消</el-button>
        <el-button class="btn" style="margin-left: 20%;"size="large" @click="saveForm()">确 定</el-button>
      </div>
    </div>

    <!--背景视频-->
<!--    <div class="videoContainer">-->
<!--      <video class="fullscreenVideo" id="bgVid" playsinline="" autoplay="" muted="" loop="">-->
<!--        <source src="../../assets/bgVideo.mp4" type="video/mp4">-->
<!--      </video>-->
<!--    </div>-->

  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {
        uid:1223,
        pa:2222,
      },
      identityList: [],
      registerFormVisible: false,   //表单是否可见
      loginFormVisible:true,
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

              // 根据不同身份跳转页面
              if (res.data.identity == "社团管理员"){   // 社团管理员，则跳转到发布活动页面
                this.$router.push("/addActivity")
              } else if (res.data.identity == "系统管理员"){   // 系统管理员，跳转到后台系统
                this.$router.push("/admin")
              }else{                   // 普通用户、社团成员，跳转到活动首页
                this.$router.push("/home")
              }
            }
          })
        }// if(valid)
      });
    }, // login()

    // 注册检验
    register(){             //注册新用户
      this.registerFormVisible = true
      this.loginFormVisible = false
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
      this.registerFormVisible = false
      this.loginFormVisible = true
      this.form = {}
    },
    forgetPw(){
      this.$message({
        message: '请联系系统管理员',
        center: true
      });

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
  /*background-image:url("@/assets/bg.jpg");*/
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
  background-repeat: no-repeat;
}
.form{
  background-color: rgba(255,255,255,0.3);
  color: white;
  width: 400px;
  height: 350px;
  padding: 20px;
  border-radius: 15px;
}
.btn{
  background: none;
  border-radius: 15px;
  color: #cccccc;
}
/deep/.el-input__inner{
  font-size: 14px;
  border: none;
  border-bottom: 1px solid #eeeeee;
  background-color: rgba(255, 255, 255, 0);
  color: #eeeeee;
}
span{
  font-size: 16px;
}
</style>
