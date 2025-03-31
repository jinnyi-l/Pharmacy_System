<template>
  <div id="div1">
    <el-table :data="tableData">
      <!-- <el-table-column type="index" label="序号"></el-table-column> -->
      <el-table-column prop="id" label="排序字段"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="password" label="密码"></el-table-column>
    </el-table>
  </div>
</template>



<script>
import Sortable from "sortablejs";
export default {
  data() {
    return {
      tableData: [
        {
          id:"1",
          username: "yang",
          password: "123456",
        },
        {
          id:"2",
          username: "hello",
          password: "dwafa",
        },
        {
          id:"3",
          username: "hahahaha",
          password: "15a1wd",
        },
        {
          id:"4",
          username: "abc",
          password: "sgegqw2",
        },
      ],
    };
  },
  mounted() {
    this.initSort();
  },
  methods: {
    initSort() {
      const el = document.querySelectorAll(
        ".el-table__body-wrapper > table > tbody"
      )[0];
      // const sortable = new Sortable(el, options);
      // 根据具体需求配置options配置项
      const sortable = new Sortable(el, {
        onEnd: (evt) => {
          // 监听拖动结束事件
          console.log(this); // this是当前vue上下文
          console.log("被拖拽前的顺序===>",evt.oldIndex+1); // 当前行的被拖拽前的顺序
          console.log("被拖拽后的顺序===>",evt.newIndex+1); // 当前行的被拖拽后的顺序
          // 这里就可以写我们需要传给后台的逻辑代码
          // 我们有了 evt.oldIndex 和 evt.newIndex 这两个参数做索引，我们可以根据绑定在表格上面的 data 这个 Array 找到两个相应的记录。就可以针对数据进行操作啦。
          // 下面将拖拽后的顺序进行修改
         /*  const currRow = this.tableData.splice(evt.oldIndex, 1)[0];
          this.tableData.splice(evt.newIndex, 0, currRow);
          const newData = [];
          this.tableData.forEach((item, index) => {
            newData[index] = {
              id: item.id,
              rank: index + 1,
            };
          }); */
          // 下面是将排序结果更新到数据库中，根据自己项目的逻辑进行实现即可。
          // const data = {         
          //   id: this.$route.params.id,
          //   datas: {
          //     streams: newData,
          //   },
          // };
          // this.$store
          //   .dispatch("user/setMultiSort", data)
          //   .then((res) => {
          //     if (res.code === 200) {
          //       this.$message.success("排序修改成功");
          //       // 修改成功后重新获取列表数据更新
          //       this.getMultiLiveList();
          //     } else {
          //       this.$message.error("排序修改失败");
          //     }
          //   })
          //   .catch((e) => {
          //     console.log(e);
          //   });
        },
      });
    },
  },
};
</script>