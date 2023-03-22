<template>
  <div>
    <div>
      <el-button type="primary" @click="addOrg()" style="float:right; margin-bottom: 10px">
        新增社团 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
    </div>

    <el-table :data="tableData" border stripe header-cell-class-name="headerBg">
      <el-table-column prop="orgID" label="社团ID" width="80" align="center"></el-table-column>
      <el-table-column prop="orgName" label="社团名称" width="100" align="center"></el-table-column>
      <el-table-column prop="orgCreatedDate" label="社团创建时间" width="100" align="center"></el-table-column>
      <el-table-column prop="orgSummary" label="社团概要"></el-table-column>
      <el-table-column prop="orgAdminID" label="社团管理员ID" width="120" align="center"></el-table-column>
      <el-table-column label="操作"  width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="alter(scope.row)">修改<i class="el-icon-edit"></i></el-button>
          <el-popconfirm confirm-button-text='确定' cancel-button-text='取消' icon="el-icon-info" icon-color="red"
                         title="您确定删除该数据吗？" @confirm="del(scope.row)">
            <el-button style="margin-left: 10px" type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--    社团增加、修改，同个表单-->
    <el-dialog title="社团信息" :visible.sync="dialogFormVisible" width="30%" center>
      <el-form :model="form" :rules="rules" ref="form" label-width="auto" size="small">
        <el-form-item label="社团创建时间" prop="orgCreatedDate">
          <el-date-picker v-model="form.orgCreatedDate" type="date" placeholder="选择日期" style="width: 100%"
                          :picker-options="pickerOptions" value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="社团名称" prop="orgName">
          <el-input v-model="form.orgName" placeholder="请输入社团名称" ></el-input>
        </el-form-item>
        <el-form-item label="社团概要" prop="orgSummary">
          <el-input v-model="form.orgSummary" type="textarea" :autosize="{minRows:2, maxRows:4}" placeholder="请输入社团概要"></el-input>
        </el-form-item>
        <el-form-item label="社团管理员ID" prop="orgAdminID">
          <el-input v-model="form.orgAdminID" placeholder="请输入社团管理员ID"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="medium" @click="cancelForm" style="width: 47%">取 消</el-button>
        <el-button size="medium" type="primary" @click="saveForm" style="width: 47%">确 定</el-button>
      </div>
    </el-dialog>

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
</template>

<script>
import {interceptors} from "axios";

export default {
  name: "OrganizationAdmin",
  data(){
    const checkChinese = (rule, value, callback) => {
      if (/^[0-9]*$/.test(value) == true) {       // 如果全是数字就报错
        callback(new Error("请输入中文或字母或数字的组合"));
      } else {          //校验通过
        callback();
      }
    };
    const checkNumber = (rule, value, callback) => {
      if (/^([1-9][0-9]*){1,10}$/.test(value) == false) {
        callback(new Error("请输入正整数"));
      } else {          //校验通过
        callback();
      }
    };
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      dialogFormVisible: false,
      oldOrgAdminID: '',    // 修改社团管理员时，保存原来的社团管理员ID
      orgNameList: [],      // 记录所有社团名称
      orgAdminIDList: [],     // 记录所有社团管理员ID
      form: {},
      rules: {
        orgCreatedDate: [ {required: true, message: '请选择日期', trigger: 'blur'} ],
        orgName: [
          {required: true,  message: '请输入社团名称', trigger: 'blur'},
          {validator: checkChinese, trigger: "blur"}
        ],
        orgSummary: [
          {required: true, message: '请输入社团概要', trigger: 'blur'},
          {validator: checkChinese, trigger: "blur"}
        ],
        orgAdminID: [
          {required: true, message: '请输入社团管理员ID', trigger: 'blur'},
          {validator: checkNumber, trigger: "blur"}
        ],
      },
      pickerOptions: {
        disabledDate(time) {    // 未来的时间不可选
          return time.getTime() > Date.now();
        }
      }
    }
  },
  created() {   // 请求分页查询数据
    this.load()
  },
  methods:{
    load(){
      // 用axios请求数据
      this.request.get("/org/page",{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.tableData = res.data
        this.total = res.total
        for (let i = 0; i < res.data.length; i++) {
          this.orgNameList[i] = res.data[i].orgName
          this.orgAdminIDList[i] = res.data[i].orgAdminID
        }
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

    addOrg(){
      this.form = {}
      this.dialogFormVisible = true
      this.oldOrgAdminID = 9999   // 9999代表不存在旧管理员
    },
    alter(row){
      this.dialogFormVisible = true
      this.form = row
      this.oldOrgAdminID = row.orgAdminID
    },
    cancelForm(){
      this.form = {}
      this.load()
      this.dialogFormVisible = false
    },
    saveForm(){
      this.$refs['form'].validate((valid) => {
        if (valid){
          this.request.post("/org/save",this.form).then(res =>{
            if (res == "0"){                                      // 修改、插入社团信息成功
              if (this.oldOrgAdminID !== this.form.orgAdminID){   // 修改社团管理员为另一人
                this.request.get("/user/alterAdmin",{
                  params:{
                    oldOrgAdminID: this.oldOrgAdminID,
                    newOrgAdminID: this.form.orgAdminID,
                    orgName: this.form.orgName
                  }
                })
              }
              this.$message.success("修改成功")
              this.dialogFormVisible = false
              this.load()
              // window.location.reload()      // 刷新页面
            } else if (res == "1"){
              this.$message.error("社团名称重复")
            } else if (res == "2"){
              this.$message.error("社团管理员ID重复")
            } else if (res == "3"){
              this.$message.error("社团管理员不存在")
            } else if (res == null){
              this.$message.error("系统繁忙，请稍候重试")
            }
          })

        }
      });
    },
    del(row){
      this.request.delete("/org/"+row.orgAdminID).then(res =>{
        if (res){
          this.$message.success("删除成功")
          this.load()
          // window.location.reload()      // 刷新页面
        }
      })
    },
  }
}
</script>


<style scoped>
.headerBg {
  background: #eee!important;
}
</style>