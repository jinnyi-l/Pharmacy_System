<template>
  <div class="admin_list">
    <!-- 顶部面包屑 -->
    <el-breadcrumb separator="/" class="adminList-breadcrumb">
      <el-breadcrumb-item
        ><span style="font-size: 16px">用户管理</span></el-breadcrumb-item
      >
      <el-breadcrumb-item
        ><span style="font-size: 18px">管理员列表</span></el-breadcrumb-item
      >
    </el-breadcrumb>

    <div class="yjx-admin">
      <!-- 搜索功能 -->
      <el-input
        placeholder="可通过用户ID、用户名或手机号码等精确信息进行查找"
        v-model="inputContent"
        clearable
        class="admin-search"
      >
      </el-input>
      <el-button
        type="primary"
        icon="el-icon-search"
        plain
        @click="goToSearch()"
        >搜索</el-button
      >

      <!-- 查看全部按钮 -->
      <el-button
        type="success"
        size="small"
        class="look-all"
        icon="el-icon-document"
        @click="lookAll()"
        >查看全部</el-button
      >
      <!-- 添加管理员按钮 -->
      <el-button
        type="primary"
        size="small"
        class="admin-add"
        icon="el-icon-plus"
        @click="dialogFormVisible = true"
        >添加管理员</el-button
      >

      <!-- 添加管理员 表单-->
      <el-dialog
        title="添加管理员"
        :visible.sync="dialogFormVisible"
        width="32%"
      >
        <el-form :model="form" :rules="rules" ref="ruleForm">
          <el-form-item
            label="账号"
            prop="username"
            :label-width="formLabelWidth"
          >
            <el-input v-model="form.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item
            label="密码"
            prop="password"
            :label-width="formLabelWidth"
          >
            <el-input v-model="form.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item
            label="真实姓名"
            prop="realName"
            :label-width="formLabelWidth"
          >
            <el-input v-model="form.realName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email" :label-width="formLabelWidth">
            <el-input v-model="form.email" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmAdd('ruleForm')"
            >确 定</el-button
          >
        </div>
      </el-dialog>

      <!-- 表格开始 -->
      <el-table
        :data="adminList"
        border
        style="width: 100%"
        v-loading="adminListIsLoading"
        element-loading-text="拼命加载中"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="50"> </el-table-column>
        <el-table-column
          fixed
          prop="id"
          label="序"
          width="100"
          :show-overflow-tooltip="true"
        >
        <template slot-scope="scope">
          {{scope.$index + 1}}
        </template>
        </el-table-column>
        <el-table-column
          prop="pageStatus"
          label="状态"
          width="100"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <el-table-column
          prop="username"
          label="用户名"
          width="120"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <el-table-column
          prop="realName"
          label="真实姓名"
          width="120"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <el-table-column
          prop="gender"
          label="性别"
          width="80"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <el-table-column
          prop="birthday"
          label="生日"
          width="120"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <el-table-column
          prop="phone"
          label="手机号码"
          width="120"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <!-- <el-table-column prop="address" label="地址" width="230">
        </el-table-column> -->
        <el-table-column
          prop="email"
          label="邮箱"
          width="200"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <el-table-column
          prop="authority"
          label="身份"
          width="150"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <el-table-column
          prop="gmtCreate"
          label="创建时间"
          width="250"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="110"
          :show-overflow-tooltip="true"
        >
          <template slot-scope="scope">
            <el-button
              @click="handleLookOver(scope.row)"
              type="text"
              size="small"
              >查看</el-button
            >
            <el-button
              type="text"
              size="small"
              @click="handleDisable(scope.row)"
              v-if="scope.row.auth != 1"
              >{{ scope.row.status == 0 ? "启用" : "禁用" }}</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 查看用户的详细信息，以表单的形式展示 -->
      <el-dialog :visible.sync="centerDialogVisible" width="50%" center>
        <el-descriptions title="详细信息" :column="2" border :labelStyle="LS">
          <!-- <el-descriptions-item label="头像"
            ><img :src="adminDetails.avatar" width="70" height="70"
          /></el-descriptions-item> -->
          <el-descriptions-item label="ID">{{
            adminDetails.id
          }}</el-descriptions-item>
          <el-descriptions-item label="真实姓名">{{
            adminDetails.realName
          }}</el-descriptions-item>
          <el-descriptions-item label="性 别">{{
            adminDetails.gender
          }}</el-descriptions-item>
          <el-descriptions-item label="生 日">
            {{ adminDetails.birthday }}
          </el-descriptions-item>
          <el-descriptions-item label="账 号">{{
            adminDetails.username
          }}</el-descriptions-item>

          <el-descriptions-item label="拥有权限"
            ><el-tag size="small">{{
              adminDetails.auth
            }}</el-tag></el-descriptions-item
          >
          <el-descriptions-item label="邮箱地址">{{
            adminDetails.email
          }}</el-descriptions-item>
          <el-descriptions-item label="手机号码">{{
            adminDetails.phone
          }}</el-descriptions-item>
          <el-descriptions-item label="所在地址">{{
            adminDetails.address
          }}</el-descriptions-item>
          <el-descriptions-item label="账户状态"
            ><el-tag size="small">{{
              adminDetails.pageStatus
            }}</el-tag></el-descriptions-item
          >
          <el-descriptions-item label="创建时间">{{
            adminDetails.gmtCreate
          }}</el-descriptions-item>
        </el-descriptions>
        <span slot="footer" class="dialog-footer">
          <el-button @click="centerDialogVisible = false">返 回</el-button>
        </span>
      </el-dialog>

      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 30, 40, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "AdminList",
  data() {
    return {
      /* 控制添加管理员的表单显隐 */
      dialogFormVisible: false,
      form: {
        username: "",
        password: "",
        realName: "",
        email: "",
      },
      rules: {
        username: [
          { required: true, message: "账号不能为空！", trigger: "blur" },
          {
            min: 5,
            max: 20,
            message: "账号长度应在5~20个字符之间！",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "密码不能为空！", trigger: "blur" },
          {
            min: 8,
            max: 20,
            message: "密码长度应在8~20个字符之间！",
            trigger: "blur",
          },
        ],
        realName: [
          { required: true, message: "真实姓名不能为空！", trigger: "blur" },
        ],
        email: [
          {
            required: true,

            message: "邮箱不能为空！",
            trigger: "blur",
          },
          {
            type: "email",
            message: "邮箱格式不正确！",
            trigger: "blur",
          },
        ],
      },
      formLabelWidth: "100px",
      // 用户列表
      adminList: [],
      // 是否展示加载动画
      adminListIsLoading: true,
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
      // 是否展示用户的详细信息
      centerDialogVisible: false,
      // 以表单的形式展示用户详细信息，因为表中展示的数据在某些情况下不好直观查看
      adminDetails: {
        id: "",
        realName: "",
        gender: "",
        birthday: "",
        username: "",
        avatar: "",
        nickname: "",
        auth: "",
        email: "",
        phone: "",
        address: "",
        pageStatus: "",
        gmtCreate: "",
      },
      // labelStyle
      LS: {
        "width": "100px",
      },
    };
  },
  methods: {
    /* 搜索功能：可通过药品名称、编号或用途查找 */
    goToSearch() {
      if (this.inputContent != "") {
        this.currentPage = 1;
        this.adminListIsLoading = true;
        this.getAdminList();
      }
    },
    /* 分页获取admin列表， 页面加载就执行 */
    getAdminList() {
      this.$axios
        .get(
          "/admin/list/" +
            this.currentPage +
            "/" +
            this.pageSize +
            "/" +
            this.inputContent.trim(),
          {
            headers: {
              Authorization: localStorage.getItem("token"),
            },
          }
        )
        .then((res) => {
          console.log("getAdminList===>");
          console.log(res);
          this.adminList = res.data.data.data.records;
          this.total = res.data.data.data.total;
          this.adminListIsLoading = false;
        });
    },

    // 查看每个人的详细信息
    handleLookOver(row) {
      this.centerDialogVisible = true;
      this.adminDetails.id = row.id;
      this.adminDetails.realName = row.realName;
      this.adminDetails.gender = row.gender;
      this.adminDetails.birthday = row.birthday;
      this.adminDetails.username = row.username;
      this.adminDetails.auth = row.authority;
      // 如果图片的路径包含 img/ ，就在头像路径前加上后端的域名
      this.adminDetails.avatar = row.avatar;
      this.adminDetails.nickname = row.nickName;
      this.adminDetails.email = row.email;
      this.adminDetails.phone = row.phone;
      this.adminDetails.address = row.address;
      this.adminDetails.pageStatus = row.pageStatus;
      this.adminDetails.gmtCreate = row.gmtCreate;
    },
    // 根据adminIsDisabled的值来判断是调用禁用还是启用的方法
    handleDisable(row) {
      let formData = new FormData();
      formData.append("id", row.id);
      if (row.status == 0) {
        this.$confirm("是否启用该账户?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "",
        })
          .then(() => {
            this.$axios.post("/admin/change/status", formData).then((res) => {
              this.adminListIsLoading = true;
              this.getAdminList(this.currentPage);
              // 添加日志
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
                  let doWhat = "启用管理员账号：" + row.username;
                  formData.append("doWhat", doWhat);
                  this.$axios.post("/log/add", formData);
                });
            });
            this.$message({
              type: "success",
              message: "已成功启用该用户!",
            });
          })
          .catch(() => {});
      } else {
        this.$confirm("是否禁用该账户?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "",
        })
          .then(() => {
            this.$axios.post("/admin/change/status", formData).then((res) => {
              this.adminListIsLoading = true;
              this.getAdminList(this.currentPage);
              // 添加日志
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
                  let doWhat = "禁用管理员账号：" + row.username;
                  formData.append("doWhat", doWhat);
                  this.$axios.post("/log/add", formData);
                });
            });
            this.$message({
              type: "success",
              message: "已成功禁用该用户!",
            });
          })
          .catch(() => {});
      }
    },
    /* 复选框进行多选 */
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val);
    },
    /* 获取当前选择的pageSize */
    handleSizeChange(newPageSize) {
      this.adminListIsLoading = true;
      this.pageSize = newPageSize;

      this.getAdminList();
    },
    /* 获取当前选择的pageSize */
    handleCurrentChange(newCurrentPage) {
      this.adminListIsLoading = true;
      this.currentPage = newCurrentPage;

      this.getAdminList();
    },
    /* 确定添加管理员！ */
    confirmAdd(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.form);
          this.$axios.post("/admin/add", this.form).then((res) => {
            console.log(res);
            if (res.data.status) {
              this.$message({
                type: "success",
                message: res.data.message,
                showClose: true,
              });
              this.dialogFormVisible = false;
              this.getAdminList();
              // 添加日志
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
                    "添加管理员 => 姓名：" +
                    this.form.realName +
                    "，账号：" +
                    this.form.username;
                  formData.append("doWhat", doWhat);
                  this.$axios.post("/log/add", formData);
                });
            } else {
              this.$message({
                type: "error",
                message: res.data.message,
                showClose: true,
              });
            }
          });
        } else {
          return false;
        }
      });
    },
    /* 查询全部 */
    lookAll() {
      this.adminListIsLoading = true;
      this.inputContent = "";
      this.currentPage = 1;
      this.getAdminList();
    },
  },
  /* 页面加载就执行 */
  created() {
    this.getAdminList();
  },
};
</script>

<style scoped>
.adminList-breadcrumb {
  margin: 0 0 15px 2px;
}
.yjx-admin {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 10px;
  padding: 20px 25px 30px 25px;
  min-width: 1200px;
  min-height: 500px;
}
.admin-search {
  width: 35%;
  margin: 10px 0;
}
/* 表格上方的查看全部，批量禁用等按钮 */
.admin-add,
.moreDisable,
.look-all {
  float: right;
  margin-left: 10px;
  margin-top: 18px;
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
.el-pagination {
  margin: 10px 0 0 0;
}

</style>
