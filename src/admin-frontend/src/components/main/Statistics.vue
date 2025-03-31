<template>
  <div>
    <div class="welcome-timer">
      <el-divider content-position="left">数据统计</el-divider>
      <el-row :gutter="24">
        <el-col :span="4">
          <a href="javascript:;"
            ><el-card shadow="hover" class="statistics-item">
              <span>药品详细分类</span>
              <p>{{ drugCategoryNums }}</p>
            </el-card></a
          >
        </el-col>
        <el-col :span="4">
          <a href="javascript:;"
            ><el-card shadow="hover" class="statistics-item">
              <span>药品总量</span>
              <p>{{ drugNums }}</p>
            </el-card></a
          >
        </el-col>
        <el-col :span="4">
          <a href="javascript:;">
            <el-card shadow="hover" class="statistics-item">
              <span>最近一周新增用户</span>
              <p>{{ newUserNums }}</p>
            </el-card></a
          >
        </el-col>
        <el-col :span="4">
          <a href="javascript:;"
            ><el-card shadow="hover" class="statistics-item">
              <span>最近一个月内投诉订单</span>
              <p>{{ complaintNums }}</p>
            </el-card></a
          >
        </el-col>
        <el-col :span="4">
          <a href="javascript:;">
            <el-card shadow="hover" class="statistics-item">
              <span>未发货的订单</span>
              <p>{{ undoneOrderNums }}</p>
            </el-card></a
          >
        </el-col>
        <el-col :span="4">
          <a href="javascript:;"
            ><el-card shadow="hover" class="statistics-item">
              <span>会员数量</span>
              <p>{{vipUserNums}}</p>
            </el-card></a
          >
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 药品二级分类总数量
      drugCategoryNums: 0,
      // 获取药品总数量
      drugNums: 0,
      // 近一周新增用户数量
      newUserNums: 0,
      // 最近一个月内的投诉订单数量
      complaintNums: 0,
      // 未发货的订单数量
      undoneOrderNums: 0,
      // 会员数量
      vipUserNums: 0,
    };
  },
  created() {
    this.getDrugCategoryNums();
    this.getDrugNums();
    this.getNewUserNums();
    this.getNewComplaintNums();
    this.getUndoneOrderNums();
    this.getVipUserNums();
  },
  methods: {
    // 获取药品二级分类总数量
    getDrugCategoryNums() {
      this.$axios.post("/admin/drug/category/nums").then((res) => {
        console.log(res);
        this.drugCategoryNums = res.data.data.responseBody;
      });
    },
    // 获取药品总数量
    getDrugNums() {
      this.$axios.post("/admin/drug/nums").then((res) => {
        console.log(res);
        this.drugNums = res.data.data.responseBody;
      });
    },
    // 获取近一周新增用户数量
    getNewUserNums() {
      this.$axios.post("/admin/get/newUsers").then((res) => {
        console.log(res);
        this.newUserNums = res.data.data.responseBody;
      });
    },
    // 获取最近一个月内的投诉订单数量
    getNewComplaintNums() {
      this.$axios.post("/order/complaint/nums").then((res) => {
        console.log(res);
        this.complaintNums = res.data.data.responseBody;
      });
    },
    // 获取最近一个月内的投诉订单数量
    getUndoneOrderNums() {
      this.$axios.post("/order/undone/nums").then((res) => {
        console.log(res);
        this.undoneOrderNums = res.data.data.responseBody;
      });
    },
    // 获取会员的数量
    getVipUserNums() {
      this.$axios.post("/admin/get/vipNums").then((res) => {
        console.log(res);
        this.vipUserNums = res.data.data.responseBody;
      });
    },
  },
};
</script>

<style scoped>
.welcome-timer {
  margin: 15px 0;
  padding: 12px 18px 20px;
  background-color: #fff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.12), 0 0 4px rgba(0, 0, 0, 0.04);
  border-radius: 10px;
  min-width: 1200px;
}
.welcome-timer a {
  text-decoration: none;
}

.el-divider__text {
  font-size: 16px;
}

.statistics-item {
  background-color: #f5f3f3b9;
  height: 110px;
}

.statistics-item:hover {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.statistics-item span {
  font-size: 14px;
}

.statistics-item p {
  font-size: 24px;
  line-height: 20px;
  height: 20px;
  margin: 20px 0;
  color: #3e817c !important;
}
</style>
