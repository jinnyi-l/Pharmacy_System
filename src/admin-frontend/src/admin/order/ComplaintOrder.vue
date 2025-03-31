<template>
  <div class="complaintOrder">
    <el-breadcrumb separator="/" class="drugRecommend-breadcrumb">
      <el-breadcrumb-item
        ><span style="font-size: 16px">订单管理</span></el-breadcrumb-item
      >
      <el-breadcrumb-item
        ><span style="font-size: 18px">投诉订单</span></el-breadcrumb-item
      >
    </el-breadcrumb>

    <div class="center">
      <div class="table">
        <el-table
          :data="complaintOrders"
          style="width: 100%"
        >
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
          <el-table-column label="订单状态" width="90">
            <span style="color:red">用户投诉</span>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <OrderDetails
                :detailsInfo="scope.row"
                class="complaintOrderDetails"
                >投诉理由</OrderDetails
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
import OrderDetails from "@/admin/order/components/OrderDetails";
export default {
  components: {
    OrderDetails,
  },
  data() {
    return {
      // 投诉订单
      complaintOrders: [],
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
    current: "getComplaintOrderList",
    pageSize: "getComplaintOrderList",
  },
  created() {
    this.getComplaintOrderList();
  },
  methods: {
    // 获取投诉订单
    getComplaintOrderList() {
      // 投诉订单的complaint == -1
      const complaint = 1;
      this.$axios
        .get(
          "/admin/complaint/order/" +
            this.current +
            "/" +
            this.pageSize +
            "/" +
            complaint
        )
        .then((res) => {
          console.log("投诉订单==>", res);
          this.complaintOrders = res.data.data.responseBody.orderList;
          this.total = res.data.data.responseBody.total;
        });
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
@import "./style/ComplaintOrder.css";
</style>