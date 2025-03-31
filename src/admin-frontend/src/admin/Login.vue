<template>
  <div class="admin-login">
    
    <!-- 账号登录 -->
    <div class="yjx-admin-login" v-show="loginFlag">
      <h2 class="title">药店后台</h2>
      <el-form
        :model="accountLoginForm"
        :rules="rules"
        ref="accountLoginForm"
        label-width="100px"
        class="demo-form"
      >
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="accountLoginForm.username"
            style="width: 330px"
            placeholder="请输入用户名"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="accountLoginForm.password"
            style="width: 330px"
            type="password"
            placeholder="请输入密码"
          ></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="verificationCode">
          <el-input
            v-model="accountLoginForm.verificationCode"
            style="width: 150px"
            placeholder="请输入验证码"
          ></el-input>
        </el-form-item>
        <el-image
          style="width: 130px; height: 48px"
          :src="verificationUrl"
          @click="refreshVerification()"
        >
        </el-image>

        <el-form-item>
          <el-button
            class="btn-login"
            type="primary"
            @click="submitForm('accountLoginForm')"
            >登录</el-button
          >
          <el-button @click="resetForm('accountLoginForm')">重置</el-button>
          
        </el-form-item>
      </el-form>
    </div>

    <!-- 邮箱登录 -->
    <div class="yjx-admin-login-email" v-show="!loginFlag">
      <h2 class="title">药店后台</h2>
      <el-form
        :model="emailLoginForm"
        :rules="rules"
        ref="emailLoginForm"
        label-width="100px"
        class="demo-form"
      >
        <el-form-item label="邮箱地址" prop="email">
          <el-input
            v-model="emailLoginForm.email"
            style="width: 330px"
            placeholder="请输入邮箱地址"
          ></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="emailCode">
          <el-input
            v-model="emailLoginForm.emailCode"
            style="width: 188px"
            placeholder="请输入验证码"
          ></el-input>
          <el-button style="margin-left:10px;width:132px" :disabled="isDisabled" @click="getEmailCode()">{{emailCodeBtnTitle}}</el-button>
        </el-form-item>
        
        </el-image>

        <el-form-item>
          <el-button
            class="btn-login"
            type="primary"
            @click="emailLogin('emailLoginForm')"
            >登录</el-button
          >
          <el-button @click="resetForm('emailLoginForm')">重置</el-button>
          <el-link
            type="primary"
            :underline="false"
            style="margin: -5px 0 0 15px"
            @click="loginFlagClick()"
            >账号登录</el-link
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      accountLoginForm: {
        username: "",
        password: "",
        verificationCode: "",
      },
      emailLoginForm: {
        email: "",
        emailCode: "",
      },
      rules: {
        username: [
          { required: true, message: "用户名不能为空", trigger: "blur" },
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
        ],
        verificationCode: [
          { required: true, message: "验证码不能为空", trigger: "blur" },
        ],
        email: [
          { required: true, message: "邮箱地址不能为空", trigger: "blur" },
        ],
        emailCode: [
          { required: true, message: "验证码不能为空", trigger: "blur" },
        ],
      },
      verificationUrl: this.$store.state.imgBaseUrl + "verification",
      emailCodeBtnTitle: "获取验证码",
      count: 60,
      isDisabled: false,
      timer: "",
      loginFlag: true,
    };
  },
  methods: {
    /* 用账号登录的表单提交方法 */
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this;
          this.$axios
            .post("/admin/login", this.accountLoginForm)
            .then((res) => {
              console.log(res);
              // 成功登录
              if (res.data.message === "登录成功！") {
                _this.$message({
                  message: res.data.message,
                  type: "success",
                });
                _this.refreshVerification();
                const jwt = res.headers["authorization"];
                const userInfo = res.data.data.adminInfo;
                console.log(userInfo);

                // 把数据共享出去
                _this.$store.commit("SET_TOKEN", jwt);
                _this.$store.commit("SET_USERINFO", userInfo);
                _this.$router.push("/admin");

                 // 记录日志
                _this.$axios
                  .get("/admin/info/" + userInfo.id, {
                    headers: {
                      Authorization: localStorage.getItem("token"),
                    },
                  })
                  .then((res) => {
                    let user = res.data.data.data;
                    let formData = new FormData();
                    formData.append(
                      "auth",
                      user.auth == 1 ? "超级管理员" : "管理员"
                    );
                    formData.append("admin", user.realName);
                    let doWhat = "登录药店后台管理系统";
                    formData.append("doWhat", doWhat);
                    this.$axios.post("/log/add", formData);
                  });
              } else {
                _this.$message({
                  message: res.data.message,
                  type: "error",
                });
                _this.refreshVerification();
              }
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    /* 刷新验证码 */
    refreshVerification() {
      this.verificationUrl =
        this.$store.state.imgBaseUrl + "/verification" + "?" + Math.random();
    },
    /* 获取邮箱验证码 */
    getEmailCode() {
      console.log("调用了getEmailCode()");
      console.log(this.emailLoginForm.email);
      // 先判断有没有邮箱地址，没有就提示输入，有就下一步
      if (
        this.emailLoginForm.email == null ||
        this.emailLoginForm.email == ""
      ) {
        this.$message({
          showClose: true,
          message: "请先输入邮箱地址！",
          type: "error",
        });
      } else {
        // 这里调用倒计时的方法，倒计时开始时，将按钮设为禁用状态
        this.timer = setInterval(this.countdown, 1000);
        this.isDisabled = true;
        this.$axios
          .get("/get/emailCode/" + this.emailLoginForm.email)
          .then((res) => {
            console.log(res);
            if (res.data.status) {
              this.$notify({
                title: "提示",
                message: "验证码发送成功，请尽快前往邮箱查看！（5分钟内有效）",
                type: "success",
                position: "bottom-right",
              });
            } else {
              this.$message({
                showClose: true,
                message: "获取验证码失败，请稍后再试！",
                type: "error",
              });
            }
          });
      }
    },
    /* 倒计时的方法，点击时被调用，点击获取验证码后，就可以倒计时 */
    countdown() {
      this.emailCodeBtnTitle = this.count + "s后重新获取";
      this.count--;
      if (this.count == -1) {
        this.isDisabled = false;
        this.emailCodeBtnTitle = "获取验证码";
        clearInterval(this.timer);
        this.count = 60;
      }
    },
    /* 邮箱登录 */
    emailLogin(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this;
          console.log(this.emailLoginForm);
          this.$axios
            .post("/admin/email/login", this.emailLoginForm)
            .then((res) => {
              console.log(res);
              // 成功登录
              if (res.data.message === "登录成功！") {
                _this.$message({
                  message: res.data.message,
                  type: "success",
                });
                const jwt = res.headers["authorization"];
                const userInfo = res.data.data.adminInfo;
                console.log(userInfo);
                // 把数据共享出去
                _this.$store.commit("SET_TOKEN", jwt);
                _this.$store.commit("SET_USERINFO", userInfo);
                _this.$router.push("/admin");

                  // 记录日志
                _this.$axios
                  .get("/admin/info/" + userInfo.id, {
                    headers: {
                      Authorization: localStorage.getItem("token"),
                    },
                  })
                  .then((res) => {
                    let user = res.data.data.data;
                    let formData = new FormData();
                    formData.append(
                      "auth",
                      user.auth == 1 ? "超级管理员" : "管理员"
                    );
                    formData.append("admin", user.realName);
                    let doWhat = "登录药店后台管理系统";
                    formData.append("doWhat", doWhat);
                    this.$axios.post("/log/add", formData);
                  });
              } else {
                _this.$message({
                  type: "error",
                  message: res.data.message,
                });
              }
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    /* 两个表单之间的切换 */
    loginFlagClick() {
      this.loginFlag = !this.loginFlag;
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style scoped>
.admin-login {
  height: 100%;
  background-image: url(../assets/img/bg_admin-login.png);
  background-repeat: no-repeat;
}
/* 账号登录 */
.yjx-admin-login {
  width: 500px;
  height: 356px;
  background-color: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding-top: 28px;
  /* 登录实现居中效果 */
  margin: auto;
  position: relative;
  top: 25%;
}
.yjx-admin-login .title {
  text-align: center;
  margin-bottom: 28px;
}
/* 图片验证码位置调整 */
.el-image {
  float: right;
  margin: -66px 77px 0 0;
}
.btn-login {
  margin-left: 77.9px;
}

/* 邮箱登录 */
.yjx-admin-login-email {
  width: 500px;
  height: 306px;
  background-color: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  /* border-radius: 10px; */
  padding-top: 28px;
  /* 登录实现居中效果 */
  margin: auto;
  position: relative;
  top: 25%;
}

.yjx-admin-login-email .title {
  text-align: center;
  margin-bottom: 28px;
}
</style>
