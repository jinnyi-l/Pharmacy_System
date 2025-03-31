<template>
  <div id="dataShow">
    <div>
      <!-- 药品销售额统计折线图 从小到大 -->
      <div style="display: flex; justify-content: center;">
        <div ref="chart" style="width: 75%; height: 500px;margin-top: 30px;"></div>
      </div>


    </div>

    <div>
      <!-- 药品分类饼图 -->
      <div style="display: flex; justify-content: center;">

        <div ref="pieChart" style="width: 80%; height: 400px;margin-top: 70px;"></div>

      </div>



    </div>

    <div>
      <!-- 药品库存柱状图 -->
      <div style="display: flex; justify-content: center;">

        <div ref="barChart" style="width: 80%; height: 400px;margin-top: 70px;"></div>
      </div>


    </div>
  </div>
</template>

<script>
  import * as echarts from 'echarts';

  export default {

    data() {
      return {
        salesData: [{
            month: 'Jan',
            salesQuantity: 30,
            totalSales: 2000,
            productName: 'Product A'
          },



        ],
        categoryData: [{
            categoryName: '感冒',
            productCount: 15
          },
          {
            categoryName: '骨折',
            productCount: 65
          },
          {
            categoryName: '头痛',
            productCount: 2
          }
          // Add more category data as needed
        ],
        stockData: [{
            itemName: 'Medicine A',
            quantity: 100
          },
          {
            itemName: 'Medicine B',
            quantity: 150
          },
          {
            itemName: 'Medicine C',
            quantity: 80
          }
          // Add more stock data as needed
        ]


      };
    },

    mounted() {
      // 打开页面之后获取销售额

      this.initSales();
      this.initCategory();
      this.initStock();

      setTimeout(() => {
        this.renderPieChart();
        this.renderBarChart();
      }, 1000);




    },

    methods: {
      renderBarChart() {
        const barChart = echarts.init(this.$refs.barChart);

        const xAxisData = this.stockData.map(item => item.itemName);
        const seriesData = this.stockData.map(item => item.quantity);

        barChart.setOption({
          title: {
            text: '药品库存柱状图',
            x: 'center'
          },
          xAxis: {
            type: 'category',
            data: xAxisData,
            axisLabel: {
              rotate: 0, // 不旋转标签
              interval: 0, // 不隐藏任何标签
            }
          },
          yAxis: {
            type: 'value'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            },
            formatter: function (params) {
              return params[0].name + ': ' + params[0].value;
            }
          },
          series: [{
            data: seriesData,
            type: 'bar'
          }]
        });
      },


      renderPieChart() {
        const pieChart = echarts.init(this.$refs.pieChart);

        const data = this.categoryData.map(item => ({
          name: item.categoryName,
          value: item.productCount
        }));

        pieChart.setOption({
          title: {
            text: '药品分类统计',
            x: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: '{b}: {c} ({d}%)' // 只显示分类名称、数值和百分比
          },
          series: [{
            type: 'pie',
            radius: '50%',
            data: data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }]
        });
      },




      initSales() {
        // 获取销售额

        this.$axios.post("/admin/drug/sale").then((res) => {
          console.log(res);
          this.salesData = res.data.data.salesData;
          this.renderChart();
        });
      },

      initCategory() {
        // 获取分类

        this.$axios.post("/admin/drug/category").then((res) => {
          console.log(res);
          this.categoryData = res.data.data.categoryData;
          this.renderPieChart();
        });
      },


      initStock() {
        // 获取库存

        this.$axios.post("/admin/drug/repertory").then((res) => {
          console.log(res);
          this.stockData = res.data.data.stockData;
          this.renderBarChart();
        });
      },




      // 折线图代码
      renderChart() {
        const chart = echarts.init(this.$refs.chart);

        const xAxisData = this.salesData.map(data => data.productName);
        const salesQuantity = this.salesData.map(data => data.salesQuantity);
        const totalSales = this.salesData.map(data => data.totalSales);

        chart.setOption({
          title: {
            text: `销售额折线图`
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['销售数量', '总销售额']
          },
          xAxis: {
            type: 'category',
            data: xAxisData,
            axisLabel: {
              rotate: 0, // 不旋转标签
              interval: 0, // 不隐藏任何标签
            }
          },
          yAxis: {
            type: 'value'
          },
          series: [{
              name: '销售数量',
              type: 'line',
              data: salesQuantity
            },
            {
              name: '总销售额',
              type: 'line',
              data: totalSales
            }
          ]
        });
      }
    },





  }
</script>

<style>
  #dataShow {
    width: 1675px;
    height: 520px;
    background-color: #F3F3F3;
    display: inline-block;
    border-radius: 20px;
    display: flex;
    justify-content: space-between;
    overflow: hidden;
  }

  #dataShow>div {
    display: inline-block;
    height: 520px;
    width: 550px;
    background-color: #ffffff;
    border-radius: 10px;
  }
</style>