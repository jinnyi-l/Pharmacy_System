<template>
  <el-result
    title="404"
    subTitle="当前页面还在在开发中，先回去看看其他的页面吧！"
  >
    <!-- <template slot="icon">
    <el-image src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"></el-image>
  </template> -->
    <template slot="extra">
      <el-button size="medium" @click="goBack">返回</el-button>
      <el-button type="primary" size="medium" @click="goToAdminIndex"
        >后台主页</el-button
      >
    </template>
  </el-result>
</template>
<script>
export default {
  data() {
    return {};
  },
  methods: {
    goBack() {
      this.$router.go(-1); //返回上一层
    },
    goToAdminIndex() {
      this.$router.push("/index");
    },
  },
};
</script>