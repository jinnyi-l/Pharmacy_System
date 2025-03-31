<template>
  <div class="logOperation">
    <el-breadcrumb separator="/" class="drugRecommend-breadcrumb">
      <el-breadcrumb-item
        ><span style="font-size: 16px">后台管理</span></el-breadcrumb-item
      >
      <el-breadcrumb-item
        ><span style="font-size: 18px">操作日志</span></el-breadcrumb-item
      >
    </el-breadcrumb>

    <div class="center">
      <div class="table">
        <el-table :data="logList" stripe style="width: 100%">
          <el-table-column prop="gmtCreate" label="时间" width="180">
          </el-table-column>
          <el-table-column prop="auth" label="身份" width="180">
          </el-table-column>
          <el-table-column prop="admin" label="姓名" width="180">
          </el-table-column>
          <el-table-column prop="doWhat" label="操作"> </el-table-column>
        </el-table>
      </div>
      <div class="pagination">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="current"
          :page-sizes="pageSizes"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 日志list
      logList: [],
      // 当前页
      current: 1,
      // 每页个数
      pageSize: 10,
      // 可选择每页个数
      pageSizes: [10, 20, 30, 50],
      // 总数
      total: 10,
    };
  },
  watch: {
    current: "getAllLog",
    pageSize: "getAllLog",
  },
  created() {
    this.getAllLog();
  },
  methods: {
    /* 获取全部日志 */
    getAllLog() {
      let formData = new FormData();
      formData.append("current", this.current);
      formData.append("pageSize", this.pageSize);
      this.$axios
        .post("/log/look", formData, {
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        })
        .then((res) => {
          console.log("日志=>", res);
          this.logList = res.data.data.responseBody.records;
          this.total = res.data.data.responseBody.total;
        });
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val;
    },
  },
};
</script>

<style lang="less" scoped>
@import "./style/LogOperation.less";
</style>
