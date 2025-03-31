<template>
  <div class="healthArticle">
    <el-page-header @back="goBack" content="文章详情"> </el-page-header>

    <div class="center">
      <div class="article">
        <h1 class="title">{{ article.title }}</h1>
        <p class="author_origin">
          <span>来源：{{ article.origin }}</span>
          <span v-show="article.origin === '原创'"
            >作者：{{ article.author }}</span
          >
          <span v-show="article.origin === '转载'"
            >原文链接：<a :href="article.reprintLink" target="_blank">{{
              article.reprintLink
            }}</a></span
          >
          <span v-show="article.origin === '原创'"
            >发布时间：{{ article.gmtCreate }}</span
          >
        </p>
        <div v-html="article.contents" class="contents"></div>
        <p class="reprint_time">
          <span v-show="article.origin === '转载'"
            >转载时间：{{ article.gmtCreate }}</span
          >
          <span class="likes">阅览次数：{{ article.readNumber }}</span>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      article: {},
    };
  },
  created() {
    this.getArticleById();
  },
  methods: {
    /* 根据文章id获取文章 */
    getArticleById() {
      // console.log(this.$route.params.articleId)
      let formData = new FormData();
      formData.append("articleId", this.$route.params.articleId);
      this.$axios.post("/health/tips/get/details",formData).then((res) => {
        console.log(res);
        this.article = res.data.data.responseBody;
      });
    },
    goBack() {
      console.log("go back");
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped>
.healthArticle >>> .el-page-header__content {
  color: #606266;
}
@import "./style/healthArticle.css";
</style>