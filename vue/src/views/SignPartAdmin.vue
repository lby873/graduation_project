<template>
  <div style="margin: 0 10px">
    <div style="margin: 10px 0 25px 0;display: flex;justify-content: center;align-items:center;">
      <template>
        <el-input style="width: 200px;font-size: 13px" placeholder="请输入活动名称" suffix-icon="el-icon-search" v-model="activityName"></el-input>
        <el-input style="width: 200px;margin-left: 20px;font-size: 13px" placeholder="请输入社团名称" suffix-icon="el-icon-search" v-model="organizer"></el-input>
        <el-input style="width: 200px;margin-left: 20px;font-size: 13px" placeholder="请输入地点" suffix-icon="el-icon-search" v-model="address"></el-input>
        <el-input style="width: 200px;margin-left: 20px;font-size: 13px" placeholder="请输入用户状态" suffix-icon="el-icon-search" v-model="status"></el-input>
      </template>
      <el-button style="margin-left: 20px;font-size: 13px" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 20px;font-size: 13px"type="warning" @click="reset">重置</el-button>
    </div>

    <div class="table-wrapper">
      <el-table :data="tableData" border stripe header-cell-class-name="headerBg">
        <el-table-column prop="activityID" sortable label="活动ID" align="center"></el-table-column>
        <el-table-column prop="activityTime" label="活动时间" align="center"></el-table-column>
        <el-table-column prop="activityName" label="活动名称" width="150" align="center"></el-table-column>
        <el-table-column prop="organizer" label="主办方社团" align="center"></el-table-column>
        <el-table-column prop="address" label="活动地址" align="center"></el-table-column>
        <el-table-column prop="userID" label="用户ID" align="center"></el-table-column>
        <el-table-column prop="nickname" label="用户昵称" align="center"></el-table-column>
        <el-table-column prop="status" label="用户状态" align="center"></el-table-column>
        <el-table-column label="操作" align="center" width="200">
          <template slot-scope="scope">
            <el-button v-if="scope.row.status == '取消参赛资格'" style="margin-left: 10px" type="success" @click="resetQualification(scope.row.signID)">
              恢复参赛资格<i class="el-icon-edit"></i>
            </el-button>
            <el-popconfirm v-else confirm-button-text='确定' cancel-button-text='取消' icon="el-icon-info" icon-color="red"
                           title="您确定取消其参赛资格吗？" @confirm="cancelQualification(scope.row.signID)">
              <el-button type="danger" slot="reference">取消参赛资格<i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>


    <!-- 分页行 -->
    <div style="padding: 10px 0; ">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[3, 5, 10, 15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          style="background-color: rgba(255,255,255,0.7);"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
  export default {
    name: "SignPartAdmin",
    data(){
      return {
        tableData: [],
        total: 0,
        pageNum: 1,
        pageSize: 5,
        activityName: '',
        organizer: '',
        address:'',
        status:'',
        signDTO:{       // 用于取消参赛资格
          activityID: '',
          userID: '',
        },
      }
    },
    created() {
      this.load()
    },
    methods:{
      load(){
        // 用axios请求数据
        this.request.get("/sign/allPage",{
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            activityName: this.activityName,
            organizer: this.organizer,
            address: this.address,
            status: this.status,
          }
        }).then(res => {
          this.tableData = res.data
          this.total = res.total
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
        this.activityName = ""
        this.organizer = ""
        this.address = ""
        this.status = ""
        this.load()
      },

      cancelQualification(signID){
        this.request.get("/sign/cancelQualification",{params: { signID: signID }}).then(res => {
          if (res){
            this.$message.success("取消成功")
            this.load()
            // window.location.reload()      // 刷新页面
          }
        })
      },
      resetQualification(signID){
        this.request.get("/sign/resetQualification",{params: { signID: signID }}).then(res => {
          if (res){
            this.$message.success("恢复成功")
            this.load()
            // window.location.reload()      // 刷新页面
          }
        })
      },
    }
  }
</script>

<style scoped>
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