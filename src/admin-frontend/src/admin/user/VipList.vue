<template>
  <div>
    <!-- 顶部面包屑 -->
    <el-breadcrumb separator="/" class="vipList-breadcrumb">
      <el-breadcrumb-item
        ><span style="font-size: 16px">用户管理</span></el-breadcrumb-item
      >
      <el-breadcrumb-item
        ><span style="font-size: 18px">会员列表</span></el-breadcrumb-item
      >
    </el-breadcrumb>

    <div class="yjx-vip">
      <!-- 搜索功能 -->
      <el-input
        placeholder="可通过用户ID、用户名或手机号码等精确信息进行查找"
        v-model="inputContent"
        clearable
        class="vip-search"
      >
      </el-input>
      <el-button
        type="primary"
        icon="el-icon-search"
        plain
        @click="goToSearch()"
        >搜索</el-button
      >

      <!-- 批量禁用按钮 -->
      <el-popover
        placement="top-start"
        title="提示"
        width="180"
        trigger="hover"
        content="并未提供批量恢复功能，此功能慎用！"
        :open-delay="500"
      >
        <el-button
          size="small"
          class="moreDisable"
          @click="toBatchDisable()"
          slot="reference"
          ><i class="el-icon-circle-close" style="color: #63b0ff"
            >&nbsp;批量禁用</i
          ></el-button
        ></el-popover
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

      <!-- 表格开始 -->
      <el-table
        :data="vipList"
        border
        style="width: 100%"
        v-loading="vipListIsLoading"
        element-loading-text="拼命加载中"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="50"> </el-table-column>
        <el-table-column
          fixed
          label="序"
          width="100"
        >
          <template slot-scope="scoped">{{ scoped.$index + 1 }}</template>
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
          prop="nickName"
          label="昵称"
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
        <el-table-column
          prop="address"
          label="地址"
          width="230"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
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
          width="100"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <el-table-column
          prop="gmtCreate"
          label="注册时间"
          width="250"
          :show-overflow-tooltip="true"
        >
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="130"
          :show-overflow-tooltip="true"
        >
          <template slot-scope="scope" >
            <el-button
              @click="handleLookOver(scope.row)"
              type="text"
              size="small"
              >查看</el-button
            >

            <el-button
              @click="upgradeToUser(scope.row)"
              type="text"
              size="small"
              >降级</el-button
            >

            <el-button
              type="text"
              size="small"
              @click="handleDisable(scope.row)"
              >{{
                scope.row.pageStatus === "已禁用" ? "启用" : "禁用"
              }}</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 查看用户的详细信息，已表单的形式展示 -->
      <el-dialog :visible.sync="centerDialogVisible" width="50%" center>
        <el-descriptions title="详细信息" :column="2" border :labelStyle="LS">
          <!-- <el-descriptions-item label="头像"
            ><img :src="vipDetails.avatar" width="70" height="70"
          /></el-descriptions-item> -->
          <el-descriptions-item label="ID">{{
            vipDetails.id
          }}</el-descriptions-item>
          <el-descriptions-item label="真实姓名">{{
            vipDetails.realName
          }}</el-descriptions-item>
          <el-descriptions-item label="性 别">{{
            vipDetails.gender
          }}</el-descriptions-item>
          <el-descriptions-item label="生 日">
            {{ vipDetails.birthday }}
          </el-descriptions-item>
          <el-descriptions-item label="账 号">{{
            vipDetails.username
          }}</el-descriptions-item>
          <el-descriptions-item label="昵 称">{{
            vipDetails.nickname
          }}</el-descriptions-item>
          <el-descriptions-item label="拥有权限"
            ><el-tag size="small" type="danger">{{
              vipDetails.auth
            }}</el-tag></el-descriptions-item
          >
          <el-descriptions-item label="邮箱地址">{{
            vipDetails.email
          }}</el-descriptions-item>
          <el-descriptions-item label="所在地址">{{
            vipDetails.address
          }}</el-descriptions-item>
          <el-descriptions-item label="手机号码">{{
            vipDetails.phone
          }}</el-descriptions-item>

          <el-descriptions-item label="账户状态"
            ><el-tag size="small" :type="type">{{
              vipDetails.pageStatus
            }}</el-tag></el-descriptions-item
          >
          <el-descriptions-item label="创建时间">{{
            vipDetails.gmtCreate
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
  name: "vipList",
  data() {
    return {
      // 用户列表
      vipList: [],
      // 是否展示加载动画
      vipListIsLoading: true,
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
      vipDetails: {
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
      type: "",
      // labelStyle
      LS: {
        width: "100px",
      },
    };
  },
  methods: {
    /* 搜索功能：可通过药品名称、编号或用途查找 */
    goToSearch() {
      if (this.inputContent != "") {
        this.currentPage = 1;
        this.vipListIsLoading = true;
        this.getVipList();
      }
    },

    /* 分页获取VIP列表， 页面加载就执行 */
    getVipList() {
      const _this = this;
      this.$axios
        .get(
          "/vip/list/" +
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
          // console.log("getVipList===>");
          // console.log(res);
          _this.vipList = res.data.data.data.records;
          _this.total = res.data.data.data.total;
          _this.vipListIsLoading = false;
        });
    },
    // 降级会员为普通用户
    upgradeToUser(row) {
      const _this = this;
      this.$confirm("是否将该VIP降级为用户?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "",
      })
        .then(() => {
          this.$axios.post("/vip/downgrade/user/" + row.id).then((res) => {
            _this.vipListIsLoading = true;
            _this.getVipList(_this.currentPage);
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
                let doWhat = "更改会员用户 " + row.username + " 为普通用户";
                formData.append("doWhat", doWhat);
                this.$axios.post("/log/add", formData);
              });
          });
          _this.$message({
            type: "success",
            message: "操作成功!",
          });
        })
        .catch(() => {});
    },
    // 查看VIP的详细信息
    handleLookOver(row) {
      this.centerDialogVisible = true;
      this.vipDetails.id = row.id;
      this.vipDetails.realName = row.realName;
      this.vipDetails.gender = row.gender;
      this.vipDetails.birthday = row.birthday;
      this.vipDetails.username = row.username;
      this.vipDetails.auth = row.authority;
      this.vipDetails.avatar = row.avatar;
      this.vipDetails.nickname = row.nickname;
      this.vipDetails.email = row.email;
      this.vipDetails.phone = row.phone;
      this.vipDetails.address = row.address;
      this.vipDetails.pageStatus = row.pageStatus;
      this.vipDetails.gmtCreate = row.gmtCreate;
      if (row.pageStatus === "已禁用") {
        this.type = "info";
      } else {
        this.type = "success";
      }
    },
    // 根据row.status的值来判断是调用禁用还是启用的方法
    handleDisable(row) {
      if (row.status == 1) {
        this.vipDisable(row);
      } else if (row.status == 0) {
        this.vipEnable(row);
      } else {
        return null;
      }
    },
    /* 禁用单个会员 */
    vipDisable(row) {
      const _this = this;
      this.$confirm(
        "此操作将导致该用户无法正常登录账号，对用户造成不好的体验，是否继续？",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          this.$axios.get("/userInfo/disabled/" + row.id).then((res) => {
            _this.vipListIsLoading = true;
            _this.getVipList(_this.currentPage);
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
                let doWhat = "禁用VIP账号：" + row.username;
                formData.append("doWhat", doWhat);
                this.$axios.post("/log/add", formData);
              });
          });
          _this.$message({
            type: "success",
            message: "已成功禁用该用户!",
          });
        })
        .catch(() => {});
    },
    /* 启用单个会员 */
    vipEnable(row) {
      const _this = this;
      this.$confirm("是否启用该账户?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "",
      })
        .then(() => {
          this.$axios.get("/userInfo/disabled/" + row.id).then((res) => {
            _this.vipListIsLoading = true;
            _this.getVipList(_this.currentPage);
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
                let doWhat = "启用VIP账号：" + row.username;
                formData.append("doWhat", doWhat);
                this.$axios.post("/log/add", formData);
              });
          });
          _this.$message({
            type: "success",
            message: "已成功启用该用户!",
          });
        })
        .catch(() => {});
    },
    /* 复选框进行多选 */
    handleSelectionChange(val) {
      this.multipleSelection = val;
      // console.log(val);
    },
    /* 先进行判断，是否有选中数据，
       再执行批量禁用请求 */
    toBatchDisable() {
      if (this.multipleSelection == null || this.multipleSelection == "") {
        this.$alert("请先选择要禁用的用户！！！", "提示", {
          confirmButtonText: "确定",
          callback: (action) => {},
        });
      } else {
        // 执行批量禁用
        this.batchDisable();
      }
    },
    /* 发送批量禁用请求  */
    batchDisable() {
      this.$confirm(
        "此操作将导致被选中的用户无法正常登录账号，可能对用户造成不好的体验，是否继续？",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          let list = [];
          for (const item of this.multipleSelection) {
            list.push(item.id);
          }
          let formData = new FormData();
          formData.append("userIds", list);
          this.$axios.post("/user/batch/disable", formData).then((res) => {
            this.vipListIsLoading = true;
            this.getVipList();
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
                let doWhat = "批量禁用VIP账号，账号id编号：（" + list + "）";
                formData.append("doWhat", doWhat);
                this.$axios.post("/log/add", formData);
              });
          });
          this.$message({
            type: "success",
            message: "操作成功!",
          });
        })
        .catch(() => {});
    },
    /* 获取当前选择的pageSize */
    handleSizeChange(newPageSize) {
      this.vipListIsLoading = true;
      this.pageSize = newPageSize;

      this.getVipList();
    },
    /* 获取当前选择的pageSize */
    handleCurrentChange(newCurrentPage) {
      this.vipListIsLoading = true;
      this.currentPage = newCurrentPage;

      this.getVipList();
    },
    /* 查询全部 */
    lookAll() {
      this.vipListIsLoading = true;
      this.inputContent = "";
      this.currentPage = 1;
      this.getVipList();
    },
  },
  /* 页面加载就执行 */
  created() {
    this.getVipList();
  },
};
</script>

<style scoped>
.yjx-vip {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 10px;
  padding: 20px 25px 30px 25px;
  min-width: 1200px;
  min-height: 500px;
}
.vip-search {
  width: 35%;
  margin: 10px 0;
}
/* 表格上方的查看全部，批量禁用等按钮 */
.vip-add,
.moreDisable,
.look-all {
  float: right;
  margin-left: 10px;
  margin-top: 18px;
}
.vipList-breadcrumb {
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
.el-pagination {
  margin: 10px 0 0 0;
}
</style>
