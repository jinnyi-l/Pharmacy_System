<template>
  <div class="medicine">
    <el-breadcrumb separator="/" class="medicineList-breadcrumb">
      <el-breadcrumb-item
        ><span style="font-size: 16px">药品管理</span></el-breadcrumb-item
      >
      <el-breadcrumb-item
        ><span style="font-size: 18px">药品列表</span></el-breadcrumb-item
      >
    </el-breadcrumb>

    <div class="yjx-medicine">
      <el-input
        placeholder="请输入药品名称、编号或总分类进行查找"
        v-model="inputContent"
        clearable
        class="medicine-search"
      >
      </el-input>
      <el-button
        type="primary"
        icon="el-icon-search"
        plain
        @click="goToSearch()"
        >搜索</el-button
      >

      <el-button
        type="danger"
        size="small"
        class="el-icon-delete moreDelete"
        @click="toBatchDelete()"
        slot="reference"
        >&nbsp;批量删除</el-button
      >

      <el-button
        type="primary"
        size="small"
        class="el-icon-add medicine-add"
        icon="el-icon-plus"
        @click="handleAdd()"
        >添加药品</el-button
      >
      <el-button
        type="success"
        size="small"
        class="look-all"
        icon="el-icon-document"
        @click="lookAll()"
        >查看全部</el-button
      >

      <!-- 表格开始 -->
      <el-table
        :data="medicineList"
        style="width: 100%"
        class="medicineList-table"
        :default-sort="{ prop: 'id', order: 'ascending' }"
        @selection-change="handleSelectionChange"
        v-loading="loading"
      >
        <el-table-column type="selection" width="50" class="medicine-selection">
        </el-table-column>
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="药品编号">
                <span>{{ props.row.id }}</span>
              </el-form-item>
              <el-form-item label="药品名称">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="发布人">
                <span>{{ props.row.uploadUser }}</span>
              </el-form-item>
              <el-form-item label="上架时间">
                <span>{{ props.row.gmtCreate }}</span>
              </el-form-item>
              <el-form-item label="分类（总）">
                <span>{{ props.row.sort }}</span>
              </el-form-item>
              <el-form-item label="分类（子）">
                <span>{{ props.row.secondSort }}</span>
              </el-form-item>
              <el-form-item label="药品价格">
                <span style="font-family: Verdana, Geneva, Tahoma, sans-serif"
                  >￥{{ parseFloat(props.row.price).toFixed(2) }}</span
                >
              </el-form-item>
              <el-form-item label="药品描述">
                <span>{{ props.row.description }}</span>
              </el-form-item>
              <el-form-item label="库存（盒）">
                <span>{{ props.row.stock }}</span>
              </el-form-item>
              <el-form-item label="生产厂家">
                <span>{{ props.row.manufacturer }}</span>
              </el-form-item>
              <el-form-item label="批准文号">
                <span>{{ props.row.approvalNumber }}</span>
              </el-form-item>
              <el-form-item label="药品图片">
                <img :src="props.row.picture" width="200" height="180" alt="" />
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="药品编号" prop="id" sortable width="120">
        </el-table-column>
        <el-table-column label="药品名称" prop="name" width="190">
        </el-table-column>
        <el-table-column
          label="药品分类（总）"
          prop="sort"
          width="145"
        ></el-table-column>
        <el-table-column
          label="药品分类（子）"
          prop="secondSort"
          width="145"
        ></el-table-column>
        <el-table-column label="仓库剩余" width="120">
          <template slot-scope="scope">
            <span style="margin-left: 15px">{{ scope.row.stock }}</span>
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
              @change="isShowDrug(scope.row.id)"
              style="margin-left: 8px"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- <el-button size="mini" class="el-icon-document">说明书</el-button> -->
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row.id)"
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
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :background="true"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "MedicineList",
  data() {
    return {
      medicineList: [],
      loading: true,
      // 当前处在第几页
      currentPage: 1,
      // 每一页展示多少条数据
      pageSize: 10,
      // 返回的总数据有多少条
      total: 10,
      // 要搜索的内容
      inputContent: "",
      // 记录批量操作中的内容
      multipleSelection: [],
    };
  },
  methods: {
    /* 搜索功能：可通过药品名称、编号或用途查找 */
    goToSearch() {
      if (this.inputContent != "") {
        this.currentPage = 1;
        this.getMedicineList();
      }
    },
    /* 跳转到添加页面 */
    handleAdd() {
      this.$router.push("/drug/add");
    },
    /* 分页获取药品列表， 页面加载就执行 */
    getMedicineList() {
      const _this = this;
      this.$axios
        .get(
          "/search/with/" +
            _this.currentPage +
            "/" +
            _this.pageSize +
            "/" +
            _this.inputContent.trim()
        )
        .then((res) => {
          console.log("药品列表==>", res);
          _this.medicineList = res.data.data.data.records;
          _this.total = res.data.data.data.total;
          _this.loading = false;
        });
    },
    /* 跳转到编辑页面 */
    handleEdit(index, rowId) {
      this.$router.push("/drug/edit/" + rowId);
      console.log("正在编辑" + index, rowId);
    },
    /* 删除药品 */
    handleDelete(index, row) {
      const _this = this;
      _this
        .$confirm("此操作将永久删除该药品, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
        .then(() => {
          this.$axios
            .get("/medicine/delete/" + row.id, {
              headers: {
                Authorization: localStorage.getItem("token"),
              },
            })
            .then((res) => {
              _this.getMedicineList();
              // 记录日志
              _this.$axios
                .get("/admin/info/" + _this.$store.getters.getUser.id, {
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
                    "删除药品--药品编号：" + row.id + "，药品名称：" + row.name;
                  formData.append("doWhat", doWhat);
                  _this.$axios.post("/log/add", formData);
                });
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
            list.push(item.id);
          }
          let formData = new FormData();
          formData.append("drugIds", list);

          this.$axios
            .post("/medicine/batch/delete", formData, {
              headers: {
                Authorization: localStorage.getItem("token"),
              },
            })
            .then((res) => {
              _this.getMedicineList();
              // 记录日志
              _this.$axios
                .get("/admin/info/" + _this.$store.getters.getUser.id, {
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
                  let doWhat = "批量删除药品--药品编号：[ " + list + " ]";
                  formData.append("doWhat", doWhat);
                  _this.$axios.post("/log/add", formData);
                });
            });
          _this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {});
    },
    /* 获取当前选择的pageSize */
    handleSizeChange(newPageSize) {
      this.pageSize = newPageSize;
      this.getMedicineList();
    },
    /* 获取当前选择的pageSize */
    handleCurrentChange(newCurrentPage) {
      this.currentPage = newCurrentPage;
      this.getMedicineList();
    },
    /* 改变药品状态status */
    isShowDrug(drugId) {
      const _this = this;
      this.$axios
        .get("/medicine/isShow/" + drugId, {
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        })
        .then((res) => {
          _this.$alert("操作成功", "提示", {
            confirmButtonText: "确定",
            callback: (action) => {
              _this.getMedicineList(_this.currentPage);
            },
          });
        });
    },
    /* 查询全部 */
    lookAll() {
      this.inputContent = "";
      this.currentPage = 1;
      this.getMedicineList();
    },
  },
  /* 页面加载就执行 */
  created() {
    this.getMedicineList();
  },
};
</script>

<style>
.medicine .yjx-medicine {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 10px;
  padding: 20px 25px 30px 25px;
  min-width: 1200px;
  min-height: 500px;
}

.medicine .yjx-medicine .filter {
  display: inline-block;
  color: #606266;
  margin-left: 18px;
}

.medicine .medicine-search {
  width: 30%;
  margin: 10px 0;
}
.medicine .medicine-add,
.medicine .moreDelete,
.medicine .look-all {
  float: right;
  margin-top: 18px;
  margin-left: 10px;
}
.medicine .medicineList-breadcrumb {
  margin: 0 0 15px 2px;
}

.medicine .demo-table-expand {
  font-size: 0;
}
.medicine .demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.medicine .demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
.medicine .el-table__cell.el-table__expanded-cell {
  padding: 5px 40px 5px 106px;
}
.medicine .el-pagination {
  margin: 10px 0 0 0;
}
</style>
