<template>
  <div class="healthTipsRecommend">
    <el-breadcrumb separator="/" class="drugRecommend-breadcrumb">
      <el-breadcrumb-item
        ><span style="font-size: 16px">首页设置</span></el-breadcrumb-item
      >
      <el-breadcrumb-item
        ><span style="font-size: 18px">健康资讯推荐</span></el-breadcrumb-item
      >
    </el-breadcrumb>

    <div class="center">
      <div class="table">
        <el-table :data="tableData" style="width: 100%" :loading="loading">
          <el-table-column label="序号" width="80">
            <template slot-scope="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column prop="title" label="标题" width="320">
          </el-table-column>
          <el-table-column prop="author" label="作者" width="120">
          </el-table-column>
          <el-table-column prop="category" label="分类" width="120">
          </el-table-column>
          <el-table-column prop="readNumber" label="阅读次数" width="120">
          </el-table-column>
          <el-table-column prop="origin" label="来源" width="120">
          </el-table-column>
          <el-table-column label="创作时间" width="150">
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
                @click="changeArticle(scope.$index, scope.row)"
                >更换</el-button
              >
            </template>
          </el-table-column>
        </el-table>

        <div class="changeArticle">
          <el-dialog
            title="更换文章"
            :visible.sync="dialogFormVisible"
            width="30%"
          >
            <el-form :model="form">
              <el-form-item label="文章编号" :label-width="formLabelWidth">
                <el-input
                  v-model="form.id"
                  autocomplete="off"
                  placeholder="请输入文章编号进行替换"
                ></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button size="medium" @click="dialogFormVisible = false"
                >取 消</el-button
              >
              <el-button size="medium" type="primary" @click="confirmChange"
                >确 定</el-button
              >
            </div>
          </el-dialog>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    this.getRecommendHealthTips();
  },
  methods: {
    /* 获取首页健康资讯status = 1 的 8篇文章 */
    getRecommendHealthTips() {
      this.$axios.post("/health/tips/status").then((res) => {
        console.log("全部资讯===>", res);
        this.tableData = res.data.data.responseBody;
        this.loading = false;
      });
    },
    /* 查看文章 */
    lookArticle(index, row) {
      console.log(index);
      console.log(row);
      this.$router.push("/article/details/" + row.id);
    },
    /* 更换文章 */
    changeArticle(index, row) {
      this.articleId = row.id;
      this.dialogFormVisible = true;
    },
    /* 确认更换文章 */
    confirmChange() {
      if (this.form.id != "") {
        let formData = new FormData();
        formData.append("oldArticleId", this.articleId);
        formData.append("newArticleId", this.form.id);
        this.$axios.post("/health/tips/status/change", formData,{
         headers: {
            Authorization: localStorage.getItem("token"),
          },
        }).then((res) => {
          if (res.data.status) {
            this.getRecommendHealthTips()
            this.$message({
              type: "success",
              message: res.data.message,
            });
          } else {
            this.$message({
              type: "info",
              message: res.data.message,
            });
          }
        });
        this.dialogFormVisible = false;
        this.form.id = "";
      } else {
        this.$message({
          type: "info",
          message: "请先输入输入文章编号！",
        });
      }
    },
    /* 分割时间 */
    splitTime(time) {
      let arr = time.split(" ");
      return arr[0];
    },
  },
  data() {
    return {
      loading: true,
      tableData: [],
      dialogFormVisible: false,
      articleId: "",
      form: {
        id: "",
      },
      formLabelWidth: "100px",
    };
  },
};
</script>

<style >
.healthTipsRecommend .center {
  margin: 15px 0;
  padding: 20px 30px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 10px;
  min-width: 1200px;
  min-height: 500px;
  color: #000000a6;
}
</style>
