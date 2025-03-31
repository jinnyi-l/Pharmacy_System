<template>
  <div class="main">
    <el-breadcrumb separator="/" class="medicineList-breadcrumb">
      <el-breadcrumb-item
        ><span style="font-size: 16px">后台管理</span></el-breadcrumb-item
      >
      <el-breadcrumb-item
        ><span style="font-size: 18px">我的桌面</span></el-breadcrumb-item
      >
    </el-breadcrumb>

    <div class="welcome-timer">
      <el-row>
        <el-col>
          <el-card shadow="never" class="welcome">
            欢迎 {{ admin.auth == 1 ? "超级管理员" : "管理员" }}：<span
              class="name"
            >
              {{ admin.realName }}</span
            >&nbsp;！当前时间：{{ time }}
          </el-card>
        </el-col>
      </el-row>
    </div>

    <statistics />

    <technology-stack />

    <div class="main-footer">
      <el-divider></el-divider>
      <p>&copy;2023</p>
    </div>
  </div>
</template>

<script>
import Statistics from "@/components/main/Statistics";
import TechnologyStack from "@/components/main/TechnologyStack";

export default {
  components: {
    Statistics,
    TechnologyStack,
  },
  data() {
    return {
      timer: "",
      time: "",
      admin: {},
    };
  },
  methods: {
    getAdminInfo() {
      this.$axios
        .get("/admin/info/" + this.$store.state.userInfo.id, {
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        })
        .then((res) => {
          this.admin = res.data.data.data;
        });
    },
    getNowTime() {
      this.timer = setInterval(() => {
        this.time = new Date().toLocaleString();
      }, 1000);
    },
  },
  created() {
    this.getAdminInfo();
    this.getNowTime();
  },
  beforeDestroy() {
    setTimeout(this.timer);
  },
};
</script>

<style scoped>
.main .welcome-timer {
  margin: 15px 0;
  padding: 20px 18px 20px;
  background-color: #fff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.12), 0 0 4px rgba(0, 0, 0, 0.04);
  border-radius: 10px;
  min-width: 1200px;
}

.main .welcome-timer .welcome {
  border-left: 7px solid rgb(91, 91, 241);
  background-color: #f5f3f3b9;
}

.main .welcome-timer .welcome .name {
  color: rgb(182, 40, 40);
}
.main .el-divider {
  margin: 28px 0 0 0;
}
.main .main-footer {
  text-align: center;
  font-size: 14px;
  color: #000000a6;
  min-width: 1200px;
  /* background: red; */
  height: 45px;
  margin: 0;
  padding: 0;
}
.main .main-footer p {
  text-align: center;
  height: 45px;
  line-height: 45px;
  margin: 0;
}
</style>
