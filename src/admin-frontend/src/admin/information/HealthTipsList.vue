<template>
  <div class="healthTipsList">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item
        ><span style="font-size: 16px">资讯推送</span></el-breadcrumb-item
      >
      <el-breadcrumb-item
        ><span style="font-size: 18px">资讯列表</span></el-breadcrumb-item
      >
    </el-breadcrumb>

    <div class="center">
      <div style="margin: 8px 0">
        <el-radio-group v-model="radio" size="small">
          <el-radio-button label="创作时间"></el-radio-button>
          <el-radio-button label="阅读次数"></el-radio-button>
          <el-radio-button label="首页展示"></el-radio-button>
        </el-radio-group>
      </div>
      <div class="table">
        <el-table
          :data="tableData"
          style="width: 100%"
          :row-class-name="tableRowClassName"
        >
          <el-table-column prop="id" label="文章编号" width="80">
          </el-table-column>

          <el-table-column prop="title" label="标题" width="340">
          </el-table-column>
          <el-table-column prop="author" label="作者" width="100">
          </el-table-column>
          <el-table-column prop="category" label="分类" width="120">
          </el-table-column>
          <el-table-column label="阅读次数" width="110">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.readNumber }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="origin" label="来源" width="80">
          </el-table-column>
          <el-table-column label="创作时间" width="130">
            <template slot-scope="scope">
              {{ splitTime(scope.row.gmtCreate) }}
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="lookArticle(scope.$index, scope.row)"
                >查看</el-button
              >
              <el-button
                size="mini"
                @click="editArticle(scope.$index, scope.row)"
                class="el-icon-edit"
                v-if="scope.row.username === $store.getters.getUser.username"
                >编辑</el-button
              >
              <el-button
                size="mini"
                type="danger"
                plain
                @click="deleteArticle(scope.$index, scope.row)"
                class="el-icon-delete"
                v-if="scope.row.username === $store.getters.getUser.username"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="current"
          :page-sizes="pageSizes"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          :background="true"
        >
        </el-pagination>
      </div>

      <div class="delete">
        <el-dialog title="提示" :visible.sync="dialogVisible" width="25%">
          <span>确定要删除此文章吗？</span>
          <span slot="footer" class="dialog-footer">
            <el-button size="medium" @click="dialogVisible = false"
              >取 消</el-button
            >
            <el-button type="primary" size="medium" @click="handleDelete()"
              >删 除</el-button
            >
          </span>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    this.getAllHealthTips();
  },
  methods: {
    /* 获取全部的文章 */
    getAllHealthTips() {
      let formData = new FormData();
      formData.append("current", this.current);
      formData.append("pageSize", this.pageSize);
      let sortBy = "";
      if (this.radio === "创作时间") {
        sortBy = "gmt_create";
      } else if (this.radio === "阅读次数") {
        sortBy = "read_number";
      } else if (this.radio === "首页展示") {
        sortBy = "status";
      }
      formData.append("sortBy", sortBy);
      this.$axios.post("/health/tips/all", formData).then((res) => {
        console.log("全部资讯===>", res);
        this.tableData = res.data.data.responseBody.records;
        this.total = res.data.data.responseBody.total;
      });
    },
    /* 文章status == 1 的挂上绿色背景色 */
    tableRowClassName({ row, rowIndex }) {
      if (row.status === 1) {
        return "success-row";
      }
      return "";
    },
    /* 查看文章 */
    lookArticle(index, row) {
      console.log(index);
      console.log(row);
      this.$router.push("/article/details/" + row.id);
    },
    /* 编辑文章 */
    editArticle(index, row) {
      console.log(index);
      console.log(row);
      this.$router.push("/article/edit/" + row.id);
    },
    /* 删除文章,弹出提示框 */
    deleteArticle(index, row) {
      console.log(index);
      console.log(row);
      this.dialogVisible = true;
      this.deleteId = row.id;
      this.deleteTitle = row.title;
    },
    /* 确认删除！ */
    handleDelete() {
      console.log("我要删除==>", this.deleteId);
      let formData = new FormData();
      formData.append("id", this.deleteId);
      this.$axios
        .post("/health/tips/delete", formData, {
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        })
        .then((res) => {
          if (res.data.status) {
            this.$message.success("删除成功！");
            this.getAllHealthTips();
            // 记录日志
            this.$axios
              .get("/admin/info/" + this.$store.getters.getUser.id)
              .then((res) => {
                let user = res.data.data.data;
                let formData = new FormData();
                formData.append(
                  "auth",
                  user.auth == 1 ? "超级管理员" : "管理员"
                );
                formData.append("admin", user.realName);
                let doWhat =
                  "删除了资讯文章（" +
                  this.deleteTitle +
                  "），文章编号：" +
                  this.deleteId;
                formData.append("doWhat", doWhat);
                this.$axios.post("/log/add", formData);
              });
          } else {
            this.$message.error("操作失败，请稍后再试！");
          }
        });
      this.dialogVisible = false;
    },
    /* 分页数量改变 */
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
    },
    /* 改变当前页 */
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val;
    },
    /* 分割时间 */
    splitTime(time) {
      let arr = time.split(" ");
      return arr[0];
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
  },
  data() {
    return {
      tableData: [],
      current: 1,
      pageSize: 10,
      pageSizes: [10, 20, 30, 50],
      total: 100,
      dialogVisible: false,
      deleteId: "",
      deleteTitle: "",
      radio: "创作时间",
    };
  },
  watch: {
    current: "getAllHealthTips",
    pageSize: "getAllHealthTips",
    radio: "getAllHealthTips",
  },
};
</script>

<style scoped>
@import "./style/healthTipsList.css";
</style>
