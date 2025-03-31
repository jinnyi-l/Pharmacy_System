<template>
  <div class="secondCategory">
    <el-page-header @back="goBack" :content="$route.params.category">
    </el-page-header>

    <div class="center">
      <!-- 右侧按钮添加分类 -->
      <el-button
        type="primary"
        size="small"
        class="el-icon-add secondSortAdd"
        icon="el-icon-plus"
        @click="addSort()"
        >添加分类</el-button
      >
      <el-dialog
        :title="form.title"
        :visible.sync="dialogFormVisible"
        width="30%"
      >
        <el-form :model="form" :rules="rules" ref="form">
          <el-form-item
            label="分类名称"
            :label-width="formLabelWidth"
            prop="category"
          >
            <el-input v-model="form.category" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="commitSortInfo('form')"
            >确 定</el-button
          >
        </div>
      </el-dialog>

      <div class="table">
        <el-table
          :data="secondCategory"
          style="width: 100%"
          :default-sort="{ prop: 'id', order: 'ascending' }"
          v-loading="loading"
        >
          <el-table-column label="分类编号（子）" width="150">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <el-table-column label="添加时间" width="250" prop="date" sortable>
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ scope.row.gmtCreate }}</span>
            </template>
          </el-table-column>

          <el-table-column label="分类名称（子）" width="200">
            <template slot-scope="scope">
              {{ scope.row.secondSort }}
            </template>
          </el-table-column>
          <el-table-column label="拥有药品数量（种类）" width="200">
            <template slot-scope="scope">
              <span style="margin-left: 28px">{{ scope.row.amount }}</span>
            </template>
          </el-table-column>
          <el-table-column label="是否展示" prop="status" width="150">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                :active-value="1"
                :inactive-value="0"
                active-color="#409eff"
                inactive-color="#f0eeee"
                @change="isShowSort(scope.row)"
              >
              </el-switch>
            </template>
          </el-table-column>

          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)"
                class="el-icon-edit"
                >编辑</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
                class="el-icon-delete"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pagination">
        <!-- 分页 -->
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page="current"
          :page-size="10"
          layout="total, prev, pager, next"
          :total="total"
          :background="true"
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
      // 一级分类id
      firstSortId: "",
      // 二级分类
      secondCategory: [],
      // 是否显示加载动画
      loading: true,
      current: 1,
      total: 0,
      // 添加分类信息的表单
      form: {
        title: "添加分类",
        id: "",
        category: "",
      },
      formLabelWidth: "100px",
      // 表单校验规则
      rules: {
        category: [
          { required: true, message: "请输入分类名称", trigger: "blur" },
          {
            min: 1,
            max: 30,
            message: "长度在 1 到 10 个字符",
            trigger: "blur",
          },
        ],
      },
      // 正常情况下让表单不可见
      dialogFormVisible: false,
    };
  },
  created() {
    this.firstSortId = this.$route.params.categoryId;
    this.getSecondCategory();
  },
  methods: {
    goBack() {
      console.log("go back");
      this.$router.go(-1);
    },
    /* 获取一级分类对应的二级分类 */
    getSecondCategory() {
      let formData = new FormData();
      formData.append("current", 1);
      formData.append("firstSortId", this.firstSortId);
      this.$axios.post("/category/second", formData).then((res) => {
        console.log("这里是二级分类结果--->", res);
        this.secondCategory = res.data.data.responseBody.records;
        this.total = res.data.data.responseBody.total;
        this.loading = false;
      });
    },
    /* 添加分类，将信息填写至表单 */
    addSort() {
      this.dialogFormVisible = true;
      this.form.category = "";
    },
    /* 提交表单，并将表单的dialogFormVisible设置为false */
    commitSortInfo(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.form.title == "添加分类") {
            let categoryForm = {
              firstSortId: this.firstSortId,
              secondSort: this.form.category,
            };
            this.$axios
              .post("/category/second/add", categoryForm)
              .then((res) => {
                this.getSecondCategory();
                this.dialogFormVisible = false;
                if (res.data.status) {
                  this.$message({
                    type: "success",
                    message: "添加成功！",
                  });
                }
              });
          } else if (this.form.title == "修改分类") {
            let newCategoryForm = {
              id: this.form.id,
              secondSort: this.form.category,
            };
            this.$axios
              .post("/category/second/update", newCategoryForm)
              .then((res) => {
                this.getSecondCategory();
                this.dialogFormVisible = false;
                if (res.data.status) {
                  this.$message({
                    type: "success",
                    message: "修改成功！",
                  });
                }
              });
          } else {
          }
        } else {
          console.log("填写的信息有误!!");
          return false;
        }
      });
    },
    /* 根据分类的id去做更改status，是否展示 */
    isShowSort(row) {
      let formData = new FormData();
      formData.append("id", row.id);
      formData.append("status", row.status);
      this.$axios
        .post("/category/second/change/status", formData)
        .then((res) => {
          if (res.data.status) {
            this.$alert("操作成功", "提示", {
              confirmButtonText: "确定",
              callback: (action) => {},
            });
            // 记录日志
            this.$axios.get("/admin/info/" + this.$store.getters.getUser.id,
              {
                headers: {
                  Authorization: localStorage.getItem("token"),
                },
              }).then((res) => {
                let user = res.data.data.data;
                let formData = new FormData();
                formData.append(
                  "auth",
                  user.auth == 1 ? "超级管理员" : "管理员"
                );
                formData.append("admin", user.realName);
                let doWhat =
                  row.status == 1
                    ? "启用药品分类（子） - " + row.secondSort
                    : "禁用药品分类（子） - " + row.secondSort;
                formData.append("doWhat", doWhat);
                this.$axios.post("/log/add", formData);
              });
          }
        });
    },
    handleEdit(index, row) {
      console.log("编辑===>", row.id);
      this.form.title = "修改分类";
      this.form.category = row.secondSort;
      this.form.id = row.id;
      this.dialogFormVisible = true;
    },
    /* 删除二级分类 */
    handleDelete(index, row) {
      this.$confirm("此操作将永久删除该分类, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let formData = new FormData();
          formData.append("id", row.id);
          this.$axios.post("/category/second/delete", formData).then((res) => {
            if (res.data.status) {
              this.getSecondCategory();
              this.$message({
                type: "success",
                message: "删除成功！",
              });
            }
          });
        })
        .catch(() => {});
    },
    /* 获取当前选择的current */
    handleCurrentChange(newCurrent) {
      this.current = newCurrent;
    },
  },
  watch: {
    current: "getSecondCategory",
  },
};
</script>

<style scoped>
.secondCategory >>> .el-page-header__content {
  color: #606266;
}

.secondCategory .center {
  margin: 15px 0;
  padding: 20px 30px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 10px;
  min-width: 1200px;
  min-height: 500px;
  color: #000000a6;
}

.secondCategory .center .pagination {
  margin-top: 18px;
}

.secondCategory .center .secondSortAdd {
  float: right;
}
</style>