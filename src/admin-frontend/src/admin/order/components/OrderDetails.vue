<template>
  <div class="newOrderDetails">
    <el-button size="mini" @click="dialogTableVisible = true"
      ><slot></slot
    ></el-button>
    <el-dialog title="详细信息" :visible.sync="dialogTableVisible" width="50%">
      <div class="details">
        <div><span class="title">订单编号：</span>{{ orderInfo.orderId }}</div>
        <div>
          <span class="title">下单时间：</span>{{ orderInfo.gmtCreate }}
        </div>
        <div>
          <span class="title">收货姓名：</span>{{ orderInfo.receiptName }}
        </div>
        <div><span class="title">联系方式：</span>{{ orderInfo.phone }}</div>
        <div>
          <span class="title">收货地区：</span
          >{{ showAddress(orderInfo.receiptAddress) }}
        </div>
        <div>
          <span class="title">详细地址：</span>{{ orderInfo.detailsAddress }}
        </div>
        <div>
          <span class="title">购买 (种)：</span>{{ orderInfo.itemAmount }}
        </div>
        <div>
          <span class="title">交易状态：</span
          ><el-tag :type="type" size="medium">{{
            showStatus(orderInfo.status)
          }}</el-tag>
        </div>
        <div>
          <span class="title">备注：</span
          >{{ orderInfo.message == "" ? "-" : orderInfo.message }}
        </div>
      </div>
      <div class="sureTime" v-if="orderInfo.status == 3">
        收货时间：{{ orderInfo.gmtModified }}
      </div>
      <div class="complaintReason" v-if="orderInfo.complaint == 1">
        投诉理由：{{ orderInfo.reason }}
      </div>
      <el-table :data="orderInfo.drugs">
        <el-table-column
          property="drugName"
          label="药品名称"
          width="200"
        ></el-table-column>
        <el-table-column
          property="standard"
          label="规格"
          width="200"
        ></el-table-column>
        <el-table-column
          property="price"
          label="单价 (元)"
          width="150"
        ></el-table-column>
        <el-table-column property="amount" label="数量"></el-table-column>
      </el-table>
      <div class="payment">
        <span>付款金额：</span>￥<span style="font-size: 16px">{{
          parseFloat(orderInfo.payment).toFixed(2)
        }}</span>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: ["detailsInfo"],
  data() {
    return {
      orderInfo: {},
      dialogTableVisible: false,
      type: "",
    };
  },
  updated() {
    if (this.dialogTableVisible) {
      console.log("详细信息", this.$props.detailsInfo);
      this.orderInfo = this.$props.detailsInfo;
    }
  },
  methods: {
    /* 对地址字符串进行分割拼接 */
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
        case 4:
          this.type = "success";
          return "交易成功";
        default:
          return "未知";
      }
    },
  },
};
</script>

<style lang="less" scoped>
.newOrderDetails {
  .details {
    display: flex;
    flex-wrap: wrap;

    div {
      width: 50%;
      line-height: 32px;

      span {
        margin-left: 10px;
      }
    }
  }

  .sureTime {
    line-height: 32px;
    margin-left: 10px;
    color: #67c23c;
  }
  .complaintReason {
    line-height: 32px;
    margin-left: 10px;
    color: #f56c6c;
  }

  .payment {
    height: 58px;
    line-height: 58px;
    text-align: right;
    padding-right: 80px;
    font-family: Verdana, Geneva, Tahoma, sans-serif;
  }
}
</style>
