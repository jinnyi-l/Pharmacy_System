<template>
  <div>
    <el-breadcrumb separator="/" class="sortAdd-breadcrumb">
      <el-breadcrumb-item
        ><span style="font-size: 16px">药品管理</span></el-breadcrumb-item
      >
      <el-breadcrumb-item
        ><span style="font-size: 18px">分类管理</span></el-breadcrumb-item
      >
    </el-breadcrumb>

    <!-- 搜索组件 -->
    <div class="yjx-sort">
      <el-input
        placeholder="可通过分类名称、编号或上传者名字查找"
        v-model="inputContent"
        clearable
        class="sort-search"
      >
      </el-input>
      <el-button
        type="primary"
        icon="el-icon-search"
        plain
        @click="goToSearch()"
        >搜索</el-button
      >

      <!-- 右侧按钮批量删除 -->
      <el-button
        type="danger"
        size="small"
        class="el-icon-delete moreSortDelete"
        @click="toBatchDelete()"
        >&nbsp;批量删除</el-button
      >

      <!-- 右侧按钮添加分类 -->
      <el-button
        type="primary"
        size="small"
        class="el-icon-add sort-add"
        icon="el-icon-plus"
        @click="addSort()"
        >添加分类</el-button
      >
      <el-dialog :title="this.form.title" :visible.sync="dialogFormVisible">
        <el-form :model="form" :rules="rules" ref="form">
          <el-form-item
            label="分类名称"
            :label-width="formLabelWidth"
            prop="category"
          >
            <el-input v-model="form.category" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item
            label="上传者"
            :label-width="formLabelWidth"
            prop="uploadUser"
          >
            <el-input v-model="form.uploadUser" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="commitSortInfo('form')"
            >确 定</el-button
          >
        </div>
      </el-dialog>

      <!-- 右侧按钮查看全部 -->
      <el-button
        type="success"
        size="small"
        class="look-all"
        icon="el-icon-document"
        @click="lookAll()"
        >查看全部</el-button
      >

      <el-table
        :data="sortList"
        style="width: 100%"
        class="yjx-sort-table"
        @selection-change="handleSelectionChange"
        :default-sort="{ prop: 'id', order: 'ascending' }"
        v-loading="loading"
      >
        <el-table-column type="selection" width="55" class="sort-selection">
        </el-table-column>
        <el-table-column label="序" width="150" prop="id" sortable>
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>

        <el-table-column label="分类（总）" width="180">
          <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
              {{ scope.row.category }}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="上传者" width="150">
          <template slot-scope="scope">
            <span>{{ scope.row.uploadUser }}</span>
          </template>
        </el-table-column>
        <el-table-column label="是否展示" prop="status" width="120">
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
        <el-table-column label="添加时间" width="250" prop="date" sortable>
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.gmtCreate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="getSecondCategory(scope.$index, scope.row)"
              >子分类</el-button
            >
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

      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 30, 40, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next"
        :total="total"
        :background="true"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "MedicineCategory",
  data() {
    return {
      // 展示分类的表格
      sortList: [],
      loading: true,
      // 正常情况下让表单不可见
      dialogFormVisible: false,
      // 添加分类信息的表单
      form: {
        title: "添加分类",
        sortId: "",
        category: "",
        uploadUser: "",
      },
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
        uploadUser: [
          { required: true, message: "请输入上传人的名字", trigger: "blur" },
        ],
      },
      formLabelWidth: "120px",
      // 当前处在第几页
      currentPage: 1,
      // 每一页展示多少条数据
      pageSize: 10,
      // 返回的总数据有多少条
      total: 10,
      // 要搜索的内容
      inputContent: "",
      multipleSelection: [],
    };
  },
  methods: {
    /* 可根据通过分类名称、编号或上传者名字查找 */
    goToSearch() {
      if (this.inputContent != "") {
        this.currentPage = 1;
        this.getSortList();
      }
    },
    /* 查询全部的分类 */
    getSortList() {
      const _this = this;
      this.$axios
        .get(
          "/sortList/" +
            this.currentPage +
            "/" +
            this.pageSize +
            "/" +
            this.inputContent.trim()
        )
        .then((res) => {
          console.log(res.data.data.sortData);
          _this.sortList = res.data.data.sortData.records;
          _this.total = res.data.data.sortData.total;
          _this.loading = false;
        });
    },
    getSecondNumberById(row) {
      let formData = new FormData();
      formData.append("firstSortId", row.sortId);
      this.$axios.post("/category/second/number", formData).then((res) => {
        console.log(res.data.data.responseBody);
        return res.data.data.responseBody;
      });
    },
    // 跳转到子分类页面
    getSecondCategory(index, row) {
      this.$router.push("/type/management/" + row.category + "/" + row.sortId);
    },
    /* 根据分类的id去做更改status，是否展示 */
    isShowSort(row) {
      this.$axios
        .get("/sort/isShow/" + row.sortId, {
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        })
        .then((res) => {
          if (res.data.status) {
            this.$alert("操作成功", "提示", {
              confirmButtonText: "确定",
              callback: (action) => {},
            });
            // 记录日志
            this.$axios
              .get("/admin/info/" + this.$store.getters.getUser.id, {
                headers: {
                  Authorization: localStorage.getItem("token"),
                },
              })
              .then((res) => {
                let user = res.data.data.data;
                let formData = new FormData();
                formData.append(
                  "auth",
                  user.auth == 1 ? "超级管理员" : "管理员"
                );
                formData.append("admin", user.realName);
                let doWhat =
                  row.status == 1
                    ? "启用药品分类（总） - " + row.category
                    : "禁用药品分类（总） - " + row.category;
                formData.append("doWhat", doWhat);
                this.$axios.post("/log/add", formData);
              });
          }
        });
    },
    /* 编辑选择的分类信息 */
    handleEdit(index, row) {
      this.form.title = "修改分类";
      this.form.sortId = row.sortId;
      this.form.category = row.category;
      this.form.uploadUser = row.uploadUser;
      this.form.sortHaveNum = row.sortHaveNum;
      this.dialogFormVisible = true;
    },
    /* 添加分类，将信息填写至表单 */
    addSort() {
      this.dialogFormVisible = true;
      this.form.category = "";
      this.form.uploadUser = "";
    },
    /* 提交表单，并将表单的dialogFormVisible设置为false */
    commitSortInfo(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.form.title == "添加分类") {
            const _this = this;
            let medicineSort = this.form;
            this.$axios
              .post("/category/add", medicineSort, {
                headers: {
                  Authorization: localStorage.getItem("token"),
                },
              })
              .then((res) => {
                _this.getSortList();
                _this.dialogFormVisible = false;
              });
          } else if (this.form.title == "修改分类") {
            let sortInfo = {
              sortId: this.form.sortId,
              category: this.form.category,
              uploadUser: this.form.uploadUser,
              sortHaveNum: this.form.sortHaveNum,
            };

            const _this = this;
            this.$axios
              .post("/category/update", sortInfo, {
                headers: {
                  Authorization: localStorage.getItem("token"),
                },
              })
              .then((res) => {
                _this.getSortList();
                _this.dialogFormVisible = false;
              });
          } else {
          }
        } else {
          console.log("填写的信息有误!!");
          return false;
        }
      });
    },
    /* 单个删除 */
    handleDelete(index, row) {
      const _this = this;
      _this
        .$confirm("此操作将永久删除该分类, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
        .then(() => {
          this.$axios
            .get("/sort/delete/" + row.sortId, {
              headers: {
                Authorization: localStorage.getItem("token"),
              },
            })
            .then((res) => {
              // 记录日志
              this.$axios
                .get("/admin/info/" + this.$store.getters.getUser.id, {
                  headers: {
                    Authorization: localStorage.getItem("token"),
                  },
                })
                .then((res) => {
                  let user = res.data.data.data;
                  let formData = new FormData();
                  formData.append(
                    "auth",
                    user.auth == 1 ? "超级管理员" : "管理员"
                  );
                  formData.append("admin", user.realName);
                  let doWhat = "删除药品分类（总） - " + row.category;
                  formData.append("doWhat", doWhat);
                  this.$axios.post("/log/add", formData);
                });
              // 获取分类列表
              _this.getSortList();
            });
          _this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {});
    },
    /* 复选框进行多选 */
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    /* 先进行判断，是否有选中数据，
       再执行批量删除请求 */
    toBatchDelete() {
      if (this.multipleSelection == null || this.multipleSelection == "") {
        this.$alert("请先选择要删除的数据！！！", "提示", {
          confirmButtonText: "确定",
          callback: (action) => {},
        });
      } else {
        // 执行批量删除
        this.batchDelete();
      }
    },
    /* 发送批量删除请求  */
    batchDelete() {
      const _this = this;
      this.$confirm("该操作将永久删除选中的数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let list = [];
          for (const item of this.multipleSelection) {
            list.push(item.sortId);
          }
          let formData = new FormData();
          formData.append("sortIds", list);
          this.$axios
            .post("/category/batch/delete", formData, {
              headers: {
                Authorization: localStorage.getItem("token"),
              },
            })
            .then((res) => {
              _this.getSortList();
            });
          _this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {});
    },
    /* 以当前页的size返回第一页 */
    lookAll() {
      this.currentPage = 1;
      this.inputContent = "";
      this.getSortList();
    },
    /* 获取当前选择的pageSize */
    handleSizeChange(newPageSize) {
      this.pageSize = newPageSize;
      this.getSortList();
    },
    /* 获取当前选择的currentPage */
    handleCurrentChange(newCurrentPage) {
      this.currentPage = newCurrentPage;
      this.getSortList();
    },
  },
  /* 页面一加载就获取信息 */
  created() {
    this.getSortList();
  },
};
</script>

<style>
.yjx-sort {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 10px;
  padding: 20px 25px 30px 25px;
  min-width: 1200px;
  min-height: 500px;
}
.sort-search {
  width: 30%;
  margin: 10px 0;
}
.sort-add,
.moreSortDelete,
.look-all {
  float: right;
  margin-top: 18px;
}
.sortAdd-breadcrumb {
  margin: 0 0 15px 2px;
}

.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
.el-table__cell.el-table__expanded-cell {
  padding: 5px 40px 5px 106px;
}
.yjx-sort-table .el-table__row {
  height: 78px;
}
.el-pagination {
  margin: 10px 0 0 0;
}
.el-dialog__wrapper .el-dialog {
  width: 35%;
}
.el-dialog__wrapper .el-dialog .el-input__inner {
  width: 82%;
}
</style>