<template>
  <div style="margin: 0 10px">
    <div style="margin: 10px 0 15px 0;display: flex;justify-content: center;align-items:center;">
      <template>
        <el-input style="width: 200px;font-size: 13px" placeholder="请输入活动名称" suffix-icon="el-icon-search" v-model="name"></el-input>
        <el-input style="width: 200px;margin-left: 20px;font-size: 13px" placeholder="请输入社团名称" suffix-icon="el-icon-search" v-model="organizer"></el-input>
        <el-input style="width: 200px;margin-left: 20px;font-size: 13px" placeholder="请输入地点" suffix-icon="el-icon-search" v-model="address"></el-input>
      </template>
      <el-button style="margin-left: 20px;font-size: 13px" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 20px;font-size: 13px"type="warning" @click="reset">重置</el-button>
      <el-button type="primary" @click="addActivity()" style="font-size: 13px;margin-left: 20px">
        新增活动 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
    </div>
    <div  style="margin: 10px 0; text-align: center">
      <span style="margin: 5px 0 0 10px; font-size: 13px; color: white">一共查询到{{tableData.length}}条活动数据</span>
    </div>

    <div v-if="tableData.length!=0">
      <el-row :gutter="40" >
        <!--        span规定一个col占据24份中的多少份.-->
        <el-col :span="19" v-for="item in tableData" style="margin: 10px 130px; ">
          <el-card shadow="hover" style="background-color: rgba(255, 255, 255, 0); color: white;font-size: 16px">
            <el-row >
              <div>
                <b style="font-size: 20px">{{ item.name }}</b>
                <el-popconfirm class="ml-5" confirm-button-text='确定删除' cancel-button-text='取消'
                               icon="el-icon-info" icon-color="red" title="您确定删除该活动吗？" @confirm="del(item.activityID)">
                  <el-button style="float:right;font-size: 14px;" type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
                </el-popconfirm>
                <el-popconfirm v-if="item.endStatus != '活动已结束'"  class="ml-5" confirm-button-text='确定' cancel-button-text='取消' icon="el-icon-info"
                               icon-color="red" title="您确定结束该活动吗？" @confirm="ending(item)">
                  <el-button style="float:right;font-size: 14px;margin-right: 10px" type="warning" slot="reference">结束活动</el-button>
                </el-popconfirm>
                <el-button type="success" @click="alter(item)" style="float:right;font-size: 14px;margin-right: 10px">修改<i class="el-icon-edit"></i></el-button>
              </div>
              <el-divider></el-divider>
              <p>活动时间：{{ item.time }}</p>
              <p style="margin-top: 10px;">活动主办方：{{ item.organizer }}</p>
              <p style="margin-top: 10px;">活动地址：{{ item.address }}</p>
              <p v-if="item.endStatus == '活动已结束'" style="margin-top: 10px;color: red"><b>活动状态：{{ item.endStatus }}</b></p>
              <p v-else style="margin-top: 10px;color: blue"><b>活动状态：{{ item.endStatus }}</b></p>
              <p style="margin-top: 10px;line-height: 150%">活动详情：{{ item.detail }}</p>
            </el-row>
          </el-card>
        </el-col>
      </el-row>
    </div>

<!--    活动增加、修改，同个表单-->
    <el-dialog title="发布活动" :visible.sync="dialogFormVisible" width="30%" center>
      <el-form :model="form" :rules="rules" ref="form" label-width="auto" size="small">
        <el-form-item label="活动时间" prop="time">
          <el-date-picker v-model="form.time" type="date" placeholder="选择日期" :picker-options="pickerOptions"
                          style="width: 100%" value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="活动名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入活动名称" ></el-input>
        </el-form-item>
        <el-form-item label="主办方" prop="organizer">
          <el-select v-model="form.organizer" placeholder="请选择社团组织">
            <el-option v-for="item in orgList" :key="item.organization"
                       :label="item.organization" :value="item.organization" >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="活动地点" prop="address">
          <el-input v-model="form.address" placeholder="请输入活动地点"></el-input>
        </el-form-item>
        <el-form-item label="活动详情" prop="detail">
          <el-input v-model="form.detail" type="textarea" :autosize="{minRows: 2}" placeholder="请输入活动详情"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="medium" @click="cancelForm" style="width: 47%">取 消</el-button>
        <el-button size="medium" type="primary" @click="saveForm" style="width: 47%">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

export default {
  name: "activityAdmin",
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
      tableData: [],
      name: '',
      organizer: '',
      address: '',
      orgList: [],
      dialogFormVisible: false,   // 活动表单是否可见
      form: {},
      rules: {
        time: [ {required: true, message: '请选择日期', trigger: 'blur'} ],
        name: [
          {required: true,  message: '请输入活动名称', trigger: 'blur'},
          {validator: checkChinese, trigger: "blur"}
        ],
        organizer:[
          {required: true, message: '请选择社团', trigger: 'change'}
        ],
        address: [
          {required: true, message: '请输入活动地点', trigger: 'blur'},
          {validator: checkChinese, trigger: "blur"}
        ],
        detail: [
          {required: true, message: '请输入活动详情', trigger: 'blur'},
          {validator: checkChinese, trigger: "blur"}
        ],
      },
      pickerOptions: {
        disabledDate(time) {    // 已经过去的时间不可选
          return time.getTime() < Date.now();
        }
      },


    }
  },
  created() {   // 请求分页查询数据
    this.load()
  },
  methods:{
    load(){
      // 用axios请求数据
      this.request.get("/activity/page",{
        params: {
          name: this.name,
          organizer: this.organizer,
          address: this.address,
        }
      }).then(res => {
        this.tableData = res.data
        this.total = res.total
      })

      this.request.get("/user/org").then(res =>{    // 查询社团列表
        res = res.filter(item => item.organization !== "" && item.organization !=="无")
        this.orgList = res
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    reset(){
      this.name = ""
      this.organizer = ""
      this.address = ""
      this.load()
    },
    cancelForm(){
      this.form = {}
      this.load()
      this.dialogFormVisible = false
    },
    saveForm(){
      this.$refs['form'].validate((valid) => {
        if (valid){
          this.request.post("/activity/save",this.form).then(res =>{
            if (res){
              this.$message.success("修改成功")
              this.dialogFormVisible = false
              this.load()
              // window.location.reload()      // 刷新页面
            }
          })
        }
      });
    },
    addActivity(){
      this.dialogFormVisible = true
      this.form = {}
    },
    alter(row){
      this.dialogFormVisible = true
      this.form = row
    },
    del(id) {
      console.log(id)
      this.request.delete("/activity/" + id).then(res =>{
        if (res){
          this.$message.success("删除成功")
          this.load()
          // window.location.reload()      // 刷新页面
        }
      })
    },
    ending(row) {
      if (row.endStatus == "活动已结束"){
        this.$message.error("操作失败，活动已是结束状态")
      } else {
        this.request.post("/activity/ending/"+row.activityID).then(res =>{
          if (res){
            this.$message.success("修改成功")
            this.load()
          } else {
            this.$message.error("当前活动没有人报名，无法结束活动，您可以选择删除活动")
          }
        })
      }
    },
  }
}
</script>


<style scoped>
.headerBg {
  background: #eee!important;
}
</style>