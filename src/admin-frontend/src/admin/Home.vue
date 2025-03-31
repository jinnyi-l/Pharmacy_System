<template>
  <el-container class="admin-container">
    <!--头部-->
    <el-header>
      <!--标题-->
      <div class="title">
        <img alt="" height="60" src="../assets/img/logo_index.png" />
        <el-divider direction="vertical"></el-divider>
        <span
          ><el-link
            :href="this.$store.state.frontPageBaseUrl"
            style="color: #ffffffa6; font-size: 18px"
            >前台首页</el-link
          ></span
        >
      </div>
      <!-- 头像 -->
      <div style="margin-right: 28px">
        <el-dropdown v-if="user" class="user">
          <div class="el-dropdown-link">
            <p style="color: white;">{{user.username}}</p>
          </div>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="goToMyDetails"
              >个人资料</el-dropdown-item
            >
            <el-dropdown-item @click.native="logout">切换账号</el-dropdown-item>

            <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>

    <!--页面主体-->
    <el-container>
      <!--侧边栏-->
      <el-aside :width="isCollapse ? '64px' : '190px'">
        <div class="toggle-button" @click="isCollapse = !isCollapse">
          <i :class="isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
        </div>
        <!--菜单-->
        <el-menu
          :collapse="isCollapse"
          :collapse-transition="false"
          :default-active="activeRouter"
          :router="true"
          :unique-opened="true"
          active-text-color="#FFFFFF"
          background-color="#ffffff"
          text-color="black"
          ref="menu"
        >
          <!-- 一级菜单 -->
          <el-submenu
            v-for="item in menuList"
            :key="item.menuId"
            :index="item.menuId"
          >
            <!-- 一级菜单的模板区域 -->
            <template slot="title">
              <i :class="iconsObj[item.menuId]" class="iconfont"></i>
              &nbsp;
              <span>{{ item.title }}</span>
            </template>
            <!-- 二级菜单 -->
            <el-menu-item
              v-for="subItem in item.children"
              :key="subItem.menuId"
              :index="subItem.path"
            >
              <template slot="title">
                <i :class="iconsObj[subItem.menuId]"></i>
                <span>{{ subItem.title }}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!--右侧内容主体-->
      <el-main
        :class="isCollapse ? 'm-el-main-width-64' : 'm-el-main-width-190'"
      >
        <!--加 key 让组件被重用时 重新执行生命周期 -->

        <router-view :key="$route.fullPath" />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      menuList: [],
      iconsObj: {
        1: "el-icon-suitcase",
        2: "el-icon-first-aid-kit",
        3: "el-icon-document-copy",
        4: "el-icon-user-solid",
        5: "el-icon-s-home",
        6: "el-icon-s-promotion",
        11: "el-icon-monitor",
        12: "el-icon-postcard",
        21: "el-icon-document",
        22: "el-icon-menu",
        23: "el-icon-folder-add",
        31: "el-icon-document-add",
        32: "el-icon-truck",
        33: "el-icon-finished",
        34: "el-icon-warning-outline",
        41: "el-icon-s-custom",
        42: "el-icon-user",
        43: "el-icon-medal-1",
        51: "el-icon-tickets",
        52: "el-icon-picture-outline",
        53: "el-icon-thumb",
        61: "el-icon-notebook-1",
        62: "el-icon-notebook-2",
        // 63: "el-icon-tickets",
      },
      //是否折叠
      isCollapse: false,
      // 活跃的路由
      activeRouter: "",
      user: {},

      screenWidth: document.body.clientWidth,
    };
  },
  mounted() {
    //获取地址栏中的路由，设置element-ui中的导航栏选中状态
    this.activeRouter = window.location.pathname;
    window.onresize = () => {
      return (() => {
        window.screenWidth = document.body.clientWidth;
        this.screenWidth = window.screenWidth;
      })();
    };
  },
  watch: {
    screenWidth: "setIsCollapse",
  },
  methods: {
    /* 设置窗口小于xxx时， this.isCollapse = true; */
    setIsCollapse() {
      // console.log("宽度在变化===>", this.screenWidth);
      if (this.screenWidth <= 1360) {
        this.isCollapse = true;
      } else {
        this.isCollapse = false;
      }
    },
    /* 获取菜单 */
    getMenuList() {
      this.$axios.get("/menu/list/" + this.user.auth).then((res) => {
        this.menuList = res.data.data.data;
        // console.log(res);
        // console.log(this.menuList);
      });
    },
    // 获取缓存的用户名和id
    getUserInfo() {
      let user = this.$store.getters.getUser;
      if (user) {
        if (user.username) {
          // console.log("有用户信息");
          this.$axios.get("/admin/info/" + user.id,{
         headers: {
            Authorization: localStorage.getItem("token"),
          },
        }).then((res) => {
            this.user = res.data.data.data;
            // console.log(res);
            this.getMenuList();
          });
        } else {
          // console.log("没有用户信息，先登录");
          this.$router.push("/admin/login");
        }
      } else {
        // console.log("没有用户信息，先登录");
        this.$router.push("/admin/login");
      }
    },
    // 退出账号
    logout() {
      this.$axios
        .get("/logout/" + this.user.username, {
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        })
        .then((res) => {
          this.$store.commit("REMOVE_INFO");
          // _this.$store.commit("SET_DEFAULTOPENS", []);
          this.$router.push("/admin/login");
        });
    },
    // 跳转到个人资料页面
    goToMyDetails() {
      this.$router.push("/mine/info/" + this.user.id).catch((err) => {});
    },

  },
  created() {
    this.getUserInfo();
  },
};
</script>

<style scoped>
.el-header {
  background-color: #363a3b;
  display: flex;
  font-size: 20px;
  /* user-select: none; */
  /* position: fixed; */
  justify-content: space-between;
  /* padding-left: 10px; */
  align-items: center;
  color: #ebe8e8;
  /* left: 0; */
  /* top: 0; */
  width: 100%;
  z-index: 1000;
}

.el-header div {
  display: flex;
  align-items: center;
}

.el-aside {
  background-color: #ffffff;
  position: absolute;
  top: 60px;
  bottom: 0;
  user-select: none;
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.3), 0 0 10px rgba(0, 0, 0, 0.2);
}

.el-main {
  background-color: #f0eded98;
  position: absolute;
  top: 60px;
  bottom: 0;
  right: 0;
  overflow-y: auto;
  overflow-x: hidden;
}

.el-aside .el-menu {
  border-right: none;
}

.m-el-main-width-190 {
  width: calc(100% - 190px);
}

.el-dropdown-menu {
  margin: 7px 0 0 0 !important;
  padding: 0 !important;
  border: 0 !important;
}

.m-el-main-width-64 {
  width: calc(100% - 64px);
}

.admin-container {
  height: 100%;
}

.toggle-button {
  background-color: #ffffff;
  font-size: 20px;
  line-height: 40px;
  color: black;
  text-align: center;
  cursor: pointer;
}

.el-dropdown-link {
  outline-style: none !important;
  outline-color: unset !important;
  height: 100%;
  cursor: pointer;
}

.el-main::-webkit-scrollbar-track-piece {
  background-color: transparent;
}

.el-main::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px transparent;
  box-shadow: inset 0 0 6px transparent;
  background-color: transparent;
}

.el-main::-webkit-scrollbar-thumb {
  -webkit-box-shadow: inset 0 0 6px #48dbfb;
  box-shadow: inset 0 0 6px #48dbfb;
  background-color: #48dbfb;
}

.el-aside {
  -ms-overflow-style: none; /* IE10 */
  scrollbar-width: none; /* Firefox */
}

.el-aside::-webkit-scrollbar {
  display: none;
}

.el-main::-webkit-scrollbar {
  width: 8px;
  height: 5px;
}

.el-menu-item.is-active {
  background-color: #68a39e !important;
}
</style>
