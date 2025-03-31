<template>
  <div class="healthTipsAdd">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item
        ><span style="font-size: 16px">资讯推送</span></el-breadcrumb-item
      >
      <el-breadcrumb-item
        ><span style="font-size: 18px">添加资讯</span></el-breadcrumb-item
      >
    </el-breadcrumb>

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
        <p class="author">作者：&emsp;{{ realName }}</p>
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
          >立即上传</el-button
        >
      </p>

      <div v-show="contents != '' || title != ''">
        <span class="tag_views">文章视图：</span>
        <div class="article_views">
          <h2 class="title">{{ title }}</h2>
          <div v-html="contents"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import E from "wangeditor";
export default {
  data() {
    return {
      realName:"",
      editor: "",
      title: "",
      reprintLink: "",
      contents: "",
      article: "",
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
    this.getFirstSortArr();
    this.getUserRealName()
  },
  methods: {
    /* 获取作者的真实名字 */
    getUserRealName(){
      const userId = this.$store.getters.getUser.id
      this.$axios.get("/admin/info/"+userId,{
        headers:{
          Authorization:localStorage.getItem("token"),
        }
      }).then(res=>{
        this.realName = res.data.data.data.realName
      })
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
    /* 获取一级分类 */
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
        console.log("分类数组====>", this.categoryArr);
      });
    },
    /* 添加健康资讯 */
    addHealthTips() {
      console.log(this.contents);
      if (this.title != "" && this.contents != "") {
        let article = {
          title: this.title,
          username: this.$store.getters.getUser.username,
          author: this.$store.getters.getUser.realName,
          origin: this.options[this.value].label,
          category: this.categoryArr[this.category].label,
          reprintLink: this.reprintLink,
          contents: this.contents,
        };
        console.log(article);
        this.$axios
          .post("/health/tips/add", article, {
            headers: {
              Authorization: localStorage.getItem("token"),
            },
          })
          .then((res) => {
            console.log(res);
            if (res.data.status) {
              this.$message({
                type: "success",
                message: "文章上传成功！",
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
                  let doWhat = "上传文章：" + article.title;
                  formData.append("doWhat", doWhat);
                  this.$axios.post("/log/add", formData);
                });
            } else {
              this.$message(res.data.message);
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
@import "./style/healthTipsAdd.css";
</style>