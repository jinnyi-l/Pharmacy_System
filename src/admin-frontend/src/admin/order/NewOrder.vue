<template>
  <div class="newOrder">
    <el-breadcrumb separator="/" class="drugRecommend-breadcrumb">
      <el-breadcrumb-item
        ><span style="font-size: 16px">订单管理</span></el-breadcrumb-item
      >
      <el-breadcrumb-item
        ><span style="font-size: 18px">最新订单</span></el-breadcrumb-item
      >
    </el-breadcrumb>

    <div class="center">
      <div class="filter">
        <el-button @click="lookAll">查看全部</el-button>
        <span class="addressFilter">地区筛选：</span
        ><el-cascader
          :options="options"
          :props="{ expandTrigger: 'hover' }"
          clearable
          v-model="areaArr"
          @change="chooseArea()"
        >
        </el-cascader>
        <el-button @click="selectWithAddress">筛选</el-button>
        <span class="search">精确搜索：</span>
        <el-input
          placeholder="请输入内容"
          v-model="input"
          class="input-with-select"
          clearable
        >
          <el-select v-model="select" slot="prepend" placeholder="请选择">
            <el-option label="订单编号" value="1"></el-option>
            <el-option label="收货人" value="2"></el-option>
            <el-option label="联系方式" value="3"></el-option>
          </el-select>
          <el-button
            slot="append"
            icon="el-icon-search"
            @click="searchWithContent"
          ></el-button>
        </el-input>
      </div>
      <div class="table">
        <el-table :data="newOrders" v-loading="loading" style="width: 100%">
          <el-table-column label="序号" width="50">
            <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="下单时间" width="180">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 6px">{{ scope.row.gmtCreate }}</span>
            </template>
          </el-table-column>
          <el-table-column label="订单编号" width="190">
            <template slot-scope="scope">
              <span>{{ scope.row.orderId }}</span>
            </template>
          </el-table-column>
          <el-table-column label="收货人" width="90">
            <template slot-scope="scope">
              <span>{{ scope.row.receiptName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="联系方式" width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.phone }}</span>
            </template>
          </el-table-column>
          <el-table-column label="收货地区" width="180">
            <template slot-scope="scope">
              <span>{{ showAddress(scope.row.receiptAddress) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="交易金额 (元)" width="110">
            <template slot-scope="scope">
              <span>￥{{ parseFloat(scope.row.payment).toFixed(2) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="购买 (种)" width="80">
            <template slot-scope="scope">
              <span>{{ scope.row.itemAmount }} </span>
            </template>
          </el-table-column>
          <el-table-column label="交易状态" width="90">
            <template slot-scope="scope">
              <el-tag :type="type" size="medium">{{
                showStatus(scope.row.status)
              }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <OrderDetails :detailsInfo="scope.row" class="OrderDetails"
                >详细</OrderDetails
              >
              <el-button size="mini" type="primary" @click="sendDrug(scope.row)"
                >发货</el-button
              >
            </template>
          </el-table-column>
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
import { regionData, CodeToText, TextToCode } from "element-china-area-data";
import OrderDetails from "@/admin/order/components/OrderDetails";
export default {
  components: {
    OrderDetails,
  },
  data() {
    return {
      // 一下子看不出来这个flag是要干嘛的了，好像是区分选项，分情况用的
      flag: 0,
      // 搜索的内容
      input: "",
      // 搜索的前缀
      select: "",
      // 选项
      options: regionData,
      // 选中的值用数组存储
      areaArr: [],
      // 筛选的地区
      receiptAddress: "",
      // tag的类型，根据status变化
      type: "",
      // 是否加载动画
      loading: true,
      // 新增订单
      newOrders: [],
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
    current: "getNewOrderList",
    pageSize: "getNewOrderList",
  },
  created() {
    this.getNewOrderList();
  },
  methods: {
    // 获取新增订单
    getNewOrderList() {
      // 未发货的status == 1
      const status = 1;
      if (this.flag == 0) {
        this.$axios
          .get(
            "/admin/order/" + this.current + "/" + this.pageSize + "/" + status
          )
          .then((res) => {
            console.log("最新订单==>", res);
            this.newOrders = res.data.data.responseBody.orderList;
            this.total = res.data.data.responseBody.total;
            this.loading = false;
          });
      } else if (this.flag == 1) {
        let formData = new FormData();
        formData.append("receiptAddress", this.receiptAddress);
        formData.append("current", this.current);
        formData.append("size", this.pageSize);
        formData.append("status", status);
        this.$axios.post("/admin/orderWith/address", formData).then((res) => {
          console.log("最新订单==>", res);
          this.newOrders = res.data.data.responseBody.orderList;
          this.total = res.data.data.responseBody.total;
          this.loading = false;
        });
      } else if (this.flag == 2) {
        let formData = new FormData();
        formData.append("type", this.select);
        formData.append("content", this.input.trim());
        formData.append("current", this.current);
        formData.append("size", this.pageSize);
        formData.append("status", status);
        this.$axios.post("/admin/orderWith/search", formData).then((res) => {
          console.log("最新订单==>", res);
          this.newOrders = res.data.data.responseBody.orderList;
          this.total = res.data.data.responseBody.total;
          this.loading = false;
        });
      }
    },
    // 查看全部
    lookAll() {
      this.flag = 0;
      this.getNewOrderList();
    },
    // 根据地址筛选
    selectWithAddress() {
      if (this.receiptAddress != "") {
        this.flag = 1;
        this.getNewOrderList();
      }
    },
    // 搜索
    searchWithContent() {
      if (this.select != "" && this.input != "") {
        this.flag = 2;
        this.getNewOrderList();
      }
    },
    // 区域地址改变
    chooseArea() {
      // 展示的你选择的值 是一个areaCode
      console.log("chooseArea ==> ", this.areaArr);
      // 把areaCode转换成地名并拼接起来
      let str = "";
      for (let i = 0; i < this.areaArr.length; i++) {
        str += CodeToText[this.areaArr[i]] + "/";
      }
      console.log(str.substring(0, str.length - 1));
      this.receiptAddress = str.substring(0, str.length - 1);
    },
    // 去除地址的 / ，拼接
    showAddress(address) {
      if (address != "" && address != null) {
        let arr = address.split("/");
        let str = "";
        for (const item of arr) {
          str += item;
        }
        return str;
      }
    },
    /* 展示对应状态 */
    showStatus(status) {
      switch (status) {
        case -1:
          this.type = "danger";
          return "被投诉";
        case 0:
          this.type = "warning";
          return "交易取消";
        case 1:
          this.type = "info";
          return "未发货";
        case 2:
          this.type = "";
          return "运输中";
        case 3:
          this.type = "success";
          return "交易成功";
        default:
          return "未知";
      }
    },
    // 发货操作
    sendDrug(order) {
      this.$confirm(
        "为避免造成客户的体验不佳， 请在发货前务必确认检查药品是否有遗漏！",
        "提示",
        {
          confirmButtonText: "确认发货",
          cancelButtonText: "取消",
        }
      )
        .then(() => {
          let formData = new FormData();
          formData.append("orderId", order.orderId);
          this.$axios.post("/admin/order/send", formData).then((res) => {
            console.log(res);
            if (res.data.status) {
              this.getNewOrderList();
              this.$message({
                type: "success",
                message: "发货成功!",
              });
              // 记录日志
              this.$axios.get("/admin/info/" + this.$store.getters.getUser.id,{
                headers:{
                  Authorization:localStorage.getItem("token")
                }
              }).then((res) => {
                let user = res.data.data.data;
                let formData = new FormData();
                formData.append(
                  "auth",
                  user.auth == 1 ? "超级管理员" : "管理员"
                );
                formData.append("admin", user.realName);
                let doWhat =
                  "将订单编号 " +
                  order.orderId +
                  " 的状态'未发货'修改为'运输中'";
                formData.append("doWhat", doWhat);
                this.$axios.post("/log/add", formData);
              });
            }
          });
        })
        .catch(() => {
          // this.$message({
          //   type: "info",
          //   message: "已取消",
          // });
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

<style scoped>
@import "./style/NewOrder.css";
</style>