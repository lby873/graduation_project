<template>
  <div>
    <div style="margin: 10px 0">
      <template>
        <el-input style="width: 200px;" placeholder="请输入活动名称" suffix-icon="el-icon-search" v-model="name"></el-input>
        <el-input style="width: 200px;margin-left: 20px;" placeholder="请输入社团名称" suffix-icon="el-icon-search" v-model="organizer"></el-input>
        <el-input style="width: 200px;margin-left: 20px;" placeholder="请输入地点" suffix-icon="el-icon-search" v-model="address"></el-input>
      </template>
      <el-button style="margin-left: 20px;" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <el-table :data="tableData" border stripe header-cell-class-name="headerBg">
      <el-table-column prop="activityID" label="活动ID" width="60"></el-table-column>
      <el-table-column prop="time" label="活动时间" width="100"></el-table-column>
      <el-table-column prop="name" label="活动名称" width="100"></el-table-column>
      <el-table-column prop="organizer" label="主办方社团" width="100"></el-table-column>
      <el-table-column prop="address" label="活动地址" width="100"></el-table-column>
      <el-table-column prop="detail" label="活动详情"></el-table-column>
      <el-table-column label="操作"  width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" >修改<i class="el-icon-edit"></i></el-button>
          <el-button type="danger" >删除<i class="el-icon-remove-outline"></i></el-button>
        </template>
      </el-table-column>
    </el-table>

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
export default {
  name: "activityAdmin",
  data(){
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 3,
      name: '',
      organizer: '',
      address: '',
      isSign: false,
      isCancelSign: true,
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
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          organizer: this.organizer,
          address: this.address,
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
      this.name = ""
      this.organizer = ""
      this.address = ""
      this.load()
    },

  }
}
</script>


<style scoped>
.headerBg {
  background: #eee!important;
}
</style>