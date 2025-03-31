<template>
  <div class="healthTipsEdit">
    <el-page-header @back="goBack" content="文章编辑"> </el-page-header>

    <div class="center">
      <div class="title">
        <span>标题：</span>
        <el-input
          type="text"
          placeholder="请输入标题"
          v-model="title"
          maxlength="25"
          show-word-limit
          style="width: 96%"
        ></el-input>
        <p class="author">作者：&emsp;{{ author }}</p>
      </div>
      <span>类型：</span>
      <el-select
        v-model="value"
        placeholder="请选择"
        style="margin-bottom: 16px"
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <div class="link_div" v-if="value == 1">
        <span class="link_span">链接地址：</span>
        <el-input
          type="text"
          placeholder="如为转载，请输入原文链接；原创内容无需填写。"
          v-model="reprintLink"
          style="width: 90%"
        ></el-input>
      </div>
      <div class="link_else" v-else></div>

      <span>分类：</span>
      <el-select
        v-model="category"
        placeholder="为文章选择分类"
        style="margin-bottom: 16px"
      >
        <el-option
          v-for="item in categoryArr"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <div class="link_else"></div>

      <span class="tag_contents">正文：</span>
      <div id="editor"></div>
      <p style="text-align: center">
        <el-button plain @click="clearAll()">清空</el-button>
        <el-button type="primary" plain @click="addHealthTips()"
          >修改</el-button
        >
      </p>
    </div>
  </div>
</template>

<script>
import E from "wangeditor";
export default {
  data() {
    return {
      editor: "",
      title: "",
      author:"",
      reprintLink: "",
      contents: "",
      options: [
        {
          value: "0",
          label: "原创",
        },
        {
          value: "1",
          label: "转载",
        },
      ],
      value: "0",
      categoryArr: [],
      category: "",
    };
  },
  mounted() {
    this.initEditor();
  },
  created() {
    this.getArticleById();
    this.getFirstSortArr();
  },
  methods: {
    goBack() {
      // console.log("go back");
      this.$router.go(-1);
    },
    /* 初始化编辑器 */
    initEditor() {
      const editor = new E("#editor");
      // 或者 const editor = new E( document.getElementById('div1') )
      editor.config.height = 500;
      // 适应版本
      editor.customConfig = editor.customConfig
        ? editor.customConfig
        : editor.config;
      editor.customConfig.onchange = (html) => {
        this.contents = html;
        // this.contents = editor.txt.text();
      };
      // 配置 server 接口地址
      // editor.config.uploadImgServer = this.$store.state.imgBaseUrl + "img";
      editor.customConfig.zIndex = 1;
      this.editor = editor;

      editor.create();
    },
    /* 获取药品的一级分类 */
    getFirstSortArr() {
      this.$axios.get("/health/tips/get/firstSort").then((res) => {
        let categoryArr = res.data.data.data;
        for (let [index, category] of categoryArr.entries()) {
          let item = {
            value: "",
            label: "",
          };
          item.value = index;
          item.label = category.category;
          this.categoryArr.push(item);
        }
        // console.log("分类数组====>", this.categoryArr);
      });
    },
    /* 根据文章id获取要编辑的文章 ， 回显数据*/
    getArticleById() {
      let formData = new FormData();
      formData.append("articleId", this.$route.params.articleId);
      this.$axios.post("/health/tips/get/details", formData).then((res) => {
        console.log(res);
        let editArticle = res.data.data.responseBody;
        this.author = editArticle.author
        this.title = editArticle.title;
        if (editArticle.origin === "原创") {
          this.value = "0";
        } else {
          this.value = "1";
          this.reprintLink = editArticle.reprintLink;
        }
        this.category = editArticle.category;
        this.editor.txt.html(editArticle.contents);
      });
    },
    /* 修改文章 */
    addHealthTips() {
      console.log(this.contents);
      console.log(typeof this.category);
      if (this.title != "" && this.contents != "") {
        let article = {
          id: this.$route.params.articleId,
          title: this.title,
          username: this.$store.getters.getUser.username,
          author: this.author,
          origin: this.options[this.value].label,
          // category: this.categoryArr[this.category].label,
          // 回显的是类型，是字符串，而选择时是数值替换的字符串，所以做一个判断
          category:
            typeof this.category === "number"
              ? this.categoryArr[this.category].label
              : this.category,
          reprintLink: this.reprintLink,
          contents: this.contents,
        };
        console.log(article);
        this.$axios
          .post("/health/tips/update", article, {
            headers: {
              Authorization: localStorage.getItem("token"),
            },
          })
          .then((res) => {
            console.log(res);
            if (res.data.status) {
              this.$message({
                type: "success",
                message: "文章修改成功！",
                showClose: true,
              });
              this.$router.push("/drug/news/list");
              // 记录日志
              this.$axios
                .get("/admin/info/" + this.$store.getters.getUser.id)
                .then((res) => {
                  let user = res.data.data.data;
                  let formData = new FormData();
                  formData.append(
                    "auth",
                    user.auth == 1 ? "超级管理员" : "管理员"
                  );
                  formData.append("admin", user.realName);
                  let doWhat =
                    "修改了文章（" +
                    article.title +
                    "），文章编号：" +
                    article.id;
                  formData.append("doWhat", doWhat);
                  this.$axios.post("/log/add", formData);
                });
            } else {
              this.$message.warning(res.data.message + "！");
            }
          });
      } else {
        this.$message({
          message: "文章标题和内容都必须填写！",
          showClose: true,
          type: "warning",
        });
      }
    },
    /* 清除所有 */
    clearAll() {
      console.log(this.options[this.value].label);
      this.editor.txt.clear();
      this.title = "";
      this.reprintLink = "";
      this.value = "0";
      this.category = "";
    },
  },
};
</script>

<style scoped>
.healthTipsEdit >>> .el-page-header__content {
  color: #606266;
}
@import "./style/healthTipsEdit.css";
</style>