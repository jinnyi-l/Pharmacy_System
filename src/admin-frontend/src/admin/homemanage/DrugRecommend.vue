<template>
  <div class="drugRecommend">
    <el-breadcrumb separator="/" class="drugRecommend-breadcrumb">
      <el-breadcrumb-item
        ><span style="font-size: 16px">首页设置</span></el-breadcrumb-item
      >
      <el-breadcrumb-item
        ><span style="font-size: 18px">药品优惠</span></el-breadcrumb-item
      >
    </el-breadcrumb>

    <div class="center">
      <!-- 表格开始 -->
      <el-table
        :data="medicineList"
        style="width: 100%"
        class="medicineList-table"
        :default-sort="{ prop: 'id', order: 'ascending' }"
        v-loading="loading"
      >
        <el-table-column label="序号" width="60">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
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
        <el-table-column label="药品编号" prop="id" sortable width="110">
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
        <el-table-column label="优惠" width="60">
          <template slot-scope="scope">
            <span>八折</span>
          </template>
        </el-table-column>
        <el-table-column label="单价（元）" width="120">
          <template slot-scope="scope">
            <span style="font-family: Verdana, Geneva, Tahoma, sans-serif"
              >￥{{ parseFloat(scope.row.price).toFixed(2) }}</span
            >
          </template>
        </el-table-column>

        <el-table-column label="现价（元）" width="120">
          <template slot-scope="scope">
            <span style="font-family: Verdana, Geneva, Tahoma, sans-serif"
              >￥{{ parseFloat(scope.row.price * 0.8).toFixed(2) }}</span
            >
          </template>
        </el-table-column>
        <el-table-column label="仓库剩余" width="110">
          <template slot-scope="scope">
            <span style="margin-left: 15px">{{ scope.row.stock }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="primary">更换</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      medicineList: [],
      loading: true,
    };
  },
  methods: {
    /* 获取优惠药品 isDiscount = 1 */
    getDiscountDrugs() {
      this.$axios.post("/medicine/discount/all").then((res) => {
        this.medicineList = res.data.data.responseBody;
        this.loading = false;
      });
    },
  },
  created() {
    this.getDiscountDrugs();
  },
};
</script>

<style scoped>
.drugRecommend .center {
  margin: 15px 0;
  padding: 20px 30px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 10px;
  min-width: 1200px;
  min-height: 500px;
  color: #000000a6;
}

.drugRecommend .center .demo-table-expand {
  font-size: 0;
}
.drugRecommend .center .demo-table-expand >>> label {
  width: 90px;
  color: #99a9bf;
}
.drugRecommend .center .demo-table-expand >>> .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
.drugRecommend .center >>> .el-table__cell.el-table__expanded-cell {
  padding: 5px 40px 5px 106px;
}
</style>
